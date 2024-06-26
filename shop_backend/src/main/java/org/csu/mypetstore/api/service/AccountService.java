package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.vo.AccountVO;

public interface AccountService {
    CommonResponse<AccountVO> getAccount(String username, String password);
    CommonResponse<AccountVO> getAccount(String username);
    CommonResponse<AccountVO> addAccount(String username,String password);
    CommonResponse<AccountVO> updateAccount(AccountVO accountVO);
    CommonResponse<AccountVO> updateAccount(String password, String username);
}
