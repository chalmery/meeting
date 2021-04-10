package top.yangcc.service;

import top.yangcc.entity.Meeting;

/**
 * @author yangcc
 */
public interface MeetingService {

    /**
     * 新建会议
     * @param meeting 会议对象
     */
    void add(Meeting meeting);
}
