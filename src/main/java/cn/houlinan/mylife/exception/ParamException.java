package cn.houlinan.mylife.exception;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/25
 * Time : 21:40
 */
public class ParamException extends RuntimeException{


    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
