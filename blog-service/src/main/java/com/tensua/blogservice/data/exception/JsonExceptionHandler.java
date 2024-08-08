//package com.tensua.blogservice.data.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.web.ErrorProperties;
//import org.springframework.boot.autoconfigure.web.WebProperties;
//import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.cloud.gateway.support.NotFoundException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.reactive.function.server.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author zhouhd
// * @since 2021/10/8 13:08
// **/
//@Slf4j
//public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {
//
//    public JsonExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext) {
//        super(errorAttributes, resources, errorProperties, applicationContext);
//    }
//
//    /**
//     * BusinessException 业务异常处理
//     *
//     * @param request
//     * @param options
//     * @return
//     */
//    @Override
//    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
//        int code = 500;
//        Throwable error = super.getError(request);
//        String message = error.getMessage();
//        if (message.contains("404") || error instanceof NotFoundException) {
//            code = 404;
//        }
//        return response(code, this.buildMessage(request, error));
//    }
//
//    /**
//     * 指定响应处理方法为JSON处理的方法
//     *
//     * @param errorAttributes
//     */
//    @Override
//    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
//    }
//
//    /**
//     * 根据code获取对应的HttpStatus
//     *
//     * @param errorAttributes
//     */
//    @Override
//    protected int getHttpStatus(Map<String, Object> errorAttributes) {
//        int statusCode = (int) errorAttributes.get("code");
//        return statusCode;
//    }
//
//    /**
//     * 构建异常信息
//     *
//     * @param request
//     * @param ex
//     * @return
//     */
//    private String buildMessage(ServerRequest request, Throwable ex) {
//        StringBuilder message = new StringBuilder("Failed to handle request [");
//        message.append(request.methodName());
//        message.append(" ");
//        message.append(request.uri());
//        message.append("]");
//        if (ex != null) {
//            message.append(": ");
//            message.append(ex.getMessage());
//        }
//        return message.toString();
//    }
//
//    /**
//     * 构建返回的JSON数据格式
//     *
//     * @param status       状态码
//     * @param errorMessage 异常信息
//     * @return
//     */
//    public static Map<String, Object> response(int status, String errorMessage) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", status);
//        map.put("message", errorMessage);
//        map.put("data", null);
//        return map;
//    }
//
//}
