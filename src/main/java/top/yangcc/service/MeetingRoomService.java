package top.yangcc.service;

import org.springframework.web.multipart.MultipartFile;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMeetingRoomPageBean;
import top.yangcc.entity.MeetingRoom;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 会议室服务接口
 * @author yangcc
 */
public interface MeetingRoomService {


    /**
     * 根据前端传来的图片文件，以及表单信息进行处理
     * @param file 文件
     * @param meetingRoom meetingRoom对象
     * @throws  IOException io异常
     */
    void upload(MultipartFile file, MeetingRoom meetingRoom) throws IOException;

    /**
     * 分页查询
     * @param queryPageBean 查询条件等
     * @return 返回分页信息
     */
    PageResult findPage(QueryMeetingRoomPageBean queryPageBean);

    /**
     * 删除会议室
     * @param id 会议室id
     */
    void  delete(Integer id);

    /**
     * 更新会议室
     * @param meetingRoom 会议室对象
     */
    void edit(MeetingRoom meetingRoom);

    /**
     * 更新会议室信息并且修改会议室图片
     * @param file 会议室的图片
     * @param meetingRoom 会议室信息
     * @throws  IOException io异常
     */
    void editAndUpload(MultipartFile file, MeetingRoom meetingRoom) throws IOException;

    /**
     * 根据院系，查询会议室
     * @param id 院系Id
     * @return 会议室地点，可容纳人数
     */
    List<MeetingRoom> findByFacultyId(Integer id);
}
