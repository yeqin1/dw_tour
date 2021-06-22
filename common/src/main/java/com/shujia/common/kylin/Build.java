package com.shujia.common.kylin;

import com.google.gson.Gson;

public class Build {

    private Long startTime;
    private Long endTime;
    private String buildType;

    public Build(String buildType, Long startTime, Long endTime) {
        this.buildType = buildType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Build(Long startTime, Long endTime) {
        this.buildType = "BUILD";
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * 返回对象json字符串
     * @return
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
