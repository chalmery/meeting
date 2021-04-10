package top.yangcc.mapper;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.Role;
import top.yangcc.entity.User;

/**
 * 管理员用户相关的持久层接口
 * @author yangcc
 */
@Repository
public interface AdminMapper {
    /**
     * 查询所有的管理员用户信息
     * @param user 查询条件
     * @return User
     */
    Page<User> findAdminByCondition(top.yangcc.request.User user);

    /**
     * 根据用户名id 查询角色
     * @param uid uid
     * @return role
     */
    Role findRoleByAdminId(Integer uid);

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


}
