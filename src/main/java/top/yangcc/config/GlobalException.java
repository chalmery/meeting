package top.yangcc.config;


import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import org.springframework.web.bind.annotation.*;
import top.yangcc.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 * @author yangcc
 */
@RestControllerAdvice
public class GlobalException {
    /**
     * 全局异常拦截（拦截项目中的NotLoginException异常）
     */
	@ExceptionHandler(NotLoginException.class)
	public Result handlerNotLoginException(NotLoginException nle, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		nle.printStackTrace();
		// 判断场景值，定制化异常信息,以及返回码
		String message = "";
        switch (nle.getType()) {
            case NotLoginException.NOT_TOKEN:
                message = "未提供token";
                return Result.unToken();
            case NotLoginException.INVALID_TOKEN:
                message = "token无效";
                return Result.invalidToken();
            case NotLoginException.TOKEN_TIMEOUT:
                message = "token已过期";
                return Result.expiredToken();
            case NotLoginException.BE_REPLACED:
                message = "token已被顶下线";
                return Result.conflictToken();
            case NotLoginException.KICK_OUT:
                message = "token已被踢下线";
                return Result.forcedOfflineToken();
            default:
                message = "当前会话未登录";
                return Result.noLoginToken();
        }
	}

    @ExceptionHandler(DisableLoginException.class)
    public Result handlerDisableLoginException(DisableLoginException dle, HttpServletRequest request, HttpServletResponse response)throws Exception{
        dle.printStackTrace();
        return Result.banToken();
    }
}


