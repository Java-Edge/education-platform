package com.javagpt.back.advice;

import com.javagpt.common.annotation.RespSuccess;
import com.javagpt.common.resp.ResultBody;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;

/**
 * 实现了ResponseBodyAdvice的接口，可以处理@ResponseBody在写入之之前，来更改body的value
 * 类似的还有RequestBodyAdvice，可以处理@RequestBody在读入前，来更改body的value
 *
 * @author JavaEdge
 */
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * Description: 判断是否支持返回自定义对象<br>
     *
     * @param returnType    返回类型
     * @param converterType 转换器
     * @return <br>
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> declaringClass = returnType.getMethod().getDeclaringClass();
        RespSuccess classRespSuccess = declaringClass.getAnnotation(RespSuccess.class);
        if (classRespSuccess != null) {
            return true;
        } else {
            AnnotatedElement annotatedElement = returnType.getAnnotatedElement();
            RespSuccess respSuccess = annotatedElement.getAnnotation(RespSuccess.class);
            return respSuccess != null;
        }
    }

    /**
     * Description: 重写响应体<br>
     *
     * @param body                  消息体
     * @param returnType            返回类型
     * @param selectedContentType   选择内容类型
     * @param selectedConverterType 选择转换类型
     * @param request               request
     * @param response              响应
     * @return <br>
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body != null) {
            if (body instanceof ResponseEntity || body instanceof ResultBody || body instanceof InputStreamSource || body instanceof String) {
                return body;
            }
        }
        return ResultBody.success(body);
    }
}
