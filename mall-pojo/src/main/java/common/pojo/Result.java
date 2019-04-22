package common.pojo;

import jdk.nashorn.internal.parser.JSONParser;
import lombok.Data;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName Result
 * @dsecription 返回结果
 * @data 2018/12/11
 * @Vserion 1.0
 */

@Data
public class Result<T> implements Serializable{

    private boolean success;

    private String message;

    private T data;

    public Result(boolean success,String message,T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result(boolean success,String message){
        this(success,message,null);
    }

    /**
     * 后台默认成功返回
     */
    public static final Result ADMIN_SUCCESS = new Result(true,Message.ADMIN_SUCCESS);

    /**
     * 后台默认失败返回
     */
    public static final Result ADMIN_ERROR = new Result(false,Message.ADMIN_ERROR);

    /**
     * 成功,msg:success
     * @return
     */
    public static Result success(){
        return new Result(true, "success");
    }

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result success(T data){
        return new Result<T>(true, "success", data);
    }

    /**
     * 成功，自定义消息
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result success(String message){
        return new Result<T>(true, message, null);
    }

    /**
     * 成功，自定义数据、消息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result success(T data,String message){
        return new Result<T>(true, message, data);
    }

    /**
     * 失败,msg:error
     * @return
     */
    public static Result error(){
        return new Result(false, "error");
    }

    /**
     * 失败
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result error(T data){
        return new Result<T>(true, "error", data);
    }

    /**
     * 失败，自定义消息
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result error(String message){
        return new Result<T>(false, message, null);
    }

    /**
     * 失败,自定义数据、消息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result error(T data,String message){
        return new Result<T>(false, message, data);
    }

}
