package top.yangcc.controller;


import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.entity.User;
import top.yangcc.response.Result;
import top.yangcc.service.LoginService;

/**
 * 开放登录controller,不需要权限即可访问
 * @author yangcc
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private CaptchaService captchaService;

    private LoginService loginService;

    @Autowired
    public LoginController(CaptchaService captchaService, LoginService loginService) {
        this.captchaService = captchaService;
        this.loginService = loginService;
    }

    /**
     * 对用户滑动图形的数据信息判断
     * @param captchaVO 前端封装好的拼图信息
     * @return 返回
     */
    @RequestMapping("/verify")
    public ResponseModel verify(@RequestBody CaptchaVO captchaVO) {
        return captchaService.verification(captchaVO);
    }

    /**
     * 用户登录
     * @return 返回
     */
    @RequestMapping("/login")
    public Result login(@RequestBody User user) {
        return loginService.login(user);
    }

    /**
     * 用户注销
     * @return 返回
     */
    @RequestMapping("/logout")
    public Result logout() {
        try {
            loginService.logout();
            return Result.success("注销成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("注销失败");
        }
    }

}
