package top.yangcc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 获取全部的菜单
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
    /**
     * 获取此用户对应的菜单
     */
    @SaCheckLogin
    @RequestMapping("/findByUsername/{username}")
    public Result findByUsername(@PathVariable String username){
        try {
            List<Menu> menu =  menuService.findByUsername(username);
            return Result.success(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询菜单失败");
        }

    }
}
