package top.yangcc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yangcc.mapper.PermissionMapper;
import top.yangcc.service.PermissionService;

import java.util.List;

/**
 * @author yangcc
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 根据用户查询权限列表
     * @param id userid
     * @return list
     */
    @Override
    public List<String> findPermissionValueByUserId(String id) {
        return permissionMapper.findPermissionValueByUserId(id);
    }

}
