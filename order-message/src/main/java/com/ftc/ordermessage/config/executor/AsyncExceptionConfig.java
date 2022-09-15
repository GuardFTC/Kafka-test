package com.ftc.ordermessage.config.executor;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.log.StaticLog;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.lang.reflect.Method;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 14:33:13
 * @describe: 异步无返回值方法-异常处理
 */
@Configuration
public class AsyncExceptionConfig implements AsyncConfigurer {

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    static class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            StaticLog.error("[异步方法:[{}]出现异常:{}]", method.getName(), ExceptionUtil.getMessage(throwable.getCause()));
        }
    }
}