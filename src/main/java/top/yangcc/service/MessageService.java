package top.yangcc.service;

import top.yangcc.entity.Message;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMessagePageBean;

import java.util.List;
import java.util.Map;

/**
 * @author yangcc
 */
public interface MessageService {
    /**
     *查询消息信息
     * @param queryMessagePageBean  queryMessagePageBean
     * @return list
     */
    PageResult findPage(QueryMessagePageBean queryMessagePageBean);

    /**
     * 查询消息详情
     * @param id 消息id
     * @return map
     */
    Map<String, Object> findDetail(Integer id);

    /**
     * 修改消息内容
     * @param id id
     * @param content content
     */
    void edit(Integer id, String content);

    /**
     * 查询此用户对应的消息
     * @param queryMessagePageBean queryMessagePageBean
     * @return list
     */
    PageResult findPageByUser(QueryMessagePageBean queryMessagePageBean);

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
     * 查询全部已读消息
     * @param username username
     * @return list
     */
    List<Message> findAllReadMessage(String username);

}
