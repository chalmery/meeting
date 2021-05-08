package top.yangcc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.entity.Meeting;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMeetingPageBean;
import top.yangcc.response.QueryPageBean;
import top.yangcc.response.Result;
import top.yangcc.service.MeetingService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 会议相关
 * @author yangcc
 */
@RestController
@SaCheckLogin
@RequestMapping("/meeting")
public class MeetingController {

    private MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**新增会议*/
    @RequestMapping("/add")
    @SaCheckPermission("apply")
    public Result add(@RequestBody Meeting meeting){
        try {
            meetingService.add(meeting);
            return Result.success("申报成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("申报失败");
        }

    }

    /**审核通过*/
    @RequestMapping("/pass/{id}/{username}")
    @SaCheckPermission("meetingVerify")
    public Result pass(@PathVariable  Integer id,@PathVariable String username){
        try {
            meetingService.pass(id,username);
            return Result.success("审核通过");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("审核通过失败");
        }
    }

    /**审核不通过*/
    @RequestMapping("/fail")
    @SaCheckPermission("meetingVerify")
    public Result fail(@RequestBody Map<String,Object> map){
        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        String message = (String) map.get("message");
        try {
            meetingService.fail(id,username,message);
            return Result.success("修改状态成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改状态失败");
        }
    }

    /**审批页分页查询*/
    @RequestMapping("/findPageByVerify")
    @SaCheckPermission("meetingVerify")
    public Result findPageByVerify(@RequestBody QueryMeetingPageBean queryPageBean){
        try {
            PageResult pageResult =  meetingService.findPageByVerify(queryPageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }

    /**查询会议冲突列表*/
    @RequestMapping("/findByConflict/{id}")
    @SaCheckPermission("meetingVerify")
    public Result findByConflict(@PathVariable Integer id){
        try {
            List<Meeting> list =  meetingService.findByConflict(id);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }


    /**查询会议冲突列表,根据人员查询*/
    @RequestMapping("/findByConflictUser/{id}")
    @SaCheckPermission("meetingVerify")
    public Result findByConflictUser(@PathVariable Integer id){
        try {
            Set<Meeting> list =  meetingService.findByConflictUser(id);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }

    /**分页查询会议审核历史*/
    @RequestMapping("/findPageByVerifyHistory")
    @SaCheckPermission("verifyHistory")
    public Result findPageByVerifyHistory(@RequestBody QueryMeetingPageBean queryPageBean){
        try {
            PageResult pageResult =  meetingService.findPageByVerifyHistory(queryPageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }


    /**分页查询会议审核历史*/
    @RequestMapping("/findPageByOngoing")
    @SaCheckPermission("meetingOngoing")
    public Result findPageByOngoing(@RequestBody QueryMeetingPageBean queryPageBean){
        try {
            PageResult pageResult =  meetingService.findPageByOngoing(queryPageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }

    /**分页查询会议审核历史*/
    @RequestMapping("/findPageByHistory")
    @SaCheckPermission("meetingHistory")
    public Result findPageByHistory(@RequestBody QueryMeetingPageBean queryPageBean){
        try {
            PageResult pageResult =  meetingService.findPageByHistory(queryPageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }

    /**用户申请历史会议查询*/
    @RequestMapping("/findPageByApply")
    @SaCheckPermission("applyHistory")
    public Result findPageByApply(@RequestBody QueryMeetingPageBean queryPageBean){
        try {
            PageResult pageResult =  meetingService.findPageByApply(queryPageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }

    /**用户历史会议查询*/
    @RequestMapping("/findPageByUserHistory/{username}")
    @SaCheckPermission("history")
    public Result findPageByUserHistory(@RequestBody QueryMeetingPageBean queryPageBean,@PathVariable String username){
        try {
            PageResult pageResult =  meetingService.findPageByUserHistory(queryPageBean,username);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }


    /**将要进行的查询*/
    @RequestMapping("/findPageByFuture/{username}")
    @SaCheckPermission("future")
    public Result findPageByFuture(@RequestBody QueryMeetingPageBean queryPageBean,@PathVariable String username){
        try {
            PageResult pageResult =  meetingService.findPageByFuture(queryPageBean,username);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }



    /**查询全部要参加的会议*/
    @RequestMapping("/findAllFutureByUsername/{username}")
    @SaCheckPermission("home")
    public Result findAllFutureByUsername(@PathVariable String username){
        try {
            List<Meeting> list =  meetingService.findAllFutureByUsername(username);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询会议信息失败");
        }
    }
}
