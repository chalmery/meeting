package top.yangcc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.entity.Faculty;
import top.yangcc.request.User;
import top.yangcc.response.ConflictUser;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryUserPageBean;
import top.yangcc.response.Result;
import top.yangcc.service.UserService;
import top.yangcc.utils.PictureVerify;

import java.io.IOException;
import java.util.List;


/**
 * 登录相关的controller
 * @author yangcc
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** 查询用户头像,根据用户名 */
    @RequestMapping("/findAvatarByUsername/{username}")
    public Result findAvatarByUsername(@PathVariable String username){
        try {
            String avatar =  userService.findAvatarByUsername(username);
            return Result.success("成功",avatar);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取头像失败");
        }
    }

    /** 查询用户信息,根据用户名*/
    @RequestMapping("/findByUsername/{username}")
    public Result findByUsername(@PathVariable String username){
        try {
            top.yangcc.entity.User user = userService.findByUsername(username);
            return Result.success(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询用户信息失败");
        }
    }

    /** 查询所有的用户信息,根据院系id */
    @RequestMapping("/findByFacultyId/{id}")
    public Result findByFacultyId(@PathVariable Integer id){
        try {
            List<top.yangcc.entity.User> users =  userService.findByFacultyId(id);
            return Result.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }
    /** 分页查询 */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryUserPageBean queryUserPageBean){
        try {
            PageResult pageResult = userService.findUserByCondition(queryUserPageBean);
            return Result.success("查询管理员信息成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询管理员信息失败");
        }
    }
    /** 新增用户 */
    @RequestMapping("/add")
    public Result add(MultipartFile avatar, String username, String password, String faculty, String role){
        try {
            //查询用户是否已经存
            Integer countByUsername = userService.findCountByUsername(username);
            if (countByUsername!=0){
                return Result.fail("该用户已经存在");
            }
            //如果图片为空,则使用默认头像
            if (avatar==null){
                userService.addForNoAvatar(username,password,faculty,role);
            }else {
                //校验
                boolean isPicture = PictureVerify.isPicture(avatar);
                if (!isPicture){
                    return Result.fail("不是图片");
                }
                //新增,带头像
                userService.add(avatar,username,password,faculty,role);
            }
            return Result.success("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("新增失败");
        }
    }

    /** 修改用户*/
    @RequestMapping("/edit")
    public Result edit(@RequestBody User user){
        try {
            userService.edit(user.getId(),user.getUsername(),user.getPassword(),user.getFaculty(),user.getRole());
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    /** 修改用户+修改头像*/
    @RequestMapping("/editAndUpload")
    public Result editAndUpload(MultipartFile avatar,Integer id,String username,String password,String faculty,String role){
        try {
            boolean isNull = PictureVerify.isPictureAndIsNull(avatar);
            if (!isNull){
                return Result.fail("不是图片,或者图片为空");
            }
            userService.editAdnUpload(id,avatar,username,password,faculty,role);
            return Result.success("修改成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }


    /*** 删除用户*/
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        try {
            userService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
    }

    /**用户自定义修改,不带头像*/
    @RequestMapping("/userEdit")
    public Result userEdit(@RequestBody top.yangcc.entity.User user){
        try {
            userService.userEdit(user);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }
    /** 用户自定义修改头像 */
    @RequestMapping("/userEditAvatar")
    public Result userEditAvatar(MultipartFile file,Integer id){
        try {
            userService.userEditAvatar(file,id);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    /**查询会议对应的用户信息*/
    @RequestMapping("/findByMeetingId/{id}")
    public Result findByMeetingId(@PathVariable Integer id){
        try {
            List<top.yangcc.entity.User> users = userService.findByMeetingId(id);
            return Result.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }

    /**查询会议冲突的用户*/
    @RequestMapping("/findByConflict/{id}")
    public Result findByConflict(@PathVariable Integer id){
        try {
            List<ConflictUser> users = userService.findByConflict(id);
            return Result.success(users);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }
}
