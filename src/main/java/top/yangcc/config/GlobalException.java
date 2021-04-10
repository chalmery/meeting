package top.yangcc.config;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yangcc.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 * @author yangcc
 */
@ControllerAdvice(basePackages = "top.yangcc.controller")
public class GlobalException {

    /**在当前类每个方法进入之前触发的操作*/
    @ModelAttribute
    public void get(HttpServletRequest request) throws IOException {

    }


    /**全局异常拦截（拦截项目中的所有异常）*/
    @ResponseBody
    @ExceptionHandler
    public Result handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        e.printStackTrace();

        // 不同异常返回不同状态码
        Result result = null;
        //如果是未登录异常
        if (e instanceof NotLoginException) {
            NotLoginException ee = (NotLoginException) e;
            result = Result.fail("未登录");
        }
        //如果是权限异常
        else if(e instanceof NotPermissionException) {
            NotPermissionException ee = (NotPermissionException) e;
            result = Result.fail("没有权限");
        }
        //普通异常
        else {
            result = Result.fail("异常");
        }
        // 返回给前端
        return result;
    }


    /**
     * 全局异常拦截（拦截项目中的NotLoginException异常）
     */
	@ExceptionHandler(NotLoginException.class)
	public Result handlerNotLoginException(NotLoginException nle, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		nle.printStackTrace();

		// 判断场景值，定制化异常信息
		String message = "";
        switch (nle.getType()) {
            case NotLoginException.NOT_TOKEN:
                message = "未提供token";
                break;
            case NotLoginException.INVALID_TOKEN:
                message = "token无效";
                break;
            case NotLoginException.TOKEN_TIMEOUT:
                message = "token已过期";
                break;
            case NotLoginException.BE_REPLACED:
                message = "token已被顶下线";
                break;
            case NotLoginException.KICK_OUT:
                message = "token已被踢下线";
                break;
            default:
                message = "当前会话未登录";
                break;
        }
		// 返回给前端
		return Result.fail(message);
	}


}
