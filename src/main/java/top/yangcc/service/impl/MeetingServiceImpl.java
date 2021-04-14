package top.yangcc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yangcc.mapper.MeetingMapper;
import top.yangcc.entity.Meeting;
import top.yangcc.service.MeetingService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangcc
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;


    @Override
    public void add(Meeting meeting) {
        //新建会议
    }
}
