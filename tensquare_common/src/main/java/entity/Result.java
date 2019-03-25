package entity;

import java.io.Serializable;

/**
 * 返回结果实体类
 */
public class Result  implements Serializable {
    private boolean flag;//是否成功
    private int code;//返回码
    private String message;//返回信息
    private Object data;//返回数据

    public Result(boolean flag, int code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object
            data) {
        super();
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result() {
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
