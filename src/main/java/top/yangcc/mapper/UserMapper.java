package top.yangcc.mapper;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.Role;
import top.yangcc.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户相关的持久层接口
 * @author yangcc
 */
@Repository
public interface UserMapper {

    /**
     * 查询用户信息
     * @param username username
     * @return User
     */
    User findByUsername(String username);

    /**
     * 查询用户头像
     * @param username username
     * @return avatar
     */
    String findAvatarByUsername(String username);

    /**
     * 查询所有的管理员用户信息
     * @param user 查询条件
     * @return User
     */
    Page<User> findUserByCondition(top.yangcc.request.User user);

    /**
     * 根据用户名id 查询角色
     * @param uid uid
     * @return role
     */
    Role findRoleByUserId(Integer uid);

    /**
     * 查询全部
     * @return list
     */
    List<User> findAll();

    /**
     * 查询所有的用户信息
     * @param id 院系id
     * @return User
     */
    List<User> findByFacultyId(Integer id);

    /**
     * 查询对应的院系是否还有用户
     * @param id id
     * @return count user
     */
    Integer findUserCountByFacultyId(Integer id);
    /**
     * 根据用户名查询用户头像
     * @param id id
     * @return avatar
     */
    String findAvatarById(Integer id);


    /**
     * 查询用户是否已存在
     * @param username username
     * @return int
     */
    Integer findCountByUsername(String username);

    /**
     * 新建用户
     * @param user user
     */
    void add(User user);

    /**
     * 修改用户
     * @param user user
     */
    void edit(User user);

    /**
     * 删除用户
     * @param id id
     */
    void delete(Integer id);

    /**
     * 修改信息
     * @param user user
     */
    void userEdit(User user);


    void userEditAvatar(User user);


    /**
     * 根据会议id查询用户信息
     * @param id 会议id
     * @return users
     */
    List<User> findByMeetingId(Integer id);

    /**
     * 查询冲突的会议列表
     * @param map map
     * @return list
     */
    List<Integer> findByConflict(Map<String, Object> map);

    /**查询全部的用户信息*/
    List<User> findAllUser();

    /**
     * 查询此消息对应的收件人
     * @param id 消息id
     * @return user
     */
    User findByMessageId(Integer id);

    /**
     * 修改此用户的登录状态为在线
     * @param username username
     */
    void updateStatusToOnline(String username);

    /**
     * 修改此用户的登录状态为离线
     * @param username username
     */
    void updateStatusToOffLine(String username);


    /**
     * 踢人下线
     * @param id id
     */
    void offline(Integer id);

    /**
     * 账号封禁
     * @param id id
     */
    void ban(Integer id);

    /**
     * 根据id获取用户名
     * @param id id
     * @return username
     */
    String findUsernameById(Integer id);


    /**
     * 解封进
     * @param username username
     */
    void unBan(String username);
}
