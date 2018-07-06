package com.yzbbanban.common.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolCheck {
    //DI
    private static Logger log = LoggerFactory.getLogger(ThreadPoolCheck.class);
    //DI END

    /**
     * 检查线程池容量
     *
     * @param threadPool 线程池
     */
    public static void checkThreadPool(ThreadPoolTaskExecutor threadPool) {
        int active = threadPool.getActiveCount();   // 当前活动线程数
        int max = threadPool.getMaxPoolSize();      // 目前设置的最大线程数
        int core = threadPool.getCorePoolSize();    // 核心线程数（最小线程）

        // 如果活动线程为0，则重置最大线程数
        if (active == 0) {
            max = 100;
            threadPool.setMaxPoolSize(max);
        }


        // 如果 （最大线程数 - 当前活动线程数 >= 核心线程数）
        // 则最大线程数重置为（当前设置的最大线程数 + 核心线程数）
        if ((max - active) >= core) {
            max = max + core;
            threadPool.setMaxPoolSize(max);
        }

        // 如果（当前活动线程数 + 核心线程数 <= 最大线程数的60%）,则重置最大线程数为：活动线程数 + 核心线程数
        if ((active + core) <= (max * 0.6)) {
            max = active + core;
            max = max < 100 ? 100 : max;
            threadPool.setMaxPoolSize(max);
        }

        // 如果（最大线程数 >= 600），则主线程停留2秒后，继续执行
        if (max >= 600) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        log.info("【当前活动线程数{}；核心线程数：{}；最大线程数：{}】", active, core, max);
    }
}
