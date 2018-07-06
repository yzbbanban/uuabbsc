package com.yzbbanban.common.component;

import org.apache.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by ban on 2018/4/28.
 */
public class TaskCallable<T> implements Callable<String> {
    //DI
    private Logger logger = Logger.getLogger(TaskCallable.class);

    private CallBack<String, T> callBack;
    private int sleep;        // 线程睡眠时间
    private LinkedBlockingDeque<T> accumulateQueue;
    private Long startTime;
    //DI END

    /**
     * 构造方法
     *
     * @param callBack        回调方法，用于线程任务的回调处理
     * @param accumulateQueue 队列
     * @param sleep           开启线程前的休息时间
     */
    public TaskCallable(CallBack<String, T> callBack, LinkedBlockingDeque<T> accumulateQueue, int sleep) {
        this.callBack = callBack;
        this.sleep = sleep;
        this.accumulateQueue = accumulateQueue;
        startTime = System.currentTimeMillis();
    }


    @Override
    public String call() throws Exception {
        Thread.sleep(sleep);
        int successCount = 0;
        int totalCount = 0; // 任务执行总记录数
        while (true) {
            totalCount++;

            // 从队列中取出业务对象
            T dataList = accumulateQueue.poll();
            // 如果取不到业务对象，则说明无任务可执行，结束任务
            if (dataList == null) {
                return successCount + "," + totalCount;
            }

            // 处理业务(处理成功后successCount自增1)
            boolean b = false;  // 处理结果

            // 开始处理
            try {
                // 处理方法，在service实现
//                    log.info("-------->task: " + coinMarketChartInfoList);
//                logger.info("-------->task Thread: " + Thread.currentThread().getName());
//                logger.info("-------->task time : " + (System.currentTimeMillis() - startTime));
                callBack.solve("success", dataList);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            // 处理成功后，记录数累加
            if (b) {
                successCount++;
            }
        }
    }


}
