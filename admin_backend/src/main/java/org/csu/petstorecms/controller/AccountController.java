package org.csu.petstorecms.controller;

import com.auth0.jwt.JWT;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.entity.Admin;
import org.csu.petstorecms.service.AdminService;
import org.csu.petstorecms.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
@RequestMapping
public class AccountController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/user")
    @ResponseBody
    public CommonResponse<Object> login(@RequestBody Map login){
        String username= (String) login.get("username");
        String password= (String) login.get("password");
        return adminService.login(username,password);
    }

    @PostMapping("/newuser")
    @ResponseBody
    public CommonResponse<Object> register(@RequestBody Map register){
        String username= (String) register.get("username");
        String password= (String) register.get("password");
        String realName= (String) register.get("real_name");
        String email= (String) register.get("email");
        String phone= (String) register.get("phone");
        String address= (String) register.get("address");
        return adminService.register(username,password,email, realName, phone,address);
    }

    @GetMapping("/userinfo")
    @ResponseBody
    public CommonResponse<Object> getUserInfo(HttpServletRequest request){
        return adminService.getUserInfo(request);
    }

    @PostMapping("/newuserinfo")
    @ResponseBody
    public CommonResponse<Object> updateAccount(@RequestBody Map updateUser){
        String username= (String) updateUser.get("username");
        String password= (String) updateUser.get("password");
        String realName= (String) updateUser.get("real_name");
        String email= (String) updateUser.get("email");
        String phone= (String) updateUser.get("phone");
        String address= (String) updateUser.get("address");
        int status = (int) updateUser.get("status");
        return adminService.updateAccount(username,password,email, realName, phone,address,status);
    }

    @GetMapping("/sellers")
    @ResponseBody
    public CommonResponse<Object> getAllAccounts(HttpServletRequest request){
        return adminService.getAllAccounts(request);
    }

    @PostMapping("/updateseller")
    @ResponseBody
    public CommonResponse<Object> updateSeller(HttpServletRequest request,@RequestBody Map sellerToUpdate){
        return adminService.updateSeller(request,sellerToUpdate);
    }

    @PostMapping("/removeseller")
    @ResponseBody
    public CommonResponse<Object> removeSeller(HttpServletRequest request,@RequestBody Map sellerToRemove){
        return adminService.removeSeller(request,sellerToRemove);
    }
}
