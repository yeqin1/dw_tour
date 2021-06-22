package com.shujia.dw

import java.awt.geom.Point2D
import java.text.SimpleDateFormat
import java.util.Date

import com.shujia.common.grid.Grid
import org.apache.spark.rdd.RDD
import org.apache.spark.sql._

object StayPointJob {
  def main(args: Array[String]): Unit = {
    if (args.length==0){
      println("请指定时间参数")
      return
    }
    val day_id: String = args(0)
    val spark: SparkSession = SparkSession
      .builder()
      .appName("MergeLocationJob")
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    val merge: Dataset[Row] =spark.table("dwi.dwi_res_regn_mergelocation_msk_d")
      .where($"day_id"===day_id)

    val mergeRDD: RDD[(String, String)] =merge.map(row=>{
      val mdn: String =row.getAs[String]("mdn")
      val start_time: String = row.getAs[String]("start_time")
      val county_id: String = row.getAs[String]("county_id")
      val grid_id: String = row.getAs[String]("grid_id")
      //以手机号和网格编号区县编号作为key
      (mdn + "_" + grid_id + "_" + county_id, start_time)
    }).rdd
    //按key分组
    val groupByRDD: RDD[(String, Iterable[String])] =mergeRDD.groupByKey()
    val stayPoint: DataFrame =groupByRDD.map(kv=>{
      val mdnAndGridAndCounty: String =kv._1
      val split: Array[String] = mdnAndGridAndCounty.split("_")
      val mdn: String = split(0)
      val grid: String = split(1)
      val county_id: String = split(2)
      val points: List[String] = kv._2.toList
      val sortPoint: List[String] =points.sortBy(time=>time.split(",")(0))
      //取第1个点的开始时间
      val grid_first_time: String =sortPoint.head.split(",")(0)
      //取最后一的点的结束时间
      val grid_last_time: String =sortPoint.last.split(",")(1)
      //	20180503163937,20180503163637
      val format: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
      val startDate: Date = format.parse(grid_first_time)
      val endDate: Date = format.parse(grid_last_time)
      val duration: Long =Math.abs((endDate.getTime-startDate.getTime)/60000)
      //获取网格中心点的经纬度
      val p: Point2D.Double = Grid.getCenter(grid.toLong)
      val longi: String = p.getX.formatted("%.4f")
      val lati: String = p.getY.formatted("%.4f")

      /**
        * mdn string comment '用户手机号码'
        * ,longi string comment '网格中心点经度'
        * ,lati string comment '网格中心点纬度'
        * ,grid_id string comment '停留点所在电信内部网格号'
        * ,county_id string comment '停留点区县'
        * ,duration string comment '机主在停留点停留的时间长度（分钟）,lTime-eTime'
        * ,grid_first_time string comment '网格第一个记录位置点时间（秒级）'
        * ,grid_last_time string comment '网格最后一个记录位置点时间（秒级）
        */
      (mdn,longi,lati,grid,county_id,duration,grid_first_time,grid_last_time)

    }).toDF()
    //保存数据
    stayPoint.write
      .format("csv")
      .option("sep","\t")
      .mode(SaveMode.Overwrite)
      .save(s"/daas/motl/dwi/dwi_staypoint_msk_d/day_id=$day_id")



  }

}
