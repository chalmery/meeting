package top.yangcc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yangcc.entity.Role;
import top.yangcc.mapper.RoleMapper;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryPageBean;
import top.yangcc.service.RoleService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangcc
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**分页查询*/
    @Override
    public PageResult findByCondition(QueryPageBean queryPageBean) {
        // 通过分页助手进行查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Role> byCondition = roleMapper.findByCondition(queryPageBean.getQueryString());
        PageInfo<Role> pageInfo = new PageInfo<>(byCondition);
        //拿到分页后的role
        List<Role> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**查询所有的是管理员的角色*/
    @Override
    public List<Role> findAllAdminRole() {
        return roleMapper.findAllAdminRole();
    }

    /**查询所有的是用户的角色*/
    @Override
    public List<Role> findAllUserRole() {
        return roleMapper.findAllUserRole();
    }

    /**新建*/
    @Override
    public void add(Role role) {
        //格式化时间
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = dateTimeFormatter.format(dateTime);
        role.setCreateTime(createTime);
        //新增角色
        roleMapper.add(role);
        //设置中间表
        addRoleMenu(role);
    }

    /**重建中间关系表*/
    private void addRoleMenu(Role role) {
        if (role.getMenuList()!=null && role.getMenuList().size()>0){
            for (Integer menuId : role.getMenuList()) {
                Map<String, Integer> map = new HashMap<>();
                map.put("role_id",role.getId());
                map.put("menu_id",menuId);
                roleMapper.addRoleMenu(map);
            }
        }
    }

    /**修改*/
    @Override
    public void edit(Role role) {
        //修改
        roleMapper.edit(role);
        //删除中间表关系
        roleMapper.deleteRoleMenu(role.getId());
        //重建中间表关系
        addRoleMenu(role);
    }

    /**删除role*/
    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
        roleMapper.deleteRoleMenu(id);
    }


}
