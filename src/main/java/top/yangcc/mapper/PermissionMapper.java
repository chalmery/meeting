package top.yangcc.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangcc
 */
@Repository
public interface PermissionMapper {
    /**
     * 根据用户查询权限列表
     * @param username username
     * @return list
     */
    List<String> findPermissionValueByUserId(String username);
}
