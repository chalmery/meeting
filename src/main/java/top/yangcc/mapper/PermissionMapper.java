package top.yangcc.mapper;

import java.util.List;

/**
 * @author yangcc
 */
public interface PermissionMapper {
    /**
     * 根据用户查询权限列表
     * @param id userid
     * @return list
     */
    List<String> findPermissionValueByUserId(Integer id);
}
