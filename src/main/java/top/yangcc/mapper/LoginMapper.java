package top.yangcc.mapper;

import org.springframework.stereotype.Component;
import top.yangcc.entity.User;

/**
 * @author yangcc
 */
@Component
public interface LoginMapper {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}
