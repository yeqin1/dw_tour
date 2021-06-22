package com.shujia.common.kylin.dao;

public enum SectionEnum {
    AGE_SECTION("0-20,20-25,25-30,30-35,35-40,40-45,45-50,50-55,55-60,60-65,65-~"),
    STAY_TIME_SECTION("0-12,12-24,24-48,48-72,72-96,96-~"),
    TIMES_SECTION("1-2,2-3,3-4,4-5,5-6,6-7,7-8,8-9,9-10,10-~"),
    PCKG_PRICE_SECTION("0-50,50-100,100-200,200-500,500-~");

    private String section ;

    SectionEnum(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
