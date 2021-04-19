package top.yangcc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.yangcc.entity.Meeting;
import top.yangcc.entity.Message;
import top.yangcc.entity.User;
import top.yangcc.mapper.MeetingMapper;
import top.yangcc.mapper.MessageMapper;
import top.yangcc.mapper.UserMapper;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMessagePageBean;
import top.yangcc.service.MessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author yangcc
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private MessageMapper messageMapper;

    private UserMapper userMapper;

    private MeetingMapper meetingMapper;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper, UserMapper userMapper, MeetingMapper meetingMapper) {
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.meetingMapper = meetingMapper;
    }

    /**分页查询**/
    @Override
    public PageResult findPage(QueryMessagePageBean queryMessagePageBean) {
        Integer currentPage = queryMessagePageBean.getCurrentPage();
        Integer pageSize = queryMessagePageBean.getPageSize();
        Message message = queryMessagePageBean.getMessage();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Message> meetings = messageMapper.findPage(message);
        PageInfo<Message> pageInfo = new PageInfo<>(meetings);
        List<Message> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }


    /**查询消息详情*/
    @Override
    public Map<String, Object> findDetail(Integer id) {
        //根据id查询会议详情
        Meeting meeting  = meetingMapper.findByMessageId(id);
        //根据id查询收件人信息
        User user = userMapper.findByMessageId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("user",user);
        map.put("meeting",meeting);
        return map;
    }

    /**修改消息内容*/
    @Override
    public void edit(Integer id, String content) {
        Map<String,Object> map  = new HashMap<>();
        map.put("id",id);
        map.put("content",content);
        messageMapper.edit(map);
    }

    @Override
    public PageResult findPageByUser(QueryMessagePageBean queryMessagePageBean) {
        Integer currentPage = queryMessagePageBean.getCurrentPage();
        Integer pageSize = queryMessagePageBean.getPageSize();
        Message message = queryMessagePageBean.getMessage();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Message> meetings = messageMapper.findPageByUser(message.getAddressee().getUsername());
        PageInfo<Message> pageInfo = new PageInfo<>(meetings);
        List<Message> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**修改消息为已读*/
    @Override
    public void haveRead(Integer id) {
        messageMapper.haveRead(id);
    }

    @Override
    public void haveReadAll(String username) {
        //查看此用户全部未读的消息
       List<Message> message =  messageMapper.findAllReadMessage(username);
       //设置消息为已读
        for (Message message1 : message) {
            messageMapper.haveRead(message1.getId());
        }
    }

    /**查询全部未读消息*/
    @Override
    public List<Message> findAllReadMessage(String username) {
        return messageMapper.findAllReadMessage(username);
    }
}
