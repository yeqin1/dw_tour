package com.shujia.common.kylin

import com.shujia.common.kylin.dao.DBUtil

object Test {
  def main(args: Array[String]): Unit = {

    val resultSet = DBUtil.select("select clazz,count(*) as c from student group by clazz")

    while (resultSet.next()) {
      val clazz = resultSet.getString("clazz")
      val c = resultSet.getLong("c")

      println(clazz + "\t" + c)
    }
  }

}
