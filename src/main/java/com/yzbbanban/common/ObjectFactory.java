package com.yzbbanban.common;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by ban on 2018/7/5.
 * 针对池对象的生命周期管理
 */
public class ObjectFactory<T> extends BasePooledObjectFactory<T> implements PooledObjectFactory<T> {

    static GenericObjectPool pool = null;

    static ObjectFactory poolFactory;

    private Class clz;

    public ObjectFactory(Class clz) {
        this.clz = clz;
    }

    private static ObjectFactory getFactoryInstance(Class clz) {
        if (poolFactory == null) {
            poolFactory = new ObjectFactory(clz);
        }
        return poolFactory;
    }

    // 取得对象池工厂实例
    public synchronized static <T> GenericObjectPool<T> getInstance(Class clz) {
        if (pool == null) {
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            //对象最大数量
            poolConfig.setMaxTotal(50);
            //对象最大空闲数量
            poolConfig.setMaxIdle(10);
            //对象最小空闲数量
            poolConfig.setMinIdle(5);
            poolConfig.setLifo(false);
            pool = new GenericObjectPool<>(getFactoryInstance(clz), poolConfig);

        }
        return pool;
    }

    @Override
    public T create() throws Exception {
        // 创建新对象
        return (T) clz.newInstance();
    }

    @Override
    public PooledObject<T> wrap(T obj) {
        // 将对象包装成池对象
        return new DefaultPooledObject<>(obj);
    }

    // 反初始化每次回收的时候都会执行这个方法
    @Override
    public void passivateObject(PooledObject<T> pooledObject) {

    }
}