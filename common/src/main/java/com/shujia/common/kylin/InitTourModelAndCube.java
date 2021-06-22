package com.shujia.common.kylin;


import com.shujia.common.kylin.cube.MakeCubeJson;
import com.shujia.common.kylin.model.MakeModelJson;

import java.util.ArrayList;

public class InitTourModelAndCube {

    public static void main(String[] args) {

        createCityDayCube();


    }


    public static void createCityDayCube() {
        //度量
        ArrayList<String> measureStr = new ArrayList<>();
        measureStr.add("only_pt");

        //通用的维度
        ArrayList<String> mandatoryDims = new ArrayList<>();
        mandatoryDims.add("D_CITY_ID");

        ArrayList<String> dimensionStrs = new ArrayList<>();
        dimensionStrs.add("DAY_ID");

        dimensionStrs.addAll(mandatoryDims);
        dimensionStrs.add("O_CITY_ID");

        String factName = "dal_tour_city_day_index";

        String modeldescription = "市天model";

        String timeDims = "DAY_ID";

        String partitionFormat = KylinConstant.KYLIN_DAY_PARTITION_FORMAT;

        String modelJson = MakeModelJson.CreateModelJson( factName, factName, modeldescription, dimensionStrs, measureStr, timeDims, partitionFormat);

        System.out.println(modelJson);

        KylinRestFulApi.createModel(KylinConstant.KYLIN_PROJECT, factName, modelJson);

        String description = "市天cube";

        ArrayList<String> measureArr = new ArrayList<>();
        measureArr.add("_COUNT_,COUNT,constant,1,bigint");

        for (String s : measureStr) {
            measureArr.add(String.format("%s,SUM,column,%s.%s,bigint", s, factName, s));
        }

        String cubejson = MakeCubeJson.CreateCubeJson(factName, factName, description, dimensionStrs, mandatoryDims, timeDims, measureArr);
        System.out.println(cubejson);
        System.out.println(KylinRestFulApi.createCube(cubejson, KylinConstant.KYLIN_PROJECT, factName));

    }

}
