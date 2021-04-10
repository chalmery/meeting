package top.yangcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.entity.Menu;
import top.yangcc.response.Result;
import top.yangcc.service.MenuService;

import java.util.List;

/**
 * 菜单controller
 * @author yangcc
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 获取全部的菜单
     *     @SaCheckLogin
     * 只有登陆后才可访问
     */

    @RequestMapping("/findAllMenu")
    public Result findAllMenu(){
        try {
            List<Menu> allMenu = menuService.findAllMenu();
            return Result.success("获取菜单成功",allMenu);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取菜单失败");
        }
    }
}
