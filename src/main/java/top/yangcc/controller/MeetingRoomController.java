package top.yangcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.entity.Faculty;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMeetingRoomPageBean;
import top.yangcc.response.Result;
import top.yangcc.entity.MeetingRoom;
import top.yangcc.service.MeetingRoomService;
import top.yangcc.utils.PictureVerify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议室相关的controller
 * @author yangcc
 */
@RestController
@RequestMapping("/meetingRoom")
public class MeetingRoomController {

    private MeetingRoomService meetingRoomService;

    @Autowired
    public MeetingRoomController(MeetingRoomService meetingRoomService) {
        this.meetingRoomService = meetingRoomService;
    }

    /**
     * 图片上传+数据写入
     * @return 是否成功
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile file,String info, String room,Integer capacity,String status,String name,String faculty){
        try {
            //判断文件格式
            boolean isNull = PictureVerify.isPictureAndIsNull(file);
            if (!isNull){
                return Result.fail("文件不是图片或者为空");
            }
            MeetingRoom meetingRoom = new MeetingRoom(room,capacity,status,info,new Faculty(name,faculty));
            meetingRoomService.upload(file,meetingRoom);
            return Result.success("保存成功");
        } catch (Exception e) {
            return Result.fail("保存失败");
        }
    }

    /**
     * 分页查询
     * @param queryPageBean 查询条件等
     * @return 返回分页信息
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryMeetingRoomPageBean queryPageBean){
        try {
            return meetingRoomService.findPage(queryPageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new PageResult(1L,null);
        }
    }

    /**
     * 修改会议室信息
     * @param meetingRoom 会议室对象
     * @return 是否成功
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody MeetingRoom meetingRoom){
        try {
            meetingRoomService.edit(meetingRoom);
            return Result.success("修改会议室信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改会议室信息失败");
        }
    }

    /**
     * 表示附带上传图片的修改会议室信息
     * @return 是否成功
     */
    @RequestMapping("/editAndUpload")
    public Result editAndUpload(MultipartFile file,String info, String room,Integer capacity,String status,String name,String faculty){
        try {
            MeetingRoom meetingRoom = new MeetingRoom(room,capacity,status,info,new Faculty(name,faculty));
            meetingRoomService.editAndUpload(file,meetingRoom);
            return Result.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("保存失败");
        }
    }


    /**
     * 删除会议室
     * @param id 保存会议室id
     * @return 是否成功
     */
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        try {
            meetingRoomService.delete(id);
            return Result.success("删除会议室成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除会议室失败");
        }
    }


    /**
     * 根据院系查询会议室
     * @param id facultyId
     * @return list
     */
    @RequestMapping("/findByFacultyId/{id}")
    public Result findByFaculty(@PathVariable Integer id){
        try {
            List<MeetingRoom> meetingRoom = meetingRoomService.findByFacultyId(id);
            return Result.success(meetingRoom);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询失败");
        }
    }
}
