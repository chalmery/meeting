package top.yangcc.service;

import top.yangcc.entity.Role;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryPageBean;

import java.util.List;

/**
 * @author yangcc
 */
public interface RoleService {
    /**
     * 分页查询
     * @param queryPageBean 查询条件
     * @return roleList
     */
    PageResult findByCondition(QueryPageBean queryPageBean);

    /**
     * 查询所有的是管理员的角色
     * @return role list
     */
    List<Role> findAllAdminRole();

    /**
     * 查询所有的是User的角色
     * @return role list
     */
    List<Role> findAllUserRole();

    /**
     * 新建角色
     * @param role role
     */
    void add(Role role);

    /**
     * 修改角色
     * @param role role
     */
    void edit(Role role);


    /**
     * 删除role
     * @param id role_id
     */
    void delete(Integer id);

}
