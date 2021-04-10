package top.yangcc.mapper;

import org.springframework.stereotype.Repository;
import top.yangcc.entity.Meeting;

import java.util.Map;

/**
 * @author yangcc
 */
@Repository
public interface MeetingMapper {
    /**
     * 新建会议
     * @param meeting 会议对象
     */
    void  add(Meeting meeting);

    /**
     * 设置中间表
     * @param map map
     */
    void addMeetingAndUser(Map<String,Integer> map);

}
