package top.yangcc.service;

import top.yangcc.entity.User;
import top.yangcc.response.Result;

/**
 * @author yangcc
 */
public interface LoginService {
    /**
     * 登录
     * @param user user
     * @return Result
     */
    Result login(User user);

    /**
     * 注销
     */
    void logout();
}
