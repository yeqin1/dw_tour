package com.shujia.common.kylin.cube;

import java.util.HashMap;
import java.util.List;

public class Cube {
    private String name;
    private String model_name;
    private String description;
    private List<Dimension> dimensions;
    private List<Measure> measures;
    private List<String> dictionaries;
    private Rowkey rowkey;
    private HbaseMapping hbase_mapping;
    private List<AggregationGroup> aggregation_groups;
    private List<String> status_need_notify;
    private String signature;
    private List<String> notify_list;
    private List<Long> auto_merge_time_ranges;
    private String engine_type;
    private String storage_type;
    private String parent_forward;
    private HashMap<String,String> override_kylin_properties;

    public HashMap<String, String> getOverride_kylin_properties() {
        return override_kylin_properties;
    }

    public void setOverride_kylin_properties(HashMap<String, String> override_kylin_properties) {
        this.override_kylin_properties = override_kylin_properties;
    }

    public List<String> getNotify_list() {
        return notify_list;
    }

    public void setNotify_list(List<String> notify_list) {
        this.notify_list = notify_list;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    public List<String> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<String> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Rowkey getRowkey() {
        return rowkey;
    }

    public void setRowkey(Rowkey rowkey) {
        this.rowkey = rowkey;
    }

    public HbaseMapping getHbase_mapping() {
        return hbase_mapping;
    }

    public void setHbase_mapping(HbaseMapping hbase_mapping) {
        this.hbase_mapping = hbase_mapping;
    }

    public List<AggregationGroup> getAggregation_groups() {
        return aggregation_groups;
    }

    public void setAggregation_groups(List<AggregationGroup> aggregation_groups) {
        this.aggregation_groups = aggregation_groups;
    }

    public List<String> getStatus_need_notify() {
        return status_need_notify;
    }

    public void setStatus_need_notify(List<String> status_need_notify) {
        this.status_need_notify = status_need_notify;
    }

    public List<Long> getAuto_merge_time_ranges() {
        return auto_merge_time_ranges;
    }

    public void setAuto_merge_time_ranges(List<Long> auto_merge_time_ranges) {
        this.auto_merge_time_ranges = auto_merge_time_ranges;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public void setEngine_type(String engine_type) {
        this.engine_type = engine_type;
    }

    public String getStorage_type() {
        return storage_type;
    }

    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
    }

    public String getParent_forward() {
        return parent_forward;
    }

    public void setParent_forward(String parent_forward) {
        this.parent_forward = parent_forward;
    }
}
