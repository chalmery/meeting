package top.yangcc.mapper;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.MeetingRoom;

import java.util.HashMap;
import java.util.List;

/**
 * 会议室相关的持久层接口
 * @author yangcc
 */
@Repository
public interface MeetingRoomMapper {
    /**
     * 新建会议室
     * @param meetingRoom 会议室对象
     */
    void add(MeetingRoom meetingRoom);

    /**
     * 删除会议室
     * @param id 会议室id
     */
    void delete(Integer id);

    /**
     * 更新会议室
     * @param meetingRoom 会议室对象
     */
    void edit(MeetingRoom meetingRoom);

    /**
     * 条件查询
     * @param meetingRoom 查询条件
     * @return 返回分页信息
     */
    Page<MeetingRoom> findByCondition(MeetingRoom meetingRoom);

    /**
     * 根据会议室id，查询到图片名称
     * @param id 会议室id
     * @return 图片名称
     */
    String findImgById(Integer id);

    /**
     * 根据院系，查询会议室
     * @param faculty 院系
     * @return 会议室点location
     */
    List<HashMap<String,String>> findByFaculty(String faculty);
}
