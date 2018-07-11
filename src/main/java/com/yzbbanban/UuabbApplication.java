package com.yzbbanban;

import com.yzbbanban.domain.User;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class UuabbApplication {


    public static void main(String[] args) {
        SpringApplication.run(UuabbApplication.class, args);
    }


    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(3);
        return taskScheduler;
    }

    /**
     * 此数量需要做调节，需要查看下内存对用的关系，在一定的线程数量上，不要阻塞，对象也不宜过多
     * 创建对象的数量和线程有直接关系，使用回池才能控制对象的量
     * 最好有一套机制，线程发生阻塞，自动创建大量对象，然后查看闲置对象，销毁对象
     *
     * @param pool
     * @param u
     */
    private void returnObject(GenericObjectPool<User> pool, User u) {
        if (pool.getCreatedCount() >= pool.getMaxTotal() / 2) {
            pool.returnObject(u);
        }

    }

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void testThread() {
//        if (Thread.activeCount() > 0) {
//            System.out.println("activeCount：" + Thread.activeCount());
//        }
//        if (pool == null) {
//            return;
//        }
//        System.out.println("interrupted：" + Thread.interrupted());
//
//        System.out.println("poolCount：" + pool.getCreatedCount());
//        if (pool.getCreatedCount() < Thread.activeCount()) {
//            try {
//                for (int i = 0; i < Thread.activeCount() - pool.getCreatedCount(); i++) {
//                    pool.addObject();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
