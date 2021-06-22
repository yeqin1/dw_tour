package com.shujia.dw

import org.apache.spark.sql.{DataFrame, SparkSession}

object UserTagJob1 {
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

}}
