package com.yzbbanban.common.component;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务线程池配置
 */
@Component
public class TaskThreadPoolConfig {
    //DI
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 100;
    private static final int QUEUE_CAPACITY = 200;
    private static final int KEEP_ALIVE_SECONDS = 300;
    //DI END

    /**
     * 线程池配置
     *
     * @return 线程执行器
     */
    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最少数量
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //线程池维护线程的最大数量
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //线程池所使用的缓冲队列
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }
}
