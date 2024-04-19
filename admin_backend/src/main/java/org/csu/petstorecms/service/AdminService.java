package org.csu.petstorecms.service;

import javax.servlet.http.HttpServletRequest;
import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.entity.Admin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//import javax.servlet.http.HttpServletRequest;

public interface AdminService {

    /**
     * 登录逻辑
     * @param username
     * @param password
     * @return
     */
    public CommonResponse<Object> login(String username, String password);

    /**
     * 注册逻辑
     * @param
     * @return
     */
    public CommonResponse<Object> register(String username, String password, String email,String realName, String phone, String address);

    /**
     * 用户修改信息
     * 用户在修改信息界面提交之后，把所有信息封装到一个Admin对象中，传过来
//     * @param admin
     * @return
     */
    public CommonResponse<Object> updateAccount(String username,String password,String email,String realName,String phone,String address,int status);
    /**
     * 获取用户信息
     * @param request
     * @return
     */
    public CommonResponse<Object> getUserInfo(HttpServletRequest request);

    /**
     * 管理员获取所有的商家的信息
     */
    public CommonResponse<Object> getAllAccounts(HttpServletRequest request);

    public int getStatusByUsername(String username);

    public CommonResponse<Object> updateSeller(HttpServletRequest request,@RequestBody Map sellerToUpdate);

    public CommonResponse<Object> removeSeller(HttpServletRequest request,@RequestBody Map sellerToRemove);
}
