package top.yangcc.mapper;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.Meeting;
import top.yangcc.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * 查询会议室信息
     * @param id id
     * @return meeting
     */
    Meeting findById(Integer id);

    /**
     * 修改此会议室的状态
     * @param meeting meeting
     */
    void editStatus(Meeting meeting);

    /**
     * 修改会议室的审核状态
     * @param meeting meeting
     */
    void editApprovalStatus(Meeting meeting);



    /**
     * 查询全部要参加的人员
     * @param mid meetingID
     * @return user
     */
    List<User> findAllMembersByMid(Integer mid);

    /**
     * 查询审核人员信息
     * @param mid meetingID
     * @return user
     */
    User findOperatorByMid(Integer mid);



    /**
     * 审核页分页查询
     * @param meeting meeting
     * @return page
     */
    Page<Meeting> findPageByVerify(Meeting meeting);

    /**
     * 审核历史页分页查询
     * @param meeting meeting
     * @return page
     */
    Page<Meeting> findPageByVerifyHistory(Meeting meeting);


    /**
     * 正在进行页分页查询
     * @param meeting   meeting
     * @return PageResult
     */
    Page<Meeting> findPageByOngoing(Meeting meeting);

    /**
     * 验证是否冲突
     * @param meeting 会议对象
     * @return count
     */
    int isConflictforMeetingRoom(Meeting meeting);

    /**
     * 验证会议人员是否冲突
     * @param map meeting
     * @return 条数
     */
    int isConflictforUser(Map<String, Object> map);

    /**
     * 验证会议人员是否冲突,在分页查询的时候
     * @param map meeting
     * @return 条数
     */
    int isConflictforUserAndFindPage(Map<String, Object> map);
    /**
     * 查询冲突会议列表
     * @param meeting meeting
     * @return list
     */
    List<Meeting> findByConflict(Meeting meeting);

    /**
     * 查询冲突会议列表,根据用户
     * @param map map
     * @return list
     */
    List<Meeting> findByConflictUser(Map<String, Object> map);


    /**
     * 会议历史分页查询
     * @param meeting   meeting
     * @return PageResult
     */
    Page<Meeting> findPageByHistory(Meeting meeting);

    /**
     * 申请历史分页查询
     * @param meeting meeting
     * @return page
     */
    Page<Meeting> findPageByApply(Meeting meeting);

    /**
     * 用户历史会议查询
     * @param map  map
     * @return page
     */
    Page<Integer> findPageByUserHistory(Map<String,Object> map);

    /**
     * 用户将要参加会议查询
     * @param map  map
     * @return page
     */
    Page<Integer> findPageByFuture(Map<String,Object> map);


    /**
     * 根据消息id查询会议信息
     * @param id 消息id
     * @return 会议
     */
    Meeting findByMessageId(Integer id);

    /**
     * 修改会议是否冲突
     * @param map map
     */
    void editConflictforMeetingRoom(Map<String, Object> map);

    /**
     * 修改会议是否冲突
     * @param map1 map
     */
    void editConflictforUser(Map<String, Object> map1);

    /**
     * 查询申请人信息
     * @param id id
     * @return id
     */
    Integer findSponsorById(Integer id);

    /**
     * 查询会议室状态
     * @param id id
     * @return 状态
     */
    String findStatusById(Integer id);

    /**
     * 查询全部的要参加会议
     * @param username username
     * @return list
     */
    List<Meeting> findAllFutureByUsername(String username);

    /**
     * 根据id查询会议详情
     * @param id id
     * @return meeting
     */
    Meeting findAllById(Integer id);

    /**
     * 删除此用户申请的所有的待申请会议
     * @param id id
     */
    void deleteMeetingApplyAllForMeetingId(Integer id);

    /**
     * 查询此用户对应的所有的待审核会议id
     * @param id userId
     * @return meeting ids
     */
    List<Integer> findMeetingApplyAllForUserId(Integer id);
}
