package com.shujia.common.kylin.cube;

import java.util.List;

public class HbaseMapping {

    private List<ColumnFamily> column_family;


    public List<ColumnFamily> getColumn_family() {
        return column_family;
    }

    public void setColumn_family(List<ColumnFamily> column_family) {
        this.column_family = column_family;
    }
}
