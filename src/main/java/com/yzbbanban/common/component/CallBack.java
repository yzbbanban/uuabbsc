package com.yzbbanban.common.component;

/**
 * 用于回调的接口
 */
public interface CallBack<T, M> {
    /**
     * chart数据回调函数
     *
     * @param result 返回的处理结果
     */
    void solve(T result, M info);

}
