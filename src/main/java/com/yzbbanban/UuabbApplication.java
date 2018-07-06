package com.yzbbanban;

import com.google.common.collect.Lists;
import com.sun.management.OperatingSystemMXBean;
import com.yzbbanban.common.ObjectFactory;
import com.yzbbanban.common.component.CallBack;
import com.yzbbanban.common.component.QueueThreadUtils;
import com.yzbbanban.common.objectPool.ObjectPool;
import com.yzbbanban.common.objectPool.ObjectPoolFactory;
import com.yzbbanban.common.objectPool.ParameterObject;
import com.yzbbanban.domain.User;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class UuabbApplication implements CallBack<String, Integer> {
    @Autowired
    private QueueThreadUtils queueThreadUtils;

    public static void main(String[] args) {
        SpringApplication.run(UuabbApplication.class, args);
    }


    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        String time = format.format(Calendar.getInstance().getTime());
        System.out.println("-------userList------>" + time);

        List<Integer> integerList = Lists.newArrayList(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100);
        try {
            queueThreadUtils.executeDataIn(11, integerList, this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void solve(String result, Integer info) {
        GenericObjectPool<User> pool = ObjectFactory.getInstance(User.class);

        List<User> userList = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            try {
//                User u = (User) pool.getObject();
                User u = pool.borrowObject();
//                User u = new User();
                u.setId(i);
                u.setName("ban" + i);
                u.setAge(i + 1);
                userList.add(u);
                pool.returnObject(u);
//                returnObject(pool, u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        System.out.println("-------userList------>" + time + " c:" + pool.getCreatedCount() + ":" + userList);
//        System.out.println("-------userList------>" + userList);
//        getMemInfo();
    }

    /**
     * 此数量需要做调节，需要查看下内存对用的关系，在一定的线程数量上，不要阻塞，对象也不宜过多
     * 创建对象的数量和线程有直接关系，使用回池才能控制对象的量
     * 最好有一套机制，线程发生阻塞，自动创建大量对象，然后查看闲置对象，销毁对象
     * @param pool
     * @param u
     */
    private void returnObject(GenericObjectPool<User> pool, User u) {
        if (pool.getCreatedCount() >= pool.getMaxTotal() / 2) {
            pool.returnObject(u);
        }

    }

    private void getMemInfo() {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize());
        System.out.println("Available　RAM：" + mem.getFreePhysicalMemorySize());
    }


}
