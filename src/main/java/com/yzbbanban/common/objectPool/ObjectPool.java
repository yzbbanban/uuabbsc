package com.yzbbanban.common.objectPool;

import java.util.Vector;

/**
 * Created by ban on 2018/7/5.
 */
public class ObjectPool {
    private ParameterObject paraObj;//该对象池的属性参数对象
    private Class clsType;//该对象池中所存放对象的类型
    private int currentNum = 0; //该对象池当前已创建的对象数目
    private Object currentObj;//该对象池当前可以借出的对象
    private Vector pool;//用于存放对象的池

    public ObjectPool(ParameterObject paraObj, Class clsType) {
        this.paraObj = paraObj;
        this.clsType = clsType;
        pool = new Vector();
    }

    public Object getObject() {
        if (pool.size() <= paraObj.getMinCount()) {
            if (currentNum <= paraObj.getMaxCount()) {
                //如果当前池中无对象可用，而且已创建的对象数目小于所限制的最大值，就利用
                //PoolObjectFactory创建一个新的对象
                PoolableObjectFactory objFactory = PoolableObjectFactory.getInstance();
                currentObj = objFactory.createObject(clsType);
                currentNum++;
            } else {
                //如果当前池中无对象可用，而且所创建的对象数目已达到所限制的最大值，
                //就只能等待其它线程返回对象到池中
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    currentObj = pool.firstElement();
                }
            }
        } else {
            //如果当前池中有可用的对象，就直接从池中取出对象
            currentObj = pool.firstElement();
        }
        return currentObj;
    }

    public void returnObject(Object obj) {
        // 确保对象具有正确的类型
        if (clsType.isInstance(obj)) {
            pool.addElement(obj);
            synchronized (this) {
                notifyAll();
            }
        } else {
            throw new IllegalArgumentException("该对象池不能存放指定的对象类型");
        }
    }
}
