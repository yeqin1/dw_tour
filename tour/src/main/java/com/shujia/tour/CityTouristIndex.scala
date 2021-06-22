package com.shujia.tour

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put, Table}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object CityTouristIndex {
  def main(args: Array[String]): Unit = {
    /**
      *
      * 统计游客指标
      * 客流量按天 [市id,客流量]
      * 性别按天 [市id,性别,客流量]
      * 年龄按天 [市id,年龄,客流量]
      * 常住地按天 [市id,常住地市,客流量]
      * 归属地按天 [市id,归属地市,客流量]
      * 终端型号按天 [市id,终端型号,客流量]
      * 消费等级按天 [市id,消费等级,客流量]
      * 停留时长按天 [市id,停留时长,客流量]
      * 停留时长按天 [市id,出游距离,客流量]
      *
      *
      */
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



    //客流量按天
    val resultDF: DataFrame =spark.sql(
      s"""
        |select d_city_id,day_id,count(mdn) as c
        |from dal_tour.dal_tour_city_tourist_msk_d where day_id=$day_id
         group by d_city_id,day_id
        |
      """.stripMargin)

    /**
      * 将结果保存到hbase中，以id+时间作为rowkey
      */
    resultDF.foreachPartition(rows => {

      //创建hbase连接
      val configuration: Configuration = new Configuration

      //指定zk的链接地址
      configuration.set("hbase.zookeeper.quorum", "master,node1,node2")

      val connection: Connection = ConnectionFactory.createConnection(configuration)
      val table: Table =connection.getTable(TableName.valueOf("tour:city_index"))
      rows.foreach(row=>{
        val d_city_id: String =row.getAs[String]("d_city_id")
        val flow: Long =row.getAs[Long]("c")
        val rowkey: String = d_city_id+"_"+day_id
        val put: Put = new Put(rowkey.getBytes())
        put.addColumn("info".getBytes(),"flow".getBytes(),flow.toString.getBytes())
        table.put(put)

      })
      connection.close()
    })

    /**
      * 性别按天[市id,性别，客流量]
      */
    val joinDF: DataFrame =spark.sql(
      s"""
        |select a.source_county_id,a.d_city_id,a.d_stay_time,a.d_max_distance,b.* from
        |(select * from dal_tour.dal_tour_city_tourist_msk_d where day_id=$day_id) as a
        |join
         (select * from dim.dim_usertag_msk_m where month_id=$month_id) as b
         on a.mdn=b.mdn
      """.stripMargin)
    joinDF.cache()
    indexSaveHbase(joinDF, "age", day_id)
    indexSaveHbase(joinDF, "gender", day_id)
    indexSaveHbase(joinDF, "resi_county_id", day_id)
    indexSaveHbase(joinDF, "number_attr", day_id)
    indexSaveHbase(joinDF, "trmnl_brand", day_id)
    indexSaveHbase(joinDF, "number_attr", day_id)
    indexSaveHbase(joinDF, "conpot", day_id)
    indexSaveHbase(joinDF, "d_stay_time", day_id)
    indexSaveHbase(joinDF, "d_max_distance", day_id)




















    //    resultDF.write
//      .format("jdbc")
//      .option("url","jdbc:mysql://master:3306")
//      .option("dbtable","tour.city_tourist_index_d")
//      .option("characterEncoding","utf-8")
//      .option("user","root")
//      .option("password","123456")
//      .mode(SaveMode.Overwrite)
//      .save()

  }
  def indexSaveHbase(joinDF:DataFrame,field:String,day_id:String):Unit={
    joinDF.groupBy("d_city_id",field)
      .agg(count("mdn")as "c")
      .rdd
      .map(row=>{
        val d_city_id: String =row.getAs[String]("d_city_id")
        val f:Any=row.getAs[Any](field)
        val c: Long = row.getAs[Long]("c")
        val value: String =f + ":" + c
        (d_city_id,value)
      })
      .reduceByKey((x,y)=>x+"|"+y)
      .foreachPartition(iter=>{

        val configuration: Configuration = new Configuration

        //指定zk的链接地址
        configuration.set("hbase.zookeeper.quorum", "master,node1,node2")

        val connection: Connection = ConnectionFactory.createConnection(configuration)
        val table: Table =connection.getTable(TableName.valueOf("tour:city_index"))
        iter.foreach(kv=>{
          val d_city_id: String = kv._1
          val index: String = kv._2
          val rowkey: String = d_city_id+"_"+day_id
          val put: Put = new Put(rowkey.getBytes())
          put.addColumn("info".getBytes(),field.getBytes(),index.getBytes())
          table.put(put)
        })
        connection.close()


      })


  }

}
