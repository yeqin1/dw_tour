package com.shujia.common.kylin.test;


import com.shujia.common.kylin.dao.DBUtil;

/**
 * 并发查询测试
 * 1，分不同查询并发量查询测试，100，1000，10000
 */
public class ParallelQueryTest {
    public static void main(String[] args) {


        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DBUtil.select("SELECT d_scenic_name,sum(only_pn) FROM product_tour_scenic_day_index WHERE day_id='20180508' group by d_scenic_name" );

                }
            }).run();
        }

    }
}
