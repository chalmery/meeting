package top.yangcc.service.impl;

import org.springframework.stereotype.Service;
import top.yangcc.service.PermissionService;

import java.util.List;

/**
 * @author yangcc
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    /**
     * 根据用户查询权限列表
     * @param id userid
     * @return list
     */
    @Override
    public List<String> findPermissionValueByUserId(Integer id) {
        return null;
    }

}
