package git.tmou.t_common.utils;

public enum ErrorCodeEnum {
    SUCCESS(0, "success"),

    INVALID_ARGUMENT(2001, "invalid arguments"),

    // 登录相关
    WRONG_USERNAME_PWD(3001, "wrong username or password"),
    LOGIN_TIMEOUT(3002, "login timeout"),
    TOO_MANY_LOGINS(3003, "too many logins"),
    ACCOUNT_LOCKED(3004, "account locked due to three failed logins"),
    NOT_ALLOWED_IP(3005, "Client IP is not allowed"),
    EXPIRED_ACCOUNT(3006, "The account is expired"),
    FORBIDDEN_ACCOUNT(3007, "The account is forbidden"),


    // 文件上传下载相关
    FILE_NOT_EXISTS(4001, "file not exists"),
    FILE_FORMAT_ERROR(4002, " file format incorrect"),
    FILE_PARSE_ERROR(4003, "failed to parse the file"),

    UNKNOWN_ERROR(9999, "unknown error");

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    ErrorCodeEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
