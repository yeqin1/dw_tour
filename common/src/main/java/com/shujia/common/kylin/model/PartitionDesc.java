package com.shujia.common.kylin.model;

public class PartitionDesc {
    private String partition_date_column;
    private String partition_time_column;
    private int partition_date_start;
    private String partition_date_format;
    private String partition_time_format;
    private String partition_type;
    private String partition_condition_builder;

    public String getPartition_date_column() {
        return partition_date_column;
    }

    public void setPartition_date_column(String partition_date_column) {
        this.partition_date_column = partition_date_column;
    }

    public String getPartition_time_column() {
        return partition_time_column;
    }

    public void setPartition_time_column(String partition_time_column) {
        this.partition_time_column = partition_time_column;
    }

    public int getPartition_date_start() {
        return partition_date_start;
    }

    public void setPartition_date_start(int partition_date_start) {
        this.partition_date_start = partition_date_start;
    }

    public String getPartition_date_format() {
        return partition_date_format;
    }

    public void setPartition_date_format(String partition_date_format) {
        this.partition_date_format = partition_date_format;
    }

    public String getPartition_time_format() {
        return partition_time_format;
    }

    public void setPartition_time_format(String partition_time_format) {
        this.partition_time_format = partition_time_format;
    }

    public String getPartition_type() {
        return partition_type;
    }

    public void setPartition_type(String partition_type) {
        this.partition_type = partition_type;
    }

    public String getPartition_condition_builder() {
        return partition_condition_builder;
    }

    public void setPartition_condition_builder(String partition_condition_builder) {
        this.partition_condition_builder = partition_condition_builder;
    }
}
