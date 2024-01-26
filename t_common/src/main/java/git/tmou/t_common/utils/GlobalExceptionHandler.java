package git.tmou.t_common.utils;

import com.alibaba.fastjson2.JSON;
import git.tmou.t_common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 请求参数格式验证失败处理
     *
     * @param req 请求
     * @param e   验证失败exception
     * @return 验证失败响应
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<?> argumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.warn("Arguments validation failed: " + JSON.toJSONString(req.getParameterMap()) + " Reason: " + e.getMessage());
        return Response.failure(ErrorCodeEnum.INVALID_ARGUMENT);
    }


    /**
     * 通用失败处理
     *
     * @param req 请求
     * @param e   exception
     * @return 未知错误响应
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<?> exceptionHandler(HttpServletRequest req, Throwable e) {
        log.error("Unknown error occurred: " + JSON.toJSONString(req.getParameterMap()) + " Reason: " + e.getMessage());
        e.printStackTrace();
        return Response.failure(ErrorCodeEnum.UNKNOWN_ERROR, e);
    }
}
