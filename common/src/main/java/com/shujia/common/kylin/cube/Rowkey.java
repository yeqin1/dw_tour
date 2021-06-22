package com.shujia.common.kylin.cube;

import java.util.List;

public class Rowkey {
    private List<RowkeyColumn> rowkey_columns ;


    public List<RowkeyColumn> getRowkey_columns() {
        return rowkey_columns;
    }

    public void setRowkey_columns(List<RowkeyColumn> rowkey_columns) {
        this.rowkey_columns = rowkey_columns;
    }
}
