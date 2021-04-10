package top.yangcc.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yangcc.service.PermissionService;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * 保证此类被SpringBoot扫描，完成sa-token的自定义权限验证扩展
 * @author yangcc
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private PermissionService permissionService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        //根据用户名查询用户的权限码集合
        return permissionService.findPermissionValueByUserId((Integer) loginId);
    }

    /**
     * 返回一个账号所拥有的角色标识集合,
     * 这里我基于权限,而不是角色,因此不实现此类
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        return null;
    }

}