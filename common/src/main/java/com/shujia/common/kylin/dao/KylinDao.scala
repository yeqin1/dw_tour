package com.shujia.common.kylin.dao

import scala.collection.mutable.ListBuffer


class KylinDao(var dimensionEnums: List[DimensionEnum] //维度
               , var measuresEnums: List[MeasuresEnum] //指标
               , var tableName: String //表名
               , var condition: Map[DimensionEnum, String] //条件
               , var ordered: (String, String) //排序
              ) {


  def this(dimensionEnums: List[DimensionEnum], measuresEnums: List[MeasuresEnum], tableName: String, condition: Map[DimensionEnum, String]) {
    this(dimensionEnums, measuresEnums, tableName, condition, null)
  }

  var data: Array[Array[String]] = _
  var sql: String = _

  /**
    * 构建sql语句
    *
    * @return
    */
  sql = {
    if (dimensionEnums.isEmpty || measuresEnums.isEmpty) throw new RuntimeException("维度和指标都不能为空")
    val sql = new StringBuffer
    sql.append("select ")
    //查询维度
    for (dimensionEnum <- dimensionEnums) {
      sql.append(dimensionEnum.getColumName).append(",")
    }
    //查询指标
    for (measuresEnum <- measuresEnums) {
      sql.append("sum(")
      sql.append(measuresEnum.getColumName)
      sql.append(") as ")
      sql.append(measuresEnum.getColumName)
      sql.append(",")
    }
    sql.delete(sql.length - 1, sql.length)
    sql.append(" from ")
    sql.append(tableName)
    if (condition.nonEmpty) { //增加条件
      sql.append(" where ")
      var tager = condition.size
      for ((key, value) <- condition) {
        if ("int".equals(key.getType)) {
          sql.append(key.getColumName).append("=").append(value)
        } else if ("string".equals(key.getType)) {
          sql.append(key.getColumName).append("=").append("'").append(value).append("'")
        } else {
          sql.append(key.getColumName).append("=").append("'").append(value).append("'")
        }
        tager -= 1
        if (tager != 0) sql.append(" and ")
      }
    }
    //增加分组
    sql.append(" group by ")
    for (dimensionEnum <- dimensionEnums) {
      sql.append(dimensionEnum.getColumName).append(",")
    }

    sql.delete(sql.length - 1, sql.length)

    if (ordered != null) {
      sql.append(" order by ").append(ordered._1).append(" ").append(ordered._2)
    }

    sql.toString
  }

  data = {
    //构建sql
    val data = new ListBuffer[Array[String]]
    //查询
    val resultSet = DBUtil.select(sql)
    while (resultSet.next) {
      val rows = new ListBuffer[String]
      for (dimensionEnum <- dimensionEnums) {
        rows.append(resultSet.getString(dimensionEnum.getColumName))
      }
      for (measuresEnum <- measuresEnums) {
        rows.append(resultSet.getString(measuresEnum.getColumName))
      }
      data.append(rows.toArray)
    }
    data.toArray
  }

}

