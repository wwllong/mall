package common.pojo;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName Result
 * @dsecription 返回结果
 * @data 2018/12/11
 * @Vserion 1.0
 */
public class Result implements Serializable{

    private boolean success;

    private String message;

    public Result(boolean success,String message){
        super();
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
