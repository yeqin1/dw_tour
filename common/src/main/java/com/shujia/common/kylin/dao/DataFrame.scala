package com.shujia.common.kylin.dao

import java.text.DecimalFormat

import scala.collection.mutable.ListBuffer

/**
  * 指标数据框
  * qinxiao
  */
class DataFrame(var dimensionEnums: List[DimensionEnum] //维度
                , var measuresEnums: List[MeasuresEnum] //指标
                , var originalData: Array[Array[String]] //数据
                , section: Boolean //是否分段
                , per: Boolean //是否触发百分比显示结果
               ) {


  def this(dimensionEnums: List[DimensionEnum], measuresEnums: List[MeasuresEnum], originalData: Array[Array[String]]) {
    this(dimensionEnums, measuresEnums, originalData, true, true)
  }


  var flag = false //是否计算百分比
  for (elem <- dimensionEnums) {
    if ("%".equals(elem.getFormat) && per) {
      flag = true
    }
  }

  var title: String = _
  val titler = new StringBuffer()
  for (elem <- measuresEnums) {
    titler.append(elem.getChineseName).append("/")
  }
  titler.delete(titler.length() - 1, titler.length())


  var sheads: List[String] = _
  var tyoes: List[String] = _
  val sheadsr = new ListBuffer[String]
  val tyoesr = new ListBuffer[String]
  for (elem <- dimensionEnums) {
    sheadsr.append(elem.getChineseName)
    tyoesr.append("string")
  }

  for (elem <- measuresEnums) {
    sheadsr.append(elem.getChineseName)
    if (flag) {
      tyoesr.append("string")
    } else {
      tyoesr.append("int")
    }
  }

  sheads = sheadsr.toList
  tyoes = tyoesr.toList
  title = titler.toString

  var data: Array[Array[String]] = _ //处理后的数据

  /**
    * 格式化data，分段处理，排序处理
    */
  data = {
    var rdata = originalData.map(x => {
      val dataresult = new ListBuffer[String]
      for (i <- dimensionEnums.indices) {
        var flag = ""
        val sectionEnum = dimensionEnums(i).getSectionEnum
        if (sectionEnum != null && section) {
          sectionEnum.getSection.split(",")
            .map(x => (x, x.split("-")))
            .map(x => (x._2(0), x._2(1), x._1))
            .map(x => {
              val f = if (x._2.equals("~")) Integer.MAX_VALUE else x._2.toInt
              (x._1.toInt, f, x._3)
            })
            .foreach(y => {
              if (x(i) != null && !x(i).equals("null")) {
                if (x(i).toInt >= y._1 && x(i).toInt < y._2) {
                  flag = y._3
                }
              }
            })
        }
        if (flag.isEmpty) {
          flag = x(i)
        }
        dataresult.append(flag)
      }
      for (j <- measuresEnums.indices) {
        dataresult.append(x(dimensionEnums.length + j))
      }
      dataresult.toArray
    }).groupBy(_.take(dimensionEnums.length).mkString("\t")).map(x => {
      x._1.split("\t").union(
        x._2.map(y => y.takeRight(measuresEnums.length))
          .reduce((x, y) => x.zip(y).map(x => x._1.toInt + x._2.toInt).map(_.toString))
      )
    }).toList.sortBy(_.take(dimensionEnums.length).mkString("\t")).toArray


    val df1 = new DecimalFormat("##.00%")
    if (flag) {
      var udata = rdata.map(_.take(dimensionEnums.length))
      for (j <- measuresEnums.indices) {
        val nums = rdata.map(x => x(dimensionEnums.length + j).toDouble)
        val sum = nums.sum
        udata = udata.zip(nums.map(x => df1.format(x / sum))).map(x => (x._1.mkString("\t") + "\t" + x._2).split("\t"))

      }
      rdata = udata
    }

    rdata
  }

  def show(numRows: Int): Unit = {
    println(toString(numRows))
  }

  def show(): Unit = {
    show(data.length)
  }

  override def toString: String = toString(data.length)

  /**
    * 格式化打印数据
    *
    * @param numRows string
    * @return
    */
  def toString(numRows: Int): String = {
    val showStr = new StringBuilder
    for (dimensionEnum <- dimensionEnums) {
      showStr.append(dimensionEnum.getChineseName)
      showStr.append("\t")
    }
    for (measuresEnum <- measuresEnums) {
      showStr.append(measuresEnum.getChineseName)
      showStr.append("\t")
    }
    showStr.delete(showStr.length - 1, showStr.length)
    showStr.append("\n")

    val flag = if (data.lengthCompare(numRows) > 0) numRows else data.length

    for (i <- 0 until flag) {
      for (raws <- data(i)) {
        showStr.append(raws)
        showStr.append("\t")
      }
      showStr.delete(showStr.length - 1, showStr.length)
      showStr.append("\n")
    }

    showStr.toString
  }

  def getData: Array[Array[String]] = data
}
