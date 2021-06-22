package com.shujia.common.kylin.cube;

import java.util.List;

class Column{
    private String qualifier;
    private List<String> measure_refs;

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public List<String> getMeasure_refs() {
        return measure_refs;
    }

    public void setMeasure_refs(List<String> measure_refs) {
        this.measure_refs = measure_refs;
    }
}