package com.shujia.common.kylin.cube;

public class Dimension {
    private String name;
    private String table;
    private String column;
    private String derived;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDerived() {
        return derived;
    }

    public void setDerived(String derived) {
        this.derived = derived;
    }
}
