package com.ftc.ordermessage.config.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-09-13 14:04:56
 * @describe: 线程池配置
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean("kafkaListenerExecutor-1")
    public Executor singleExecutorNumber1() {
        return getSingleExecutor(1);
    }

    @Bean("kafkaListenerExecutor-2")
    public Executor singleExecutorNumber2() {
        return getSingleExecutor(2);
    }

    @Bean("kafkaListenerExecutor-3")
    public Executor singleExecutorNumber3() {
        return getSingleExecutor(3);
    }

    /**
     * 获取单例线程池
     *
     * @param number 线程池序号
     * @return 单例线程池
     */
    private Executor getSingleExecutor(int number) {

        //1.定义线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        //2.定义核心线程数
        executor.setCorePoolSize(1);

        //3.最大线程数
        executor.setMaxPoolSize(1);

        //4.设置额外线程存活时间
        executor.setKeepAliveSeconds(60);

        //5.队列大小
        executor.setQueueCapacity(1024);

        //6.线程池中的线程名前缀
        executor.setThreadNamePrefix("kafka-listener-" + number + "-");

        //7.拒绝策略:异常抛出策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        //8.初始化线程池
        executor.initialize();

        //9.返回线程池
        return executor;
    }
}