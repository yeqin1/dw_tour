package com.shujia.tour

import com.shujia.common.util.Geography
import org.apache.spark.sql._

object CityTouristJob {
  /**
    * 计算市游客
    * 1：用户在城市停留时间大于三小时
    * 2：用户出游距离大于三小时
    */
  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("请指定时间参数")
      return
    }

    val day_id: String = args(0)
    println("当前天分区数：" + day_id)

    val month_id: String = day_id.substring(0, 6)
    println("当前月分区：" + month_id)

    val spark: SparkSession = SparkSession
      .builder()
      .appName("MergeLocationJob")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._
    //加载停留表
    val stayPoint: Dataset[Row] =spark
      .table("dwi.dwi_staypoint_msk_d")
      .where($"day_id"===day_id)
    //加载用户画像表
    val usertag: Dataset[Row] =spark
      .table("dim.dim_usertag_msk_m")
      .where($"month_id"===month_id)
    //读取行政区配置表
    val adminCode: Dataset[Row] =spark.table("dim.dim_admincode")

    /**
      * mdn string comment '手机号大写MD5加密'
      * ,source_county_id string comment '游客来源区县'
      * ,d_city_id string comment '旅游目的地市代码'
      * ,d_stay_time double comment '游客在该省停留的时间长度（小时）'
      * ,d_max_distance double comment '游客本次出游距离'
      */
//计算两个网格距离
    spark
      .udf
      .register("calculateLength",(grid1:String,grid2:String)=>
      Geography.calculateLength(grid1.toLong,grid2.toLong)
    )
    val cityTour: DataFrame =stayPoint
      .join(adminCode.hint("broadcast"),"county_id")//关联行政区配置表
      .select($"mdn",$"city_id",$"duration", $"grid_id")
      .join(usertag,"mdn")
      .select($"mdn",$"city_id",$"duration", expr("calculateLength(grid_id,resi_grid_id)/1000.0 as distance"),$"resi_county_id")
      .groupBy($"mdn",$"city_id",$"resi_county_id")
      .agg(sum($"duration")/60.0 as "d_stay_time",max($"distance") as "d_max_distance")
      .where($"d_stay_time">3 and $"d_max_distance">10)
      .select($"mdn", $"resi_county_id" as "source_county_id", $"city_id" as "d_city_id", round($"d_stay_time", 3), round($"d_max_distance", 3))
    //打印物理计划
    cityTour.explain()

    cityTour.write
      .format("csv")
      .option("sep","\t")
      .mode(SaveMode.Overwrite)
      .save(s"/daas/motl/dal_tour/dal_tour_city_tourist_msk_d/day_id=$day_id")




  }

}
