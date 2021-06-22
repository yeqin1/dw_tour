package com.shujia.dw

import com.shujia.common.util.MD5
import org.apache.spark.sql._

object UserTagJob {
  def main(args: Array[String]): Unit = {
    if (args.length==0){
      println("请指定时间参数")
      return
    }
    val month_id: String = args(0)

    val spark: SparkSession = SparkSession
      .builder()
      .appName("MergeLocationJob")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import org.apache.spark.sql.functions._

    /**
      * mdn string comment '手机号大写MD5加密'
      * ,name string comment '姓名'
      * ,gender string comment '性别，1男2女'
      * ,age string comment '年龄'
      * ,id_number string comment '证件号码'
      * ,number_attr string comment '号码归属地'
      * ,trmnl_brand string comment '终端品牌'
      * ,trmnl_price string comment '终端价格'
      * ,packg string comment '套餐'
      * ,conpot string comment '消费潜力'
      * ,resi_grid_id string comment '常住地网格'
      * ,resi_county_id string comment '常住地区县'
      */
    val userTag: Dataset[Row] = spark
      .table("ods.ods_usertag_m")
      .where($"month_id"===month_id)
    //脱敏
    spark.udf.register("md5",(str:String)=>MD5.md5(str))
    val mdfDF: DataFrame =userTag.select(
      expr("md5(mdn) as mdn"),
      expr("md5(name) as name"),
      $"gender",
      $"age",
      expr("md5(id_number) ") as "id_number",
      $"number_attr",
      $"trmnl_brand",
      $"trmnl_price",
      $"packg",
      $"conpot",
      $"resi_grid_id",
      $"resi_county_id"
    )
    mdfDF.write
      .format("csv")
      .option("sep","\t")
      .mode(SaveMode.Overwrite)
      .save(s"/daas/motl/dim/dim_usertag_msk_m/month_id=$month_id")


  }

}
