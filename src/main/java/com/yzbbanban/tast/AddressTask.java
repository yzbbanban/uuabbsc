package com.yzbbanban.tast;

import com.google.common.collect.Lists;
import com.yzbbanban.common.objectPool.GenericObjectPoolFactory;
import com.yzbbanban.common.component.CallBack;
import com.yzbbanban.common.component.QueueThreadUtils;
import com.yzbbanban.domain.Address;
import org.apache.commons.pool2.impl.GenericObjectPool;
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
public class AddressTask implements CallBack<String, Integer> {

    @Autowired
    private QueueThreadUtils queueThreadUtils;

    private GenericObjectPoolFactory<Address> genericObjectPoolFactory = new GenericObjectPoolFactory<>();

    private GenericObjectPool<Address> pool;

//    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        pool = genericObjectPoolFactory.createObjectPool(Address.class);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        String time = format.format(Calendar.getInstance().getTime());
        System.out.println("-------AddressTask------>" + time);

        List<Integer> integerList = Lists.newArrayList(100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100, 100, 100);
        try {
            queueThreadUtils.executeDataIn(20, integerList, this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void solve(String result, Integer info) {


        List<Address> addressList = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            try {
                Address u = pool.borrowObject();
//                Address u=new Address();
                u.setId(i);
                u.setStreet("banpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp" + i);
                u.setArea("asafffffffffffssssssssssssss");
                addressList.add(u);
                //一定要回池
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

}
