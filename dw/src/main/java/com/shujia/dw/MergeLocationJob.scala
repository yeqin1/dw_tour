package com.shujia.dw

import com.shujia.common.util.MD5
import org.apache.spark.sql._

object MergeLocationJob {
  def main(args: Array[String]): Unit = {
    if(args.length==0){
      print("没有传参")
      return
    }
    val day_id:String = args(0)
    val spark: SparkSession = SparkSession
      .builder()
      .appName("MergeLocationJob")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    val ddr: DataFrame = spark.table("ods.ods_ddr").where($"day_id"===day_id)
    val dpi: DataFrame = spark.table("ods.ods_dpi").where($"day_id"===day_id)
    val oidd: DataFrame = spark.table("ods.ods_oidd").where($"day_id"===day_id)
    val wcdr: DataFrame = spark.table("ods.ods_wcdr").where($"day_id"===day_id)
    val unionDF: DataFrame = ddr.union(dpi).union(oidd).union(wcdr).toDF("mdn", "start_time", "county_id", "longi", "lati", "bsid", "grid_id", "biz_type", "event_type", "data_source","day_id")
    unionDF.createOrReplaceTempView("union")



    spark.udf.register("md5",(str:String)=>MD5.md5(str))
    //md5(mdn) as mdn", "start_time", "county_id","longi", "lati", "bsid", "grid_id", "biz_type", "event_type", "data_source"
    val DF: DataFrame = spark.sql(
      """
        select md5(mdn) as mdn,
        start_time,county_id,longi,
        lati,bsid,grid_id,biz_type,
        event_type,data_source,day_id
        from union
      """.stripMargin)
    DF.write
      .mode(SaveMode.Overwrite)
      .format("csv")
      .option("sep","\t")
      .save(s"/daas/motl/dwi/dwi_res_regn_mergelocation_msk_d/day_id=$day_id")
  }

}
