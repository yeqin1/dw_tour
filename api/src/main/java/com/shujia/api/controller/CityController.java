package com.shujia.api.controller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class CityController {
    private static Table table;
    //创建连接
    static {
        Configuration configuration = new Configuration();

        //指定zk的链接地址
        configuration.set("hbase.zookeeper.quorum", "master,node1,node2");

        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(configuration);
            //获取表对象
            table = connection.getTable(TableName.valueOf("tour:city_index"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @GetMapping("/queryFlowByCity")
    public HashMap<String, Long> queryFlowByCity(String cityId,String day_id,String field){
        HashMap<String, Long> map = new HashMap<>();
        String rowkey=cityId+"_"+day_id;
        Get get = new Get(rowkey.getBytes());
        //指定要查寻的列
        get.addColumn("info".getBytes(),field.getBytes());
        try {
            Result result = table.get(get);
            byte[] value = result.getValue("info".getBytes(), field.getBytes());
            String values = Bytes.toString(value);
            String[] split = values.split("\\|");
            for (String s : split) {
                String[] split1 = s.split(":");
                String k = split1[0];
                Long v = Long.parseLong(split1[1]);
                map.put(k,v);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return map;


    }
}
