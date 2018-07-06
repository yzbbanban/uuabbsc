package com.yzbbanban.common.objectPool;

/**
 * Created by ban on 2018/7/5.
 */
public class PoolableObjectFactory {
    private static PoolableObjectFactory poolFactory;

    public static PoolableObjectFactory getInstance() {
        if (poolFactory == null) {
            poolFactory = new PoolableObjectFactory();
        }
        return poolFactory;
    }

    public Object createObject(Class clsType) {
        Object obj = null;
        try {
            obj = clsType.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
