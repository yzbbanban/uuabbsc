package com.yzbbanban.common.objectPool;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by ban on 2018/7/6.
 *
 * @author ban
 */
public abstract class AbstractGenericObjectPoolFactory<T> {
    /**
     * 创建对象池
     *
     * @param clz 类
     * @return 对象池
     */
    public abstract GenericObjectPool<T> createObjectPool(Class<T> clz);
}
