package top.yangcc.service;

import org.springframework.web.multipart.MultipartFile;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryUserPageBean;

import java.io.IOException;

/**
 * @author yangcc
 */
public interface AdminService {
    /**
     * 查询所有的管理员用户信息
     * @param queryUserPageBean 查询条件
     * @return User
     */
    PageResult findAdminByCondition(QueryUserPageBean queryUserPageBean);


    /**
     * 查询角色是否存在
     * @param username username
     * @return Result
     */
    Integer findCountByUsername(String username);

    /**
     * 新建admin用户
     * @param avatar 头像
     * @param username 用户名
     * @param password 密码
     * @param faculty 院系名
     * @param role 角色code
     * @throws IOException 异常
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
     * @param password 用户密码
     * @param faculty 院系名
     * @param role 角色code
     * @throws IOException 异常
     */
    void editAdnUpload(Integer id, MultipartFile avatar, String username,String password,  String faculty, String role) throws IOException;

    /**
     * 删除admin用户
     * @param id id
     */
    void delete(Integer id);


}
