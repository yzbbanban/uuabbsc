package com.yzbbanban.common.objectPool;

/**
 * Created by ban on 2018/7/5.
 */
public class ObjectPoolFactory {
    private static ObjectPoolFactory poolFactory;

    public static ObjectPoolFactory getInstance() {
        if (poolFactory == null) {
            poolFactory = new ObjectPoolFactory();
        }
        return poolFactory;
    }

    public ObjectPool createPool(ParameterObject paraObj, Class clsType) {
        return new ObjectPool(paraObj, clsType);
    }


}
