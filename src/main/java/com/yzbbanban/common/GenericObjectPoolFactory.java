package com.yzbbanban.common;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 对象池工厂，请在线程中使用，每条线程创建唯一对象的对象池
 * Created by ban on 2018/7/6.
 *
 * @author ban
 */
public class GenericObjectPoolFactory<T> extends AbstractGenericObjectPoolFactory<T> {


    private GenericObjectPool<T> pool = null;

    /**
     * 创建对象池
     *
     * @param clz 类
     * @return 对象池，客用户获取，创建，回池，销毁，获取对象数量等操作
     */
    @Override
    public GenericObjectPool<T> createObjectPool(Class<T> clz) {
        if (pool == null) {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            //对象最大数量
            poolConfig.setMaxTotal(50);
            //对象最大空闲数量,此值的默认值为8，若不设置，会将空闲的对象池中对象destory
            poolConfig.setMaxIdle(50);
            //对象最小空闲数量
            poolConfig.setMinIdle(5);
            poolConfig.setMaxWaitMillis(3000);
            poolConfig.setLifo(false);
            pool = new GenericObjectPool<>(new ObjectFactory<>(clz), poolConfig);

        }
        return pool;
    }
}
