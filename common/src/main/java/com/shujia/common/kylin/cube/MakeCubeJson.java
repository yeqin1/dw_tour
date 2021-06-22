package com.shujia.common.kylin.cube;

/**
 * 20180409
 * qinxiao
 * 构建cube json字符串
 */

import com.google.gson.Gson;
import com.shujia.common.kylin.KylinConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakeCubeJson {

    /**
     * 构建cube json字符串
     *
     * @param modelName     model名
     * @param cubeName      cube名
     * @param description   描述
     * @param dimensionList 维度列表
     * @param mandatoryDims 强制维度
     * @param timeDims      时间维度
     * @param measureList   度量列表
     * @return String
     */

    public static String CreateCubeJson(String modelName, String cubeName, String description, ArrayList<String> dimensionList, List<String> mandatoryDims, String timeDims, ArrayList<String> measureList) {

        Cube cube = new Cube();
        cube.setName(cubeName);
        cube.setModel_name(modelName);
        cube.setDescription(description);

        ArrayList<Dimension> dimensions = new ArrayList<>();

        for (String dimensionStr : dimensionList) {
            for (String s : dimensionStr.split(",")) {
                Dimension dimension = new Dimension();
                dimension.setName(s);
                dimension.setTable(modelName);
                dimension.setColumn(s);
                dimension.setDerived("null");
                dimensions.add(dimension);
            }
        }

        cube.setDimensions(dimensions);

        ArrayList<Measure> measures = new ArrayList<>();


        for (String s : measureList) {
            String[] split = s.split(",");
            String name = split[0];
            String expression = split[1];
            String type = split[2];
            String value = split[3];
            String returnType = split[4];
            Measure measure = new Measure();
            measure.setName(name);

            Function function = new Function();
            function.setExpression(expression);

            Parameter parameter = new Parameter();
            parameter.setType(type);
            parameter.setValue(value);

            function.setParameter(parameter);

            function.setReturntype(returnType);

            measure.setFunction(function);

            measures.add(measure);

        }

        cube.setMeasures(measures);

        cube.setDictionaries(new ArrayList<String>());


        Rowkey rowkey = new Rowkey();

        ArrayList<RowkeyColumn> rowkeyColumns = new ArrayList<>();


        for (String dimensionStr : dimensionList) {

            for (String s : dimensionStr.split(",")) {
                RowkeyColumn rowkeyColumn = new RowkeyColumn();
                rowkeyColumn.setColumn(modelName + "." + s);
                rowkeyColumn.setEncoding("dict");
                rowkeyColumn.setShardBy(false);

                rowkeyColumns.add(rowkeyColumn);
            }
        }

        rowkey.setRowkey_columns(rowkeyColumns);

        cube.setRowkey(rowkey);


        HbaseMapping hbaseMapping = new HbaseMapping();


        ArrayList<ColumnFamily> columnFamilies = new ArrayList<>();


        ColumnFamily columnFamily = new ColumnFamily();

        columnFamily.setName("F1");

        Column column = new Column();
        column.setQualifier("M");

        ArrayList<String> measure_refs = new ArrayList<>();


        for (String s : measureList) {
            String[] split = s.split(",");
            String name = split[0];
            measure_refs.add(name);
        }


        column.setMeasure_refs(measure_refs);


        ArrayList<Column> columns = new ArrayList<>();
        columns.add(column);

        columnFamily.setColumns(columns);

        columnFamilies.add(columnFamily);

        hbaseMapping.setColumn_family(columnFamilies);

        cube.setHbase_mapping(hbaseMapping);


        List<AggregationGroup> aggregationGroups = createAggregationGroup(dimensionList, modelName, mandatoryDims, timeDims);

        cube.setAggregation_groups(aggregationGroups);

        ArrayList<String> status_need_notify = new ArrayList<>();
        status_need_notify.add("ERROR");
        status_need_notify.add("DISCARDED");
        status_need_notify.add("SUCCEED");

        cube.setStatus_need_notify(status_need_notify);


        ArrayList<Long> auto_merge_time_ranges = new ArrayList<>();
        auto_merge_time_ranges.add(604800000L);
        auto_merge_time_ranges.add(2419200000L);

        cube.setAuto_merge_time_ranges(auto_merge_time_ranges);

        cube.setEngine_type("2");
        cube.setStorage_type("2");
        cube.setParent_forward("3");

        cube.setSignature("1mCd3wfsG4Zf6K1ojaqrsA==");

        cube.setNotify_list(new ArrayList<String>());


        HashMap<String, String> override_kylin_properties = new HashMap<>();

        override_kylin_properties.put("kylin.engine.mr.min-reducer-number", KylinConstant.KYLIN_ENGINE_MR_MIN_REDUCER_NUMBER);

        cube.setOverride_kylin_properties(override_kylin_properties);

        Gson gson = new Gson();

        System.out.println(gson.toJson(cube));

        return gson.toJson(cube).replace("\"null\"", "null");
    }

    /**
     * 返回任意三个维度组合列表
     *
     * @param dimensionStrs 所有维度
     * @param modelName     model名
     * @param mandatoryDims 强制维度
     * @param timeDims      时间维度
     * @return 返回任意三个维度组合列表
     */
    private static List<AggregationGroup> createAggregationGroup(ArrayList<String> dimensionStrs, String modelName, List<String> mandatoryDims, String timeDims) {

        ArrayList<AggregationGroup> aggregationGroups = new ArrayList<>();
        dimensionStrs.remove(timeDims);


        for (int x = 0; x < mandatoryDims.size(); x++) {
            String mandatoryDim = mandatoryDims.get(x);

            for (int i = 0; i < dimensionStrs.size(); i++) {

                for (int j = i + 1; j < dimensionStrs.size(); j++) {

                    if (!mandatoryDim.equals(dimensionStrs.get(i)) && !mandatoryDim.equals(dimensionStrs.get(j))) {

                        if (x < 1) {
                            AggregationGroup aggregationGroup = new AggregationGroup();
                            ArrayList<String> includes = new ArrayList<>();

                            for (String s : mandatoryDim.split(",")) {
                                includes.add(modelName + "." + s);
                            }

                            for (String s : dimensionStrs.get(i).split(",")) {
                                includes.add(modelName + "." + s);
                            }

                            for (String s : dimensionStrs.get(j).split(",")) {
                                includes.add(modelName + "." + s);
                            }

                            includes.add(modelName + "." + timeDims);
                            aggregationGroup.setIncludes(includes);

                            SelectRule selectRule = new SelectRule();
                            ArrayList<String> mandatory_dims = new ArrayList<>();
                            for (String s : mandatoryDim.split(",")) {
                                mandatory_dims.add(modelName + "." + s);
                            }

                            selectRule.setMandatory_dims(mandatory_dims);
                            selectRule.setHierarchy_dims(new ArrayList<String>());

                            ArrayList<ArrayList<String>> Joint_dims = new ArrayList<>();


                            if (dimensionStrs.get(i).split(",").length > 1) {
                                ArrayList<String> Joint_dim = new ArrayList<>();
                                for (String s : dimensionStrs.get(i).split(",")) {
                                    Joint_dim.add(modelName + "." + s);
                                }
                                Joint_dims.add(Joint_dim);

                            }

                            if (dimensionStrs.get(j).split(",").length > 1) {
                                ArrayList<String> Joint_dim = new ArrayList<>();
                                for (String s : dimensionStrs.get(j).split(",")) {
                                    Joint_dim.add(modelName + "." + s);
                                }
                                Joint_dims.add(Joint_dim);

                            }

                            selectRule.setJoint_dims(Joint_dims);

                            aggregationGroup.setSelect_rule(selectRule);

                            aggregationGroups.add(aggregationGroup);
                        } else if (!dimensionStrs.get(i).equals(mandatoryDims.get(x - 1)) && !dimensionStrs.get(j).equals(mandatoryDims.get(x - 1))) {
                            AggregationGroup aggregationGroup = new AggregationGroup();
                            ArrayList<String> includes = new ArrayList<>();

                            for (String s : mandatoryDim.split(",")) {
                                includes.add(modelName + "." + s);
                            }

                            for (String s : dimensionStrs.get(i).split(",")) {
                                includes.add(modelName + "." + s);
                            }

                            for (String s : dimensionStrs.get(j).split(",")) {
                                includes.add(modelName + "." + s);
                            }

                            includes.add(modelName + "." + timeDims);
                            aggregationGroup.setIncludes(includes);

                            SelectRule selectRule = new SelectRule();
                            ArrayList<String> mandatory_dims = new ArrayList<>();
                            for (String s : mandatoryDim.split(",")) {
                                mandatory_dims.add(modelName + "." + s);
                            }

                            selectRule.setMandatory_dims(mandatory_dims);
                            selectRule.setHierarchy_dims(new ArrayList<String>());

                            ArrayList<ArrayList<String>> Joint_dims = new ArrayList<>();


                            if (dimensionStrs.get(i).split(",").length > 1) {
                                ArrayList<String> Joint_dim = new ArrayList<>();
                                for (String s : dimensionStrs.get(i).split(",")) {
                                    Joint_dim.add(modelName + "." + s);
                                }
                                Joint_dims.add(Joint_dim);

                            }

                            if (dimensionStrs.get(j).split(",").length > 1) {
                                ArrayList<String> Joint_dim = new ArrayList<>();
                                for (String s : dimensionStrs.get(j).split(",")) {
                                    Joint_dim.add(modelName + "." + s);
                                }
                                Joint_dims.add(Joint_dim);

                            }

                            selectRule.setJoint_dims(Joint_dims);

                            aggregationGroup.setSelect_rule(selectRule);

                            aggregationGroups.add(aggregationGroup);
                        }
                    }
                }
            }
        }
        return aggregationGroups;
    }
}
