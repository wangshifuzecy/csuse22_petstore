package org.csu.petstorecms.controller;
import org.csu.petstorecms.common.CommonResponse;
import org.csu.petstorecms.service.AdminService;
import org.csu.petstorecms.service.impl.ItemServiceImpl;
import org.csu.petstorecms.utils.JWTUtils;
import org.csu.petstorecms.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
public class itemController {
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private AdminService adminService;
    @GetMapping("/")
    public String getname()
    {
        return JWTUtils.getToken("abc");
    }

    @GetMapping("/itemlist")
    public CommonResponse<Object> getItemlist(HttpServletRequest httpServletRequest)
    {
        String username=JWTUtils.verify(httpServletRequest);
        if(adminService.getStatusByUsername(username)==1){
            List<ItemVO> itemVOList=itemService.getItemList();
            return CommonResponse.createForSuccess(itemVOList);
        }
        else{
            List<ItemVO> itemVOList=itemService.getSomeItem(username);
            return CommonResponse.createForSuccess(itemVOList);
        }
    }
    @PostMapping("/newitem")
    public CommonResponse<Object> addNewItem(@RequestBody ItemVO itemVO, HttpServletRequest httpServletRequest)
    {
        String username=JWTUtils.verify(httpServletRequest);
        if(itemService.addItem(itemVO)) return CommonResponse.createForSuccessMessage("添加成功");
        else return CommonResponse.createForFailure("商品已存在");

    }
    @PostMapping("/removeitem")
    public CommonResponse<Object> removeItem(@RequestBody Map item,HttpServletRequest httpServletRequest){
        String username=JWTUtils.verify(httpServletRequest);
        String itemid= (String) item.get("itemid");
        return itemService.removeItemById(itemid);
    }
    @PostMapping("/updateitem")
    public CommonResponse<Object> updateItem(@RequestBody ItemVO itemVO,HttpServletRequest httpServletRequest){
        String username=JWTUtils.verify(httpServletRequest);
        return itemService.updateItem(itemVO);
    }

}
