package com.yzbbanban.tast;

import com.google.common.collect.Lists;
import com.yzbbanban.common.component.CallBack;
import com.yzbbanban.common.component.QueueThreadUtils;
import com.yzbbanban.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ban on 2018/7/6.
 *
 * @author ban
 */
@Component
@EnableScheduling
public class UserTask implements CallBack<String, Integer> {

    @Autowired
    private QueueThreadUtils queueThreadUtils;

//    private GenericObjectPool<User> pool;

//    private GenericObjectPoolFactory<User> genericObjectPoolFactory = new GenericObjectPoolFactory<>();

//    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
//        pool = genericObjectPoolFactory.createObjectPool(User.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        String time = format.format(Calendar.getInstance().getTime());
        System.out.println("-------UserTask------>" + time);

        List<Integer> integerList = Lists.newArrayList(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100);
        try {
            queueThreadUtils.executeDataIn(11, integerList, this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void solve(String result, Integer info) {


        List<User> userList = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            try {
//                User u = pool.borrowObject();
                User u = new User();
                u.setId(i);
                u.setName("banpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp" + i);
                u.setAge(i + 1);
                userList.add(u);
                //一定要回池
//                pool.returnObject(u);
//                returnObject(pool, u);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        System.out.println("-------userList------>" + time + " c:" + pool.getCreatedCount() + ":" + userList);
//        System.out.println("-------userList------>" + userList);
//        getMemInfo();
    }
}
