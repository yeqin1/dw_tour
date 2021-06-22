package com.shujia.common.kylin.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 构建modeljson字符串
 */
public class MakeModelJson {

    public static String CreateModelJson( String modelName, String factname, String description, ArrayList<String> colums, ArrayList<String> measureStr, String partitionColumn, String partitionFormat) {
        Model model = new Model();
        model.setName(modelName);
        model.setDescription(description);
        model.setFact_table(String.format("PROTRAVEL.%s", factname));
        model.setLookups(new ArrayList<String>());

        ArrayList<Dimension> dimensions = new ArrayList<>();

        Dimension dimension = new Dimension();
        dimension.setTable(factname);

        ArrayList<String> c = new ArrayList<>();
        for (String colum : colums) {
            c.addAll(Arrays.asList(colum.split(",")));
        }

        dimension.setColumns(c);
        dimensions.add(dimension);


        model.setDimensions(dimensions);

        ArrayList<String> metrics = new ArrayList<>();

        for (String s : measureStr) {
            metrics.add(String.format("%s.%s", factname, s));
        }

        model.setMetrics(metrics);

        model.setFilter_condition("");

        PartitionDesc partitionDesc = new PartitionDesc();
        partitionDesc.setPartition_date_column(String.format("%s.%s", factname, partitionColumn));
        partitionDesc.setPartition_time_column("null");
        partitionDesc.setPartition_date_start(0);
        partitionDesc.setPartition_date_format(partitionFormat);
        partitionDesc.setPartition_time_format("HH:mm:ss");
        partitionDesc.setPartition_type("APPEND");
        partitionDesc.setPartition_condition_builder("org.apache.kylin.metadata.model.PartitionDesc$DefaultPartitionConditionBuilder");

        model.setPartition_desc(partitionDesc);

        model.setCapacity("MEDIUM");

        Gson gson = new Gson();
        System.out.println(gson.toJson(model));

        return gson.toJson(model).replace("\"null\"", "null");
    }
}
