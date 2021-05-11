package top.yangcc.response;


import lombok.Data;


import java.io.Serializable;

/**
 * 前后端统一的返回
 * @author yangcc
 */
@Data
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    private Result(){};

    /**
     * 成功的返回,code为200
     * @param message 消息
     * @param data 数据
     * @return result
     */
    public static Result success(String message,Object data){
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 成功的返回,code为200
     * @param message 消息
     * @return result
     */
    public static Result success(String message){
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }
    /**
     * 成功的返回,code为200
     * @return result
     */
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(200);
        result.setData(data);
        return result;
    }
    /**
     * 成功的返回,code为200
     * @return result
     */
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        return result;
    }


    /**
     * 失败的返回
     * @param message 消息
     * @param data 数据
     * @return result
     */
    public static Result fail(String message, Object data){
        Result result = new Result();
        result.setCode(300);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    /**
     * 失败的返回
     * @param message 消息
     * @return result
     */
    public static Result fail(String message){
        Result result = new Result();
        result.setCode(300);
        result.setMessage(message);
        return result;
    }
    /**
     * 失败的返回
     * @return result
     */
    public static Result fail(){
        Result result = new Result();
        result.setCode(300);
        return result;
    }

    /**
     * 未提供token
     * @return result
     */
    public static Result unToken(){
        Result result = new Result();
        result.setCode(401);
        result.setMessage("未提供token");
        return result;
    }

    /**
     * token无效
     * @return result
     */
    public static Result invalidToken(){
        Result result = new Result();
        result.setCode(402);
        result.setMessage("token无效");
        return result;
    }

    /**
     * token已过期
     * @return result
     */
    public static Result expiredToken(){
        Result result = new Result();
        result.setCode(403);
        result.setMessage("您的登录信息已过期，请重新登录");
        return result;
    }

    /**
     * token已被顶下线
     * @return result
     */
    public static Result conflictToken(){
        Result result = new Result();
        result.setCode(404);
        result.setMessage("您的账号在别处登录");
        return result;
    }

    /**
     * token已被踢下线
     * @return result
     */
    public static Result forcedOfflineToken(){
        Result result = new Result();
        result.setCode(405);
        result.setMessage("您已被踢下线");
        return result;
    }

    /**
     * 当前会话未登录
     * @return result
     */
    public static Result noLoginToken(){
        Result result = new Result();
        result.setCode(406);
        result.setMessage("当前会话未登录");
        return result;
    }

    /**
     * 当前会话未登录
     * @return result
     */
    public static Result banToken(){
        Result result = new Result();
        result.setCode(407);
        result.setMessage("当前账号已被封禁");
        return result;
    }

}
