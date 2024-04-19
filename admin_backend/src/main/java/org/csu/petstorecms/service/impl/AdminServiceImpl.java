package org.csu.petstorecms.service.impl;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import javax.servlet.http.HttpServletRequest;
import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.entity.Admin;
import org.csu.petstorecms.DAO.AdminMapper;
import org.csu.petstorecms.service.AdminService;
import org.csu.petstorecms.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

//import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    private Admin getAdminByUsernameAndPassword(String username, String password) {
        // 构造条件查询器
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        // 链式调用，相当于设置两个条件，查表中username = username 且 password = password的
        queryWrapper.eq("username",username).eq("password",password);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    private int insertAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }

    private Admin getAdminByUsername(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        System.out.println(admin);
        return admin;
    }

    private List<Admin> getAccounts(){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",0);
        return adminMapper.selectList(queryWrapper);
    }

    private int removeAdmin(String username){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        int result = adminMapper.delete(queryWrapper);
        return result;
    }

    @Override
    public int getStatusByUsername(String username){
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return adminMapper.selectOne(queryWrapper).getStatus();
    }

    @Override
    public CommonResponse<Object> updateSeller(HttpServletRequest request, Map sellerToUpdate) {
        String usernameFromToken = JWTUtils.verify(request);
        Admin adminByUsername = this.getAdminByUsername(usernameFromToken);
        if(adminByUsername.getStatus()==0){
            return CommonResponse.createForFailure("您无权进行此操作");
        }
        else {
            String username= (String) sellerToUpdate.get("username");
            String password= (String) sellerToUpdate.get("password");
            String realName= (String) sellerToUpdate.get("real_name");
            String email= (String) sellerToUpdate.get("email");
            String phone= (String) sellerToUpdate.get("phone");
            String address= (String) sellerToUpdate.get("address");
            int status = (int) sellerToUpdate.get("status");
            return this.updateAccount(username,password,email,realName,phone,address,status);
        }
    }

    @Override
    public CommonResponse<Object> removeSeller(HttpServletRequest request, Map sellerToRemove) {
        String usernameFromToken = JWTUtils.verify(request);
        Admin adminByUsername = this.getAdminByUsername(usernameFromToken);
        if(adminByUsername.getStatus()==0){
            return CommonResponse.createForFailure("您无权进行此操作");
        }
        else {
            String username = (String) sellerToRemove.get("username");
            int result = this.removeAdmin(username);
            if(result == 0){
                return CommonResponse.createForFailure("删除商家失败");
            }
            else {
                return CommonResponse.createForSuccessMessage("删除商家成功");
            }
        }
    }

    @Override
    public CommonResponse<Object> updateAccount(String username,String password,String email,String realName,String phone,String address,int status){
        // todo: 修改信息，token需要重新生成一个吗？修改信息，username允许修改吗？
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        // username 相当于是微信的微信号，原则上是不允许轻易变的
        // 但是昵称是可以改的，在现在的表里面没有昵称这一项，是否考虑在表中加入一个字段nick_name
        updateWrapper.eq("username",username);
        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setEmail(email);
        admin.setRealName(realName);
        admin.setPhone(phone);
        admin.setAddress(address);
        admin.setStatus(status);
        int result = adminMapper.update(admin, updateWrapper);
        if(result == 0){
            return CommonResponse.createForFailure("用户信息已过期，请重新登录");
        }
        else {
            return CommonResponse.createForSuccessMessage("修改信息成功");
        }
    }

    @Override
    public CommonResponse<Object> getUserInfo(HttpServletRequest request) {
//        System.out.println(request.getHeader("Authorization"));
        String usernameFromToken;
        try {
            usernameFromToken = JWTUtils.verify(request);
        } catch (TokenExpiredException e) {
            return CommonResponse.createForFailure("用户信息已过期，请重新登录");
        }
        Admin admin = this.getAdminByUsername(usernameFromToken);
            if(admin == null){
                return CommonResponse.createForFailure("服务器内部错误，数据库中已无该用户信息");
            }
            else {
                return CommonResponse.createForSuccess(admin);
            }

//        if(usernameFromToken == null){
//            return CommonResponse.createForError("用户信息已过期，请重新登录");
//        }
//        else {
//            Admin admin = this.getAdminByUsername(usernameFromToken);
//            if(admin == null){
//                return CommonResponse.createForError("服务器内部错误，数据库中已无该用户信息");
//            }
//            else {
//                return CommonResponse.createForSuccess("SUCCESS",admin);
//            }
//        }
    }

    @Override
    public CommonResponse<Object> getAllAccounts(HttpServletRequest request) {
        String usernameFromToken = JWTUtils.verify(request);
        Admin adminByUsername = this.getAdminByUsername(usernameFromToken);
        if(adminByUsername.getStatus()==0){
            return CommonResponse.createForFailure("您无权访问该信息");
        }
        List<Admin> admins =this.getAccounts();
        return CommonResponse.createForSuccess(admins);
    }

    @Override
    public CommonResponse<Object> login(String username, String password) {

        if(this.getAdminByUsername(username) == null){
            return CommonResponse.createForFailure("用户名不存在");
        }
        Admin admin = this.getAdminByUsernameAndPassword(username,password);
        if(admin == null){
            return CommonResponse.createForFailure("密码错误");
        }
        else {
//            String token = this.createToken(admin);
            String token = JWTUtils.getToken(admin.getUsername());
            return CommonResponse.createForSuccess(admin,token);
        }
    }

    @Override
    public CommonResponse<Object> register(String username, String password, String email,String realName, String phone, String address) {
        Admin adminByUsername = this.getAdminByUsername(username);
        if(adminByUsername != null){
            return CommonResponse.createForFailure("用户已存在");
        }
        else {
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.setEmail(email);
            admin.setRealName(realName);
            admin.setPhone(phone);
            admin.setAddress(address);
            admin.setStatus(0); // 默认是商家

            int result = this.insertAdmin(admin);
            if(result == 0){
                return CommonResponse.createForFailure("服务器内部错误，未成功向数据库插入数据");
            }
            return CommonResponse.createForSuccessMessage("注册成功");
        }
    }

//    private String createToken(Admin admin){
//
//        Map<String,Object> loginAdmin = new HashMap<>();
//        loginAdmin.put("username",admin.getUsername());
//        loginAdmin.put("email",admin.getEmail());
//        loginAdmin.put("realName",admin.getRealName());
//        loginAdmin.put("phone",admin.getPhone());
//        loginAdmin.put("address",admin.getAddress());
//        loginAdmin.put("status(identity)",admin.getStatus());
//
//        return JWTUtils.getToken(loginAdmin);
//    }
//    private String getUsernameFromToken(HttpServletRequest httpServletRequest){
//        return JWTUtils.verify(httpServletRequest);
//    }
}
