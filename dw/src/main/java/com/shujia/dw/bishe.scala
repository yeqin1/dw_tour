package com.shujia.dw

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object bishe {
  def main(args: Array[String]): Unit = {


    /**
    * `id` INT COMMENT '数据id',
    * `confirmedCount` INT COMMENT '累计确诊',
    * `confirmedIncr` INT COMMENT '相比昨天确诊累计人数',
    * `curedCount` INT COMMENT '累计治愈',
    * `curedIncr` INT COMMENT '相比昨天治愈人数',
    * `currentConfirmedCount` INT COMMENT '现存确诊人数',
    * `currentConfirmedIncr` INT COMMENT '相比昨天累计确诊增加人数',
    * `dateId` STRING COMMENT '日期',
    * `deadCount` INT COMMENT '累计死亡',
    * `deadIncr` INT COMMENT '相比昨天新增死亡',
    * `suspectedCount` INT COMMENT '疑似死亡',
    * `suspectedCountIncr` INT COMMENT '相比昨天新增疑似人数',
    * `provinceName` STRING COMMENT '省，直辖市，自治区行政区名',
    * `provinceShortName` STRING COMMENT '省，直辖市，自治区行政区简称'
    */
  val spark: SparkSession = SparkSession
    .builder()
    .appName("beishe")
    .enableHiveSupport()
    .getOrCreate()
  import spark.implicits._
  val ocpd: DataFrame =spark.table("ods.ods_china_province_data")
  val aocpd: DataFrame =ocpd.selectExpr("confirmedCount", "curedCount","currentConfirmedCount","deadCount","suspectedCount","provinceName","dateId.substring(0,6) as dateId")
  aocpd
    .write
    .partitionBy("dateId")
    .saveAsTable("dwd.dwd_china_province_data")

  }
}
