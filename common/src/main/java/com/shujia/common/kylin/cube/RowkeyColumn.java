package com.shujia.common.kylin.cube;


class RowkeyColumn {
    private String column;
    private String encoding;
    private boolean isShardBy;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public boolean isShardBy() {
        return isShardBy;
    }

    public void setShardBy(boolean shardBy) {
        isShardBy = shardBy;
    }
}