package top.yangcc.service;

import java.util.List;

/**
 * @author yangcc
 */
public interface PermissionService {
    /**
     * 根据用户查询权限列表
     * @param id userid
     * @return list
     */
    List<String> findPermissionValueByUserId(String id);
}
