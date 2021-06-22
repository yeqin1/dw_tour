#!/usr/bin/env bash
#***********************************************************************************
# **  文件名称:MergeLocationJob.sh
# **  创建日期: 2020年8月22日
# **  编写人员: qinxiao
# **  输入信息:
# **  输出信息:
# **
# **  功能描述:融合表生成脚本
# **  处理过程:
# **  Copyright(c) 2016 TianYi Cloud Technologies (China), Inc.
# **  All Rights Reserved.
#***********************************************************************************

#***********************************************************************************
#==修改日期==|===修改人=====|======================================================|
#
#***********************************************************************************
#获取脚本所在目录
shell_home="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

#进入脚本目录
cd $shell_home
# 时间参数
day_id=$1

# 启动任务
spark-submit \
--master yarn-client \
--num-executors 2 \
--executor-cores 2 \
--executor-memory 1G \
--jars common-1.0.jar \
--conf spark.sql.shuffle.partitions=16 \
--class com.shujia.tour.CityTouristIndex \
tour-1.0-jar-with-dependencies.jar $day_id


