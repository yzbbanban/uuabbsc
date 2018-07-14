package com.yzbbanban.common.objectPool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 对象池工厂，请在线程中使用，每条线程创建唯一对象的对象池
 *
 * @author ban
 */
public class GenericObjectPoolFactory<T> extends AbstractGenericObjectPoolFactory<T> {
    //DI
    /**
     * 对象池
     */
    private GenericObjectPool<T> pool = null;
    /**
     * 对象最大数量
     */
    private int maxTool = 80;
    /**
     * 对象最大空闲数量，若不设置，会将空闲的对象池中对象destory
     */
    private int maxIdle = 50;
    /**
     * 对象最小空闲数量
     */
    private int minIdle = 25;
    private static final String ERROR_MESSAGE = "创建对象池失败";
    //DI END

    public GenericObjectPoolFactory() {
    }

    /**
     * @param maxTool 对象最大数量
     * @param maxIdle 对象最大数量
     * @param minIdle 对象最大空闲数量
     */
    public GenericObjectPoolFactory(int maxTool, int maxIdle, int minIdle) {
        this.maxTool = maxTool;
        this.maxIdle = maxIdle;
        this.minIdle = minIdle;
    }

    /**
     * 创建对象池
     *
     * @param clz 类
     * @return 对象池，客用户获取，创建，回池，销毁，获取对象数量等操作
     */
    @Override
    public GenericObjectPool<T> createObjectPool(Class<T> clz) {
        if (pool == null) {
            try {
                GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
                //对象最大数量
                poolConfig.setMaxTotal(maxTool);
                //对象最大空闲数量，若不设置，会将空闲的对象池中对象destory
                poolConfig.setMaxIdle(maxIdle);
                //对象最小空闲数量
                poolConfig.setMinIdle(minIdle);
                poolConfig.setLifo(false);
                pool = new GenericObjectPool<>(new ObjectFactory<>(clz), poolConfig);
                //准备创建对象 以MinIdle为主
                pool.preparePool();
            } catch (Exception e) {
                throw new RuntimeException(ERROR_MESSAGE);
            }
        }
        return pool;
    }
}
