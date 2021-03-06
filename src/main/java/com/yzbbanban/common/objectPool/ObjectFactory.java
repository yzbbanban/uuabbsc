package com.yzbbanban.common.objectPool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * 针对池对象的生命周期管理
 *
 * @author ban
 */
public class ObjectFactory<T> extends BasePooledObjectFactory<T> implements PooledObjectFactory<T> {

    /**
     * 对象池类型
     */
    private Class clz;

    /**
     * 对象池工厂
     *
     * @param clz 对象类型
     */
    public ObjectFactory(Class clz) {
        this.clz = clz;
    }


    @Override
    @SuppressWarnings("unchecked")
    public T create() throws Exception {
        // 创建新对象
        return (T) clz.newInstance();
    }

    @Override
    public PooledObject<T> wrap(T obj) {
        // 将对象包装成池对象
        return new DefaultPooledObject<>(obj);
    }

    /**
     * 初始化每次回收的时候都会执行这个方法
     *
     * @param pooledObject 对象实例
     */
    @Override
    public void passivateObject(PooledObject<T> pooledObject) {
//        System.out.println(pooledObject);
    }
}