package top.yangcc.mapper;

import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.Menu;
import top.yangcc.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author yangcc
 */
@Repository
public interface RoleMapper {
    /**
     * 分页查询
     * @param queryString 查询条件
     * @return roleList
     */
    Page<Role> findByCondition(String queryString);

    /**
     * 查询所有的是管理员的角色
     * @return role list
     */
    List<Role> findAllAdminRole();

    /**
     * 查询所有的是user的角色
     * @return role list
     */
    List<Role> findAllUserRole();



    /**
     * 查询roleId
     * @param role code
     * @return id
     */
    Integer findIdByCode(String role);

    /**
     * 查询roleId
     * @param role code
     * @return id
     */
    Integer findIdByName(String role);

    /**
     * 根据角色id查询菜单id
     * @param rid role id
     * @return list menu
     */
    List<Integer> findMenuListByRid(Integer rid);

    /**
     * 新建角色
     * @param role role
     */
    void add(Role role);

    /**
     * 新增角色 menu中间表
     * @param map map
     */
    void addRoleMenu(Map<String,Integer> map);

    /**
     * 修改角色
     * @param role role
     */
    void edit(Role role);


    /**
     * 删除中间表关系
     * @param id id
     */
    void deleteRoleMenu(Integer id);

    /**
     * 删除role
     * @param id role_id
     */
    void delete(Integer id);


}
