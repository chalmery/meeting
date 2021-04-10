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
     * 未授权的返回
     * @return result
     */
    public static Result unauthorized(String message,Object data){
        Result result = new Result();
        result.setCode(400);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 未授权的返回
     * @return result
     */
    public static Result unauthorized(String message){
        Result result = new Result();
        result.setCode(400);
        result.setMessage(message);
        return result;
    }

    /**
     * 未授权的返回
     * @return result
     */
    public static Result unauthorized(){
        Result result = new Result();
        result.setCode(400);
        return result;
    }

}
