package com.shujia.common.kylin.dao;

public enum MeasuresEnum {

    ONLY_PN("电信游客人数", "only_pn"),
    ONLY_PT("电信游客人次", "only_pt"),
    WHOLE_PN("全网游客人数", "whole_pn"),
    WHOLE_PT("全网游客人次", "whole_pt"),
    IN_PN("电信游客流入量", "in_pn"),
    WHOLE_IN_PN("全网游客流入量", "whole_in_pn"),
    OUT_PN("电信游客流出量", "out_pn"),
    WHOLE_OUT_PN("全网游客流出量", "whole_out_pn"),
    ONEDAY_PN("电信一日游游客量", "whole_out_pn"),
    WHOLE_ONEDAY_PN("全网一日游游客量", "whole_oneday_pn"),
    OVERNIGHT_PN("电信隔夜游游客量", "overnight_pn"),
    WHOLE_OVERNIGHT_PN("全网隔夜游游客量", "whole_overnight_pn");


    private String chineseName;
    private String columName;

    MeasuresEnum(String chineseName, String columName) {
        this.chineseName = chineseName;
        this.columName = columName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getColumName() {
        return columName;
    }

    public void setColumName(String columName) {
        this.columName = columName;
    }


    public static MeasuresEnum newInstance(String chineseName){
        for (MeasuresEnum measuresEnum : MeasuresEnum.values()) {
            if (measuresEnum.chineseName.equals(chineseName)){
                return measuresEnum;
            }
        }
        return null;
    }
}
