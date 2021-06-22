package com.shujia.common.kylin;


import com.shujia.common.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BuildCube {

    public static void buileDay(String cubeName,String time){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        try {

            Long startTime = sdf.parse(time+"08").getTime();

            Long endTime = sdf.parse(DateUtils.getTomorrow(time)+"08").getTime();

            Build build = new Build(startTime, endTime);

            String json = build.toJson();

            System.out.println(KylinRestFulApi.buildCube(cubeName,json));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按月build的时候 开始时间是上个月最后一天，结束时间市这个月第一天
     * 列如：2018013100000000_2018020100000000
     * @param cubeName
     * @param time
     */

    public static void buileMonth(String cubeName,String time){

        time = time +"01";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        try {
            Long startTime = sdf.parse(DateUtils.getYestoday(time)+"08").getTime();

            Long endTime = sdf.parse(time+"08").getTime();

            Build build = new Build(startTime, endTime);

            String json = build.toJson();

            System.out.println(KylinRestFulApi.buildCube(cubeName,json));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多天build 去开始天为基础
     * 列如：
     * 构建：20180215-20180221，实际构建，2018021500000000_2018021600000000
     * @param cubeName
     * @param time
     */
    public static void buileDays(String cubeName,String time){

        time = time.substring(0,8);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        try {

            Long startTime = sdf.parse(time+"08").getTime();

            Long endTime = sdf.parse(DateUtils.getTomorrow(time)+"08").getTime();

            Build build = new Build(startTime, endTime);

            String json = build.toJson();

            System.out.println(KylinRestFulApi.buildCube(cubeName,json));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        buileDay("dal_tour_city_day_index","20180503");
    }

}
