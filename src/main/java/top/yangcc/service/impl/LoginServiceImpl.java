package top.yangcc.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yangcc.entity.User;
import top.yangcc.mapper.LoginMapper;
import top.yangcc.mapper.UserMapper;
import top.yangcc.response.Result;
import top.yangcc.service.LoginService;
import top.yangcc.service.UserService;
import top.yangcc.utils.Md5Utils;

/**
 * @author yangcc
 */
@Service
public class LoginServiceImpl implements LoginService {

    private LoginMapper loginMapper;

    private UserMapper userMapper;

    @Autowired
    public LoginServiceImpl(LoginMapper loginMapper, UserMapper userMapper) {
        this.loginMapper = loginMapper;
        this.userMapper = userMapper;
    }

    /**登录*/
    @Override
    public Result login(User user) {
        User byUsername = loginMapper.findByUsername(user.getUsername());
        //如果没有此用户
        if (byUsername==null){
            return Result.fail("没有此用户");
        }
        //加密
        String encrypt = Md5Utils.encrypt(user.getPassword());
        //如果密码不正确
        if (!byUsername.getPassword().equals(encrypt)){
            return Result.fail("密码错误");
        }
        //如果全部正确,登录
        StpUtil.setLoginId(user.getUsername());
        //修改登录状态
        userMapper.updateStatusToOnline(user.getUsername());
        return Result.success("登录成功",StpUtil.getTokenInfo());
    }

    /**
     * 注销
     */
    @Override
    public void logout() {
        //修改数据库
        userMapper.updateStatusToOffLine((String)StpUtil.getLoginId());
        //注销
        StpUtil.logout();
    }
}
