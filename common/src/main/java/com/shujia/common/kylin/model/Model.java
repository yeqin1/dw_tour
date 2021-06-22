package com.shujia.common.kylin.model;

import java.util.List;

public class Model {
    private String name ;
    private String description ;
    private String fact_table ;
    private List<String> lookups ;
    private List<Dimension> dimensions ;
    private List<String> metrics ;
    private String filter_condition ;
    private PartitionDesc partition_desc;
    private String capacity ;

    public PartitionDesc getPartition_desc() {
        return partition_desc;
    }

    public void setPartition_desc(PartitionDesc partition_desc) {
        this.partition_desc = partition_desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFact_table() {
        return fact_table;
    }

    public void setFact_table(String fact_table) {
        this.fact_table = fact_table;
    }

    public List<String> getLookups() {
        return lookups;
    }

    public void setLookups(List<String> lookups) {
        this.lookups = lookups;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public String getFilter_condition() {
        return filter_condition;
    }

    public void setFilter_condition(String filter_condition) {
        this.filter_condition = filter_condition;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
