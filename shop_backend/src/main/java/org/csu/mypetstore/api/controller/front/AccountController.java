package org.csu.mypetstore.api.controller.front;

import jakarta.validation.constraints.Pattern;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.util.JwtUtil;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    @ResponseBody
    public CommonResponse<String> login(
            @RequestParam String username,
            @RequestParam String password){
        //发送username，password（以form-data，x-www-form-urlencoded 或 query-param的形式）
        CommonResponse<AccountVO> response = accountService.getAccount(username,password);

        if(response.isSuccess()){
            Map<String,Object> claims = new HashMap<>();
            claims.put("username",username);
            String token = JwtUtil.getToken(claims);
            return CommonResponse.createForSuccess(token);
        }
        return CommonResponse.createForError("wrong password or username");
    }

    @GetMapping("/info")
    @ResponseBody
    public CommonResponse<AccountVO> getLoginAccountInfo(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return accountService.getAccount(username);
    }

    @PostMapping("/register")
    public CommonResponse<AccountVO> register(
            @RequestParam @Pattern(regexp = "^\\S{1,25}$") String username,
            @RequestParam @Pattern(regexp = "^\\S{1,25}$")String password){
        CommonResponse<AccountVO> isOK = accountService.getAccount(username);
        if(isOK.getData() == null){
            return accountService.addAccount(username,password);
        }
        System.out.println(isOK.getData());
        return CommonResponse.createForError("username already exist");
    }

    @PostMapping("/info")
    public CommonResponse<AccountVO> updateAccountInfo(@RequestBody AccountVO accountVO, @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return accountService.updateAccount(accountVO);
    }

    @PostMapping("password")
    public CommonResponse<AccountVO> updateAccountPassword(@RequestBody String password, @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return accountService.updateAccount(password, username);
    }
}
