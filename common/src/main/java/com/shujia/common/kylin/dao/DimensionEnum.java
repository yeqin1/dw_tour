package com.shujia.common.kylin.dao;

/**
 * 维度枚举
 *
 */
public enum DimensionEnum{

    D_CITY_ID("目的地市id", "d_city_id"),

    D_CITY_NAME("目的地市名", "d_city_name"),

    D_COUNTY_ID("目的地区县id", "d_county_id"),

    D_COUNTY_NAME("目的地区县名", "d_county_name"),

    D_PROVINCE_ID("目的地省id", "d_province_id"),

    D_PROVINCE_NAME("目的地省名", "d_province_name"),

    D_SCENIC_ID("目的地景区id", "d_scenic_id"),

    D_SCENIC_NAME("目的地景区名", "d_scenic_name"),

    O_COUNTY_ID("来源地区县id", "o_county_id"),

    O_COUNTY_NAME("来源地区县名", "o_county_name"),

    O_CITY_ID("来源地市id", "o_city_id"),

    O_CITY_NAME("来源地市名", "o_city_name"),

    O_PROVINCE_ID("来源地省id", "o_province_id"),

    O_PROVINCE_NAME("来源地省名", "o_province_name"),

    D_ARRIVE_MEANS("交通方式", "d_arrive_means","%"),

    D_DISTANCE_SECTION("出游距离", "d_distance_section"),

    D_STAY_TIME("停留时间按小时", "d_stay_time",SectionEnum.STAY_TIME_SECTION),

    D_STAY_TIME_DAy("停留时间按天", "d_stay_time_day"),

    GENDER("性别", "gender","%"),

    TRMNL_BRAND("终端品牌", "trmnl_brand"),

    TRMNL_MODEL("终端型号", "trmnl_model"),

    PCKG_PRICE("套餐", "pckg_price",SectionEnum.PCKG_PRICE_SECTION),

    TRMNL_PRICE("终端价格", "trmnl_price"),

    CONPOT("消费潜力", "conpot","%"),

    AGE("年龄", "age",SectionEnum.AGE_SECTION,"%","int"),

    TIMES("刷卡次数", "times","%"),

    DAY_ID("按天分区", "day_id"),

    DAYS_ID("多天分区", "days_id"),

    MONTH_ID("按月分区", "month_id");

    DimensionEnum(String chineseName, String columName, SectionEnum sectionEnum, String format) {
        this.chineseName = chineseName;
        this.columName = columName;
        this.sectionEnum = sectionEnum;
        this.format = format;
    }

    private String chineseName;
    private String columName;
    private SectionEnum sectionEnum;
    private String format ;
    private String type;

    DimensionEnum(String chineseName, String columName, SectionEnum sectionEnum, String format, String type) {
        this.chineseName = chineseName;
        this.columName = columName;
        this.sectionEnum = sectionEnum;
        this.format = format;
        this.type = type;
    }

    DimensionEnum(String chineseName, String columName, String format) {
        this.chineseName = chineseName;
        this.columName = columName;
        this.format = format;
    }

    public SectionEnum getSectionEnum() {
        return sectionEnum;
    }

    public void setSectionEnum(SectionEnum sectionEnum) {
        this.sectionEnum = sectionEnum;
    }

    DimensionEnum(String chineseName, String columName) {
        this.chineseName = chineseName;
        this.columName = columName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    DimensionEnum(String chineseName, String columName, SectionEnum sectionEnum) {
        this.chineseName = chineseName;
        this.columName = columName;
        this.sectionEnum = sectionEnum;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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
}
