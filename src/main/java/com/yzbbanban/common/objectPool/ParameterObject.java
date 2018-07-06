package com.yzbbanban.common.objectPool;

/**
 * Created by ban on 2018/7/5.
 */
public class ParameterObject {

    private Integer maxCount;
    private Integer minCount;

    public ParameterObject(Integer maxCount, Integer minCount) {
        this.maxCount = maxCount;
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }
}
