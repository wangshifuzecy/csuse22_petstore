package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Account;
import org.csu.mypetstore.api.entity.BannerData;
import org.csu.mypetstore.api.entity.Profile;
import org.csu.mypetstore.api.entity.SignOn;
import org.csu.mypetstore.api.persistence.AccountMapper;
import org.csu.mypetstore.api.persistence.BannerDataMapper;
import org.csu.mypetstore.api.persistence.ProfileMapper;
import org.csu.mypetstore.api.persistence.SignOnMapper;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BannerDataMapper bannerDataMapper;
    @Autowired
    private SignOnMapper signOnMapper;
    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public CommonResponse<AccountVO> getAccount(String username, String password) {
        QueryWrapper<SignOn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        SignOn signOn = signOnMapper.selectOne(queryWrapper);
        if(signOn == null){
            return CommonResponse.createForError("password or username error");
        }
        return getAccount(username);
    }
    @Override
    public CommonResponse<AccountVO> getAccount(String username) {
        Account account = accountMapper.selectById(username);
        if(account == null){
            return CommonResponse.createForError("fail to get account information");
        }
        Profile profile = profileMapper.selectById(username);
        if(profile == null){
            return CommonResponse.createForError("no profile whose username is " + username);
        }
        BannerData bannerData = bannerDataMapper.selectById(profile.getFavouriteCategory());

        return CommonResponse.createForSuccess(entityToVO(account,profile,bannerData));
    }

    @Override
    public CommonResponse<AccountVO> addAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        Profile profile = new Profile();
        profile.setUserId(username);
        SignOn signOn = new SignOn();
        signOn.setUsername(username);
        signOn.setPassword(password);
        accountMapper.insert(account);
        profileMapper.insert(profile);
        signOnMapper.insert(signOn);
        return getAccount(username);
    }

    @Override
    public CommonResponse<AccountVO> updateAccount(AccountVO accountVO) {
        System.out.println(accountVO);
        Account account = new Account();
        account.setUsername(accountVO.getUsername());
        account.setEmail(accountVO.getEmail());
        account.setFirstName(accountVO.getFirstName());
        account.setLastName(accountVO.getLastName());
        account.setStatus(accountVO.getStatus());
        account.setAddress1(accountVO.getAddress1());
        account.setAddress2(accountVO.getAddress2());
        account.setCity(accountVO.getCity());
        account.setCountry(accountVO.getCountry());
        account.setState(accountVO.getState());
        account.setZip(accountVO.getZip());
        account.setPhone(accountVO.getPhone());
        System.out.println(account);
        accountMapper.updateById(account);

        Profile profile = new Profile();
        profile.setUserId(accountVO.getUsername());
        profile.setFavouriteCategory(accountVO.getFavouriteCategory());
        profile.setBannerOption(accountVO.isBannerOption() ? 1 : 0);
        profile.setListOption(accountVO.isListOption() ? 1 : 0);
        profile.setLanguagePreference(accountVO.getLanguagePreference());
        profileMapper.updateById(profile);
        return getAccount(accountVO.getUsername());
    }

    private AccountVO entityToVO(Account account, Profile profile, BannerData bannerData){
        AccountVO accountVO = new AccountVO();

        //account table
        accountVO.setUsername(account.getUsername());
        accountVO.setPassword("");//不设置明文
        accountVO.setEmail(account.getEmail());
        accountVO.setFirstName(account.getFirstName());
        accountVO.setLastName(account.getLastName());
        accountVO.setStatus(account.getStatus());
        accountVO.setAddress1(account.getAddress1());
        accountVO.setAddress2(account.getAddress2());
        accountVO.setCity(account.getCity());
        accountVO.setCountry(account.getCountry());
        accountVO.setState(account.getState());
        accountVO.setZip(account.getZip());
        accountVO.setPhone(account.getPhone());

        //profile table
        accountVO.setLanguagePreference(profile.getLanguagePreference());
        accountVO.setBannerOption(profile.getBannerOption() == 1);
        accountVO.setListOption(profile.getListOption() == 1);

        //banner table
        if(profile.getBannerOption() == 1){
            accountVO.setFavouriteCategory(profile.getFavouriteCategory());
            accountVO.setBannerName(bannerData.getBannerName());
        }else{
            accountVO.setFavouriteCategory("");
            accountVO.setBannerName("");
        }

        return accountVO;
    }

    @Override
    public CommonResponse<AccountVO> updateAccount(String password, String username) {
        SignOn signOn = new SignOn();
        signOn.setUsername(username);
        signOn.setPassword(password);
        signOnMapper.updateById(signOn);
        return getAccount(username);
    }
}
