package com.yzbbanban.controller;

import com.yzbbanban.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


/**
 * Created by ban on 2018/7/2.
 */
@RestController
public class HelloController {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() throws Exception {
        ServiceInstance instance = client.getLocalServiceInstance();
        int sleepTime = new Random().nextInt(3000);
        logger.info("sleepTime: " + sleepTime);
        Thread.sleep(sleepTime);
        logger.info("/hello,host: " + instance.getHost() + ",service_id: " + instance.getServiceId());
        return "hello world";
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello1(@RequestParam String name) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/hello,host: " + instance.getHost() + ",service_id: " + instance.getServiceId());

        return "hello world: " + name;
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public User hello2(@RequestHeader String name, @RequestHeader Integer age) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello,host: " + instance.getHost() + ",service_id: " + instance.getServiceId());
        return new User(name, age);
    }

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    public String hello3(@RequestBody User user) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello,host: " + instance.getHost() + ",service_id: " + instance.getServiceId());
        return "hello world: " + user.getName() + "," + user.getAge();
    }

    @RequestMapping(value = "/testPool", method = RequestMethod.GET)
    public String testPool() {
//        GenericObjectPool<Object> pool = ObjectFactory.getInstance(User.class);
//        try {
//            User u = (User) pool.borrowObject();
//            Random random = new Random();
//            u.setId(random.nextInt(1000));
//            u.setName("banpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp" + random.nextInt(1000));
//            u.setAge(random.nextInt(1000));
//            pool.returnObject(u);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "testPool";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        User u = new User();
        Random random = new Random();
        u.setId(random.nextInt(1000));
        u.setName("banpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp" + random.nextInt(1000));
        u.setAge(random.nextInt(1000));
        return u.toString();
    }
}
