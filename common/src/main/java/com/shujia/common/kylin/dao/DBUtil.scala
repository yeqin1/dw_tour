package com.shujia.common.kylin.dao

import java.sql._
import java.util.Properties

import com.shujia.common.kylin.KylinConstant


object DBUtil {

  /**
    * 获取数据库连接
    *
    * @return
    */

  val DBDRIVER: String = KylinConstant.KYLIN_JDBC_DRIVER
  val DBNAME: String = KylinConstant.KYLIN_PROJECT
  val DBURL: String = String.format("jdbc:kylin://%s:%s/%s", KylinConstant.KYLIN_HOST, KylinConstant.KYLIN_POST, DBNAME)
  val USERNAME: String = KylinConstant.KYLIN_USERNAME
  val PASSWORD: String = KylinConstant.KYLIN_PASSWORD
  val driver: Driver = Class.forName(DBDRIVER).newInstance.asInstanceOf[Driver]
  val info = new Properties
  info.put("user", USERNAME)
  info.put("password", PASSWORD)
  val conn: Connection = driver.connect(DBURL, info)
  val state: Statement = conn.createStatement


  /**
    * 查询数据
    *
    * @param sql
    * @return
    * @throws Exception
    */
  def select(sql: String): ResultSet = {
    val startTime = System.currentTimeMillis //获取开始时间
    val rs = state.executeQuery(sql)
    val endTime = System.currentTimeMillis //获取结束时间
    System.out.println("查询所用时间：" + (endTime - startTime) + "ms") //输出程序运行时间
    rs
  }


  /**
    * 关闭数据库连接
    */
  def close(): Unit = {
    try {
      state.close()
      conn.close()
    } catch {
      case e: SQLException =>
        e.printStackTrace()
    }
  }

}