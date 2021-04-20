package top.yangcc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.entity.Faculty;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryPageBean;
import top.yangcc.response.Result;
import top.yangcc.service.FacultyService;
import top.yangcc.service.UserService;
import java.util.HashMap;
import java.util.List;

/**
 * 院系Controller
 * @author yangcc
 */
@RestController
@SaCheckLogin
@RequestMapping("/faculty")
public class FacultyController {

    private FacultyService facultyService;

    private UserService userService;

    @Autowired
    public FacultyController(FacultyService facultyService, UserService userService) {
        this.facultyService = facultyService;
        this.userService = userService;
    }

    /**
     * 查询学院信息
     */
    @RequestMapping("/findFacultyNameForTeach")
    @SaCheckPermission(value = {"user","admin"},mode = SaMode.OR)
    public Result findFacultyNameForTeach(){
        try {
            List<String> facultyNameForTeach = facultyService.findFacultyNameForTeach();
            return Result.success(facultyNameForTeach);
        } catch (Exception e) {
            return Result.fail("查询学院信息失败");
        }
    }
    /**
     * 查询全部楼信息
     */
    @RequestMapping("/findAll")
    @SaCheckPermission(value = {"meetingRoom","apply"},mode = SaMode.OR)
    public Result findAll(){
        try {
            List<Faculty> facultyNameForTeach = facultyService.findAll();
            return Result.success(facultyNameForTeach);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询学院信息失败");
        }
    }

    /**
     * 分页查询
     * @param queryPageBean 查询条件等
     * @return 返回分页信息
     */
    @RequestMapping("/findPage")
    @SaCheckPermission("faculty")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            return facultyService.findPage(queryPageBean);
        } catch (Exception e) {
            return new PageResult(0L,null);
        }
    }


    /**
     * 新增院系信息
     * @param faculty  院系
     * @return 前后端统一的返回
     */
    @RequestMapping("/add")
    @SaCheckPermission("faculty")
    public Result add(@RequestBody Faculty faculty){
        try {
            facultyService.add(faculty);
            return Result.success("新增院系成功");
        } catch (Exception e) {
            return Result.fail("新增院系失败");
        }
    }


    /**
     * 编辑院系信息
     * @param faculty  院系
     * @return 统一的返回
     */
    @RequestMapping("/edit")
    @SaCheckPermission("faculty")
    public Result edit(@RequestBody Faculty faculty){
        System.out.println(faculty);
        try {
            facultyService.edit(faculty);
            return  Result.success("修改院系成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail( "修改院系失败");
        }
    }


    /**
     * 删除院系信息
     * @param id 院系id
     * @return 是否成功
     */
    @RequestMapping("/delete/{id}")
    @SaCheckPermission("faculty")
    public Result delete(@PathVariable Integer id){
        try {
            //查询用户个数
            Integer count = userService.findUserCountByFacultyId(id);
            if (count>0){
                return Result.fail("还有对应的用户,无法删除");
            }
            //删除
            facultyService.delete(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删失败");
        }
    }

}
