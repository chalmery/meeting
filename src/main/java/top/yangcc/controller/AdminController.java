package top.yangcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.request.User;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryUserPageBean;
import top.yangcc.response.Result;
import top.yangcc.service.AdminService;
import top.yangcc.utils.PictureVerify;

import java.io.IOException;

/**
 * @author yangcc
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /** 分页查询 */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryUserPageBean queryUserPageBean){
        try {
            PageResult pageResult = adminService.findAdminByCondition(queryUserPageBean);
            return Result.success("查询管理员信息成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询管理员信息失败");
        }
    }
    /** 新增管理员 */
    @RequestMapping("/add")
    public Result add(MultipartFile avatar,String username,String password,String faculty,String role){
        try {
            //查询用户是否已经存
            Integer countByUsername = adminService.findCountByUsername(username);
            if (countByUsername!=0){
                return Result.fail("该用户已经存在");
            }
            //如果图片为空,则使用默认头像
            if (avatar==null){
                adminService.addForNoAvatar(username,password,faculty,role);
            }else {
                //校验
                boolean isPicture = PictureVerify.isPicture(avatar);
                if (!isPicture){
                    return Result.fail("不是图片");
                }
                //新增,带头像
                adminService.add(avatar,username,password,faculty,role);
            }
            return Result.success("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("新增失败");
        }
    }

    /** 修改管理员*/
    @RequestMapping("/edit")
    public Result edit(@RequestBody User user){
        try {
            adminService.edit(user.getId(),user.getUsername(),user.getPassword(),user.getFaculty(),user.getRole());
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    /** 修改管理员+修改头像*/
    @RequestMapping("/editAndUpload")
    public Result editAndUpload(MultipartFile avatar,Integer id,String username,String password,String faculty,String role){
        try {
            boolean isNull = PictureVerify.isPictureAndIsNull(avatar);
            if (!isNull){
                return Result.fail("不是图片,或者图片为空");
            }
            adminService.editAdnUpload(id,avatar,username,password,faculty,role);
            return Result.success("修改成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }


    /*** 删除管理员*/
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        try {
            adminService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
    }
}
