package git.tmou.t_common;

import git.tmou.t_common.utils.ErrorCodeEnum;

import java.io.Serializable;

//@Data
public class Response<T> implements Serializable {
    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功时返回
     *
     * @return {"code": 0, "msg": "", "data": ""}
     */
    public static Response<String> success() {
        Response<String> response = new Response<String>();
        response.setCode(ErrorCodeEnum.SUCCESS.getErrorCode());
        response.setMsg(ErrorCodeEnum.SUCCESS.getErrorMsg());
        response.setData("");
        return response;
    }

    /**
     * 成功时返回
     *
     * @param data 对应数据
     * @return {"code": 0, "msg": "", "data": ${对应数据}}
     */
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<T>();
        response.setCode(ErrorCodeEnum.SUCCESS.getErrorCode());
        response.setMsg(ErrorCodeEnum.SUCCESS.getErrorMsg());
        response.setData(data);
        return response;
    }

    /**
     * 错误时返回
     *
     * @param errorCodeEnum 错误码枚举值
     * @return {"code": ${错误码}, "msg": ${错误消息}, "data": ""}
     */
    public static Response<String> failure(ErrorCodeEnum errorCodeEnum) {
        Response<String> response = new Response<String>();
        response.setCode(errorCodeEnum.getErrorCode());
        response.setMsg(errorCodeEnum.getErrorMsg());
        response.setData("");
        return response;
    }

    /**
     * 错误时返回，并打印错误信息
     *
     * @param errorCodeEnum 错误码枚举值
     * @param e exception
     * @return {"code": ${错误码}, "msg": ${错误消息}, "data": ""}
     */
    public static Response<String> failure(ErrorCodeEnum errorCodeEnum, Throwable e) {
        Response<String> response = new Response<String>();
        response.setCode(errorCodeEnum.getErrorCode());
        response.setMsg(errorCodeEnum.getErrorMsg());
        response.setData(e.getMessage());
        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
