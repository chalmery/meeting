package top.yangcc.service;

import top.yangcc.entity.Meeting;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMeetingPageBean;
import top.yangcc.response.QueryPageBean;

import java.util.List;
import java.util.Set;

/**
 * @author yangcc
 */
public interface MeetingService {

    /**
     * 新建会议
     * @param meeting 会议对象
     */
    void add(Meeting meeting);


    /**
     * 审核
     * @param id id
     * @param username 审核人的用户名
     */
    void pass(Integer id,String username);


    /**
     * 审核不通过
     * @param id 会议id
     * @param username 审核人name
     * @param message 不通过原因
     */
    void fail(Integer id, String username, String message);

    /**
     * 审批页分页查询
     * @param queryPageBean   queryPageBean
     * @return PageResult
     */
    PageResult findPageByVerify(QueryMeetingPageBean queryPageBean);


    /**
     * 审批历史页分页查询
     * @param queryPageBean   queryPageBean
     * @return PageResult
     */
    PageResult findPageByVerifyHistory(QueryMeetingPageBean queryPageBean);

    /**
     * 正在进行页分页查询
     * @param queryPageBean   queryPageBean
     * @return PageResult
     */
    PageResult findPageByOngoing(QueryMeetingPageBean queryPageBean);

    /**
     * 会议历史分页查询
     * @param queryPageBean   queryPageBean
     * @return PageResult
     */
    PageResult findPageByHistory(QueryMeetingPageBean queryPageBean);

    /**
     * 查询会议冲突列表
     * @param id id
     * @return list
     */
    List<Meeting> findByConflict(Integer id);


    /**
     * 查询会议冲突列表,根据人员查询
     * @param id id
     * @return set
     */
    Set<Meeting> findByConflictUser(Integer id);

    /**
     * 申请历史
     * @param queryPageBean   queryPageBean
     * @return PageResult
     */
    PageResult findPageByApply(QueryMeetingPageBean queryPageBean);


    /**
     * 用户历史会议
     * @param queryPageBean   queryPageBean
     * @param username username
     * @return PageResult
     */
    PageResult findPageByUserHistory(QueryMeetingPageBean queryPageBean,String username);

    /**
     * 用户将要参加的会议
     * @param queryPageBean   queryPageBean
     * @param username username
     * @return PageResult
     */
    PageResult findPageByFuture(QueryMeetingPageBean queryPageBean,String username);

    /**
     * 查询用户要参加的所有会议
     * @param username username
     * @return list
     */
    List<Meeting> findAllFutureByUsername(String username);
}
