package top.yangcc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.yangcc.config.RabbitMqConfig;
import top.yangcc.entity.MeetingRoom;
import top.yangcc.entity.Message;
import top.yangcc.entity.User;
import top.yangcc.mapper.MeetingMapper;
import top.yangcc.entity.Meeting;
import top.yangcc.mapper.MeetingRoomMapper;
import top.yangcc.mapper.MessageMapper;
import top.yangcc.mapper.UserMapper;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMeetingPageBean;
import top.yangcc.service.MeetingService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 会议
 * @author yangcc
 */
@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {
    /**会议的三种状态*/
    private static final String UNREVIEWED = "未审核";
    private static final String PASS = "审核通过";
    private static final String NOT_PASS = "审核未通过";


    private MeetingMapper meetingMapper;

    private RabbitTemplate rabbitTemplate;

    private MeetingRoomMapper meetingRoomMapper;

    private UserMapper userMapper;

    private MessageMapper messageMapper;

    @Autowired
    public MeetingServiceImpl(MeetingMapper meetingMapper, RabbitTemplate rabbitTemplate, MeetingRoomMapper meetingRoomMapper, UserMapper userMapper, MessageMapper messageMapper) {
        this.meetingMapper = meetingMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.meetingRoomMapper = meetingRoomMapper;
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
    }

    /**新增会议*/
    @Override
    public void add(Meeting meeting) {
        //查询此用户对应的信息
        User user = userMapper.findByUsername(meeting.getSponsor().getUsername());
        meeting.setSponsor(user);
        //获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        meeting.setSponsorTime(dateTimeFormatter.format(dateTime));
        //新建会议
        meetingMapper.add(meeting);
        System.out.println(meeting.getMembers());
        //填入中间表
        for (User member : meeting.getMembers()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("user_id",member.getId());
            map.put("meeting_id",meeting.getId());
            meetingMapper.addMeetingAndUser(map);
        }
    }

    /**审核通过*/
    @Override
    public void pass(Integer id ,String username) {
        //根据id查询当前会议的信息
        Meeting meeting = meetingMapper.findById(id);
        //查询会议室信息
        MeetingRoom meetingRoom = meetingRoomMapper.findByMeetingId(id);
        meeting.setMeetingRoom(meetingRoom);
        //查询审核人的信息
        User user = userMapper.findByUsername(username);
        //获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowTime = dateTimeFormatter.format(dateTime);
        //设置审核人和审核时间
        meeting.setOperator(user);
        meeting.setOperatingTime(nowTime);
        //设置会议批准状态为已经批准
        meeting.setApprovalStatus(PASS);
        //修改数据库 会议状态为通过
        meetingMapper.editApprovalStatus(meeting);

        //查询冲突的会议,根据会议室
        List<Meeting> byConflict = meetingMapper.findByConflict(meeting);
        //如果有冲突,给每个会议的申请人发消息
        if (byConflict!=null && byConflict.size()>0){
            for (Meeting meeting1 : byConflict) {
                String content = "您申请的会议未通过审核,原因是: 会议室冲突";
                sendMessage(meeting1.getId(),meeting1.getSponsor().getId(),nowTime,content);
            }
        }

        //构造Set集合存放冲突的会议
        Set<Meeting> set = new HashSet<>();
        //查询冲突的会议,根据人员
        List<User> users = userMapper.findByMeetingId(id);
        //会议人员是否冲突
        for (User member : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id",member.getId());
            map.put("meeting_id",meeting.getId());
            map.put("start",meeting.getStart());
            map.put("end",meeting.getEnd());
            List<Meeting> conflictUser = meetingMapper.findByConflictUser(map);
            set.addAll(conflictUser);
        }
        //人员冲突的会议室
        for (Meeting meeting1 : set) {
            //查询会议对应的申请人
            Integer sId = meetingMapper.findSponsorById(meeting1.getId());
            User user1 = new User();
            user1.setId(sId);
            meeting1.setSponsor(user1);
            //发送消息
            String content = "您申请的会议未通过审核,原因是: 人员冲突";
            sendMessage(meeting1.getId(),meeting1.getSponsor().getId(),nowTime,content);
            //设置为未通过
            meeting1.setApprovalStatus(NOT_PASS);
            //设置审核人和审核时间
            meeting1.setOperator(user);
            meeting1.setOperatingTime(nowTime);
            //修改状态
            meetingMapper.editApprovalStatus(meeting1);
        }

        //设置会议状态为未通过
        if (byConflict!=null && byConflict.size()>0){
            for (Meeting meeting1 : byConflict) {
                meeting1.setApprovalStatus(NOT_PASS);
                //设置审核人和审核时间
                meeting1.setOperator(user);
                meeting1.setOperatingTime(nowTime);
                meetingMapper.editApprovalStatus(meeting1);
            }
        }

        //获取当前时间戳
        long now = new Date().getTime();
        long start = meeting.getStart().getTime();
        long end = meeting.getEnd().getTime();
        //如果会议还未开始加入消息队列
        if((start-now)>=0){
            //消息1,到指定时间修改会议状态为正在进行
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.MEETING_EXCHANGE, //发送到的交换机
                    RabbitMqConfig.ROUTING_KEY_MEETING, //routingKey
                    meeting, //会议
                    message -> {
                        message.getMessageProperties().setExpiration(start-now+ "");
                        return message;
                    });
            //消息2,到指定时间修改会议室状态为已经结束
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.MEETING_EXCHANGE, //发送到的交换机
                    RabbitMqConfig.ROUTING_KEY_MEETING, //routingKey
                    meeting, //会议
                    message -> {
                        message.getMessageProperties().setExpiration(end-now+ "");
                        return message;
                    });

            //查询会议对应的申请人
            Integer sId = meetingMapper.findSponsorById(meeting.getId());
            User user1 = new User();
            user1.setId(sId);
            meeting.setSponsor(user1);
            //发送消息
            String content = "您申请的会议成功通过审核";
            sendMessage(meeting.getId(),meeting.getSponsor().getId(),nowTime,content);
        }
        //否则表示,这个会议审核的时间太晚了,同样表示未通过审核
        else {
            meeting.setApprovalStatus(NOT_PASS);
            meetingMapper.editApprovalStatus(meeting);
            //查询会议对应的申请人
            Integer sId = meetingMapper.findSponsorById(meeting.getId());
            User user1 = new User();
            user1.setId(sId);
            meeting.setSponsor(user1);
            //发送消息
            String content = "您申请的会议未通过审核,原因是: 审核的时候,次会议已经要结束了,即便通过也无法正常开会";
            sendMessage(meeting.getId(),meeting.getSponsor().getId(),nowTime,content);
        }
    }


    /**审核不通过*/
    @Override
    public void fail(Integer id, String username, String message) {
        //根据id查询当前会议的信息
        Meeting meeting = meetingMapper.findById(id);
        //查询审核人的信息
        User user = userMapper.findByUsername(username);
        //获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //设置审核人,审核时间
        meeting.setOperator(user);
        meeting.setOperatingTime(dateTimeFormatter.format(dateTime));
        //不通过审核
        meeting.setApprovalStatus(NOT_PASS);
        //修改会议状态为未通过
        meetingMapper.editApprovalStatus(meeting);
        //发送消息
    }

    /**审核页分页查询*/
    @Override
    public PageResult findPageByVerify(QueryMeetingPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Meeting> meetings = meetingMapper.findPageByVerify(meeting);
        PageInfo<Meeting> pageInfo = new PageInfo<>(meetings);
        List<Meeting> list = pageInfo.getList();
        //每次分页查询的时候更新下会议的冲突状态
        updateConflictStatus(list);
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**更新会议的冲突状态*/
    private void updateConflictStatus(List<Meeting> list) {
        for (Meeting meeting1 : list) {
            //查询会议对应的会议室
            MeetingRoom room = meetingRoomMapper.findByMeetingId(meeting1.getId());
            meeting1.setMeetingRoom(room);
            //查询此会议的会议室是否冲突,如果不冲突修改状态
            int count1 =  meetingMapper.isConflictforMeetingRoom(meeting1);
            Map<String,Object> map  = new HashMap<>();
            if (count1==0){
                meeting1.setConflictMeetingRoom(false);
                //写入数据库
                map.put("meeting_id",meeting1.getId());
                map.put("conflict_meetingroom",false);
                meetingMapper.editConflictforMeetingRoom(map);
            }else if(count1>0){
                meeting1.setConflictMeetingRoom(true);
                //写入数据库
                map.put("meeting_id",meeting1.getId());
                map.put("conflict_meetingroom",true);
                meetingMapper.editConflictforMeetingRoom(map);
            }
            //查询次会议的人员是否冲突
            List<User> users = userMapper.findByMeetingId(meeting1.getId());
            Set<Boolean> set = new HashSet<>();
            for (User member : users) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("user_id",member.getId());
                map1.put("meeting_id",meeting1.getId());
                map1.put("start",meeting1.getStart());
                map1.put("end",meeting1.getEnd());
                //查询此用户是否有冲突的会议
                int count2 =  meetingMapper.isConflictforUserAndFindPage(map1);
                if (count2==0){
                    set.add(false);
                }else if(count2>0){
                    set.add(true);
                }
            }
            Map<String,Object> map2  = new HashMap<>();
            //如果set集合里有两个,表示冲突
            if (set.size()==2){
                meeting1.setConflictUser(true);
                map2.put("meeting_id",meeting1.getId());
                map2.put("conflict_user",true);
                meetingMapper.editConflictforUser(map2);
            }
            //否则取出数据,看看到底是冲突还是不冲突
            else{
                for (Boolean b : set) {
                    //如果为true表示冲突
                    if (b){
                        meeting1.setConflictUser(true);
                        map2.put("meeting_id",meeting1.getId());
                        map2.put("conflict_user",true);
                    }else {
                        meeting1.setConflictUser(false);
                        map2.put("meeting_id",meeting1.getId());
                        map2.put("conflict_user",false);
                    }
                    meetingMapper.editConflictforUser(map2);
                }
            }
        }
    }

    /**审核历史页分页查询*/
    @Override
    public PageResult findPageByVerifyHistory(QueryMeetingPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Meeting> meetings = meetingMapper.findPageByVerifyHistory(meeting);
        PageInfo<Meeting> pageInfo = new PageInfo<>(meetings);
        List<Meeting> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**正在进行分页查询*/
    @Override
    public PageResult findPageByOngoing(QueryMeetingPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Meeting> meetings = meetingMapper.findPageByOngoing(meeting);
        PageInfo<Meeting> pageInfo = new PageInfo<>(meetings);
        List<Meeting> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**会议历史分页查询*/
    @Override
    public PageResult findPageByHistory(QueryMeetingPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Meeting> meetings = meetingMapper.findPageByHistory(meeting);
        PageInfo<Meeting> pageInfo = new PageInfo<>(meetings);
        List<Meeting> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**申请历史查询*/
    @Override
    public PageResult findPageByApply(QueryMeetingPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Meeting> meetings = meetingMapper.findPageByApply(meeting);
        PageInfo<Meeting> pageInfo = new PageInfo<>(meetings);
        List<Meeting> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**用户历史会议*/
    @Override
    public PageResult findPageByUserHistory(QueryMeetingPageBean queryPageBean,String username) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Map<String,Object> map  = new HashMap<>();
        map.put("meeting",meeting);
        map.put("username",username);
        Page<Integer> meetings = meetingMapper.findPageByUserHistory(map);
        PageInfo<Integer> pageInfo = new PageInfo<>(meetings);
        List<Integer> list = pageInfo.getList();
        List<Meeting> list1 = new ArrayList<>();
        //查询这些会议的详情
        for (Integer integer : list) {
            Meeting byId = meetingMapper.findAllById(integer);
            list1.add(byId);
        }
        long total = pageInfo.getTotal();
        return new PageResult(total,list1);
    }

    /**将要参加的会议*/
    @Override
    public PageResult findPageByFuture(QueryMeetingPageBean queryPageBean,String username) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        Meeting meeting = queryPageBean.getMeeting();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Map<String,Object> map  = new HashMap<>();
        map.put("meeting",meeting);
        map.put("username",username);
        //查询此用户全部待参加的会议
        Page<Integer> meetings = meetingMapper.findPageByFuture(map);
        PageInfo<Integer> pageInfo = new PageInfo<>(meetings);
        List<Integer> list = pageInfo.getList();
        List<Meeting> list1 = new ArrayList<>();
        //查询这些会议的详情
        for (Integer integer : list) {
            Meeting byId = meetingMapper.findAllById(integer);
            list1.add(byId);
        }
        long total = pageInfo.getTotal();
        return new PageResult(total,list1);
    }

    /**将要参加的会议All*/
    @Override
    public List<Meeting> findAllFutureByUsername(String username) {
        return meetingMapper.findAllFutureByUsername(username);
    }

    /**查询会议冲突列表,根据会议室*/
    @Override
    public List<Meeting> findByConflict(Integer id) {
        //查询会议信息
        Meeting meeting = meetingMapper.findById(id);
        //查询会议室信息
        MeetingRoom meetingRoom = meetingRoomMapper.findByMeetingId(id);
        meeting.setMeetingRoom(meetingRoom);
        //查询冲突列表
        return meetingMapper.findByConflict(meeting);
    }

    /**查询会议冲突列表,从根据用户*/
    @Override
    public Set<Meeting> findByConflictUser(Integer id) {
        //查询会议信息
        Meeting meeting = meetingMapper.findById(id);
        //查询此会议对应的用户信息
        List<User> users = userMapper.findByMeetingId(id);
        //构造Set集合
        Set<Meeting> set = new HashSet<>();
        //会议人员是否冲突
        for (User member : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id",member.getId());
            map.put("meeting_id",meeting.getId());
            map.put("start",meeting.getStart());
            map.put("end",meeting.getEnd());
            List<Meeting> conflictUser = meetingMapper.findByConflictUser(map);
            set.addAll(conflictUser);
        }
        System.out.println(set.size());
        //查询冲突列表
        return set;
    }



    /**发送消息*/
    private void sendMessage(Integer meetingId,Integer userId,String sendTime,String content){
        User user = new User();
        user.setId(userId);
        Message message = new Message(null, sendTime, content, user, null, false);
        messageMapper.add(message);
    }
}
