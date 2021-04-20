package top.yangcc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yangcc.entity.Role;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryPageBean;
import top.yangcc.response.Result;
import top.yangcc.service.RoleService;

import java.util.List;


/**
 * 角色Controller
 * @author yangcc
 */
@RestController
@SaCheckLogin
@RequestMapping("/role")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 分页查询
     */
    @RequestMapping("/findPage")
    @SaCheckPermission("role")
    public Result findPage(@RequestBody  QueryPageBean queryPageBean){
        try {
            PageResult pageResult = roleService.findByCondition(queryPageBean);
            return Result.success("查询角色信息成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询角色信息失败");
        }
    }

    /**查询所有的是管理员的角色*/
    @RequestMapping("/findAllAdminRole")
    @SaCheckPermission("admin")
    public Result findAllAdminRole(){
        try {
            List<Role> roles = roleService.findAllAdminRole();
            return Result.success("查询角色信息成功",roles);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询角色信息失败");
        }
    }

    /**查询所有的是User的角色*/
    @RequestMapping("/findAllUserRole")
    @SaCheckPermission("user")
    public Result findAllUserRole(){
        try {
            List<Role> roles = roleService.findAllUserRole();
            return Result.success("查询角色信息成功",roles);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询角色信息失败");
        }
    }

    /**新建角色*/
    @RequestMapping("/add")
    @SaCheckPermission("role")
    public Result add(@RequestBody Role role){
        try {
            roleService.add(role);
            return Result.success("新建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("新建失败");
        }
    }

    /**修改角色*/
    @RequestMapping("/edit")
    @SaCheckPermission("role")
    public Result edit(@RequestBody Role role){
        try {
            roleService.edit(role);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    /**删除角色*/
    @RequestMapping("/delete/{id}")
    @SaCheckPermission("role")
    public Result delete(@PathVariable Integer id){
        try {
            roleService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
    }
}
