package top.yangcc.mapper;


import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.Message;

import java.util.List;
import java.util.Map;


/**
 * 消息
 * @author yangcc
 */
@Repository
public interface MessageMapper {

    /**
     * 分页查询
     * @param message message
     * @return list
     */
    Page<Message> findPage(Message message);

    /**
     * 修改消息内容
     * @param map map
     */
    void edit(Map<String,Object> map );

    /**
     * 查询此用户对应的消息
     * @param username username
     * @return list
     */
    Page<Message> findPageByUser(String username);

    /**
     * 修改消息状态为已读
     * @param id id
     */
    void haveRead(Integer id);

    /**
     * 修改全部消息状态为已读
     * @param username username
     */
    void haveReadAll(String username);
    /**
     * 查询全部未读消息
     * @param username username
     * @return list
     */
    List<Message> findAllReadMessage(String username);


    /**
     * 新增消息
     * @param message message
     */
    void add(Message message);


}
