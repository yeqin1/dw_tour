package com.shujia.common.kylin.cube;

import java.util.List;

public class AggregationGroup {

    private List<String> includes;

    private SelectRule select_rule;



    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public SelectRule getSelect_rule() {
        return select_rule;
    }

    public void setSelect_rule(SelectRule select_rule) {
        this.select_rule = select_rule;
    }
}
