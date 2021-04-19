package top.yangcc.service;

import org.springframework.web.multipart.MultipartFile;
import top.yangcc.entity.Faculty;
import top.yangcc.entity.User;
import top.yangcc.response.ConflictUser;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryUserPageBean;

import java.io.IOException;
import java.util.List;

/**
 * @author yangcc
 */
public interface UserService {


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
     * @param queryUserPageBean 查询条件
     * @return User
     */
    PageResult findUserByCondition(QueryUserPageBean queryUserPageBean);

    /**
     * 查询所有的用户信息
     * @param id 院系id
     * @return User
     */
    List<User> findByFacultyId(Integer id);

    /**
     * 查询角色是否存在
     * @param username username
     * @return Result
     */
    Integer findCountByUsername(String username);

    /**
     * 查询对应的院系是否还有用户
     * @param id id
     * @return count user
     */
    Integer findUserCountByFacultyId(Integer id);

    /**
     * 新建admin用户
     * @param avatar 头像
     * @param username 用户名
     * @param password 密码
     * @param faculty 院系名
     * @param role 角色code
     * @exception IOException 上传七牛云
     */
    void add(MultipartFile avatar, String username, String password, String faculty, String role) throws IOException;

    /**
     * 新建admin用户,不带头像
     * @param username 用户名
     * @param password 密码
     * @param faculty 院系名
     * @param role 角色code

     */
    void addForNoAvatar(String username, String password, String faculty, String role);
    /**
     * 修改用户
     * @param id id
     * @param username username
     * @param faculty faculty
     * @param role role
     */
    void edit(Integer id,String username,String password, String faculty, String role);

    /**
     * 修改admin用户
     * @param avatar 头像
     * @param id id
     * @param username 用户名
     * @param faculty 院系名
     * @param role 角色code
     */
    void editAdnUpload(Integer id, MultipartFile avatar, String username,String password,  String faculty, String role) throws IOException;

    /**
     * 删除admin用户
     * @param id id
     */
    void delete(Integer id);


    /**
     * 用户自定义的修改信息
     * @param user user
     */
    void userEdit(User user);

    /**
     * 用户自定义的修改信息
     * @param avatar avatar
     * @param id id
     */
    void userEditAvatar(MultipartFile avatar,Integer id) throws IOException;

    /**
     * 根据会议id查询用户信息
     * @param id id
     * @return users
     */
    List<User> findByMeetingId(Integer id);

    /**
     * 查询会议冲突的用户
     * @param id meetingId
     * @return list
     */
    List<ConflictUser> findByConflict(Integer id);
}
