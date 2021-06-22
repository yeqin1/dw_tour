package com.shujia.dw

import com.shujia.common.util.MD5
import org.apache.spark.sql._

object MergeLocation {
  def main(args: Array[String]): Unit = {
    if (args.length==0){
      println("请指定时间参数")
      return
    }
   val day_id: String = args(0)
    /**
      * 读取ddr,oidd,wcdr,dpic数据
      * 抽出用户位置并合并成位置融合表
      * mdn string comment '手机号码'
      * ,start_time string comment '业务时间'
      * ,county_id string comment '区县编码'
      * ,longi string comment '经度'
      * ,lati string comment '纬度'
      * ,bsid string comment '基站标识'
      * ,grid_id string comment '网格号'
      * ,biz_type string comment '业务类型'
      * ,event_type string comment '事件类型'
      * ,data_source string comment '数据源'
      *
      */
   val spark: SparkSession = SparkSession
      .builder()
      .appName("MergeLocationJob")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._

    val ddr: DataFrame =spark.table("ods.ods_ddr")
      .where($"day_id"===day_id)
    val wcdr: DataFrame =spark.table("ods.ods_wcdr")
      .where($"day_id"===day_id)
    val dpi: DataFrame =spark.table("ods.ods_dpi")
      .where($"day_id"===day_id)
    val oidd: DataFrame =spark.table("ods.ods_oidd")
      .where($"day_id"===day_id)
    //合并数据
    val unionDF: Dataset[Row] =ddr.union(wcdr).union(dpi).union(oidd)
    //注册自定义函数
    spark.udf.register("md5",(str:String)=>MD5.md5(str))
   //脱敏
   val md4DF: DataFrame = unionDF
      .selectExpr("md5(mdn) as mdn", "start_time", "county_id", "longi", "lati", "bsid", "grid_id", "biz_type", "event_type", "data_source")
    md4DF
      .write
      .mode(SaveMode.Overwrite)
      .format("csv")
      .option("sep", "\t")
      .save(s"/daas/motl/dwi/dwi_res_regn_mergelocation_msk_d/day_id=$day_id")
  }

}
