package com.shujia.common.kylin;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class KylinRestFulApi {

    private static String encoding;

    private static final String baseURL;

    static {
        baseURL = KylinConstant.KYLIN_BASEURL;

        byte[] key =  (KylinConstant.KYLIN_USERNAME + ":" + KylinConstant.KYLIN_PASSWORD).getBytes();
        encoding = Base64.encodeBase64String(key).trim();
    }

    public static String createCube(String cubeDescData, String project, String cubeName) {

        String method = "POST";

        String para = "/cubes";
        cubeDescData = cubeDescData.replaceAll("\"", "\\\\\"");
        cubeDescData = cubeDescData.replaceAll("[\r\n]", " ");

        cubeDescData = cubeDescData.trim();

        String body = "{" + "\"cubeDescData\":" + "\"" + cubeDescData + "\"" +

                ",\"cubeName\" : \"" + cubeName + "\"" +

                ",\"project\" :  \"" + project + "\"" +

                "}";
        System.out.println(body);

        return excute(para, method, body);

    }


    public static String createModel(String project, String modelName,String modelDescData ) {

//        String modelDescData = readJsonFile("/Users/qinxiao/Documents/project/ctyun/daapl-tour/src/test/java/kylin_model.json");
//
        String method = "POST";

        String para = "/models";
        modelDescData = modelDescData.replaceAll("\"", "\\\\\"");
        modelDescData = modelDescData.replaceAll("[\r\n]", " ");

        modelDescData = modelDescData.trim();

        String body = "{" + "\"modelDescData\":" + "\"" + modelDescData + "\"" +

                ",\"modelName\" : \"" + modelName + "\"" +

                ",\"project\" :  \"" + project + "\"" +

                "}";
        System.out.println(body);

        return excute(para, method, body);

    }


//    public static void main(String[] args) {
//        KylinRestFulApi.createModel("test", "product_tour_city_day_index");
//    }

    private static String readJsonFile(String cubeDescFile) {
        File filename = new File(cubeDescFile);
        StringBuilder lines = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = br.readLine();
            while (line != null) {
                lines.append(line);
                line = br.readLine();
            }
            br.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(lines);
        return lines.toString();


    }


    public static String login(String user, String passwd) {
        String method = "POST";
        String para = "/user/authentication";
        byte[] key = (user + ":" + passwd).getBytes();
        encoding = Base64.encodeBase64String(key);
        return excute(para, method, null);
    }

    public static String listQueryableTables(String projectName) {

        String method = "GET";
        String para = "/tables_and_columns?project=" + projectName;

        return excute(para, method, null);

    }


    /**
     * @param offset      required int Offset used by pagination
     * @param limit       required int Cubes per page.
     * @param cubeName    optional string Keyword for Cube names. To find cubes whose name contains this keyword.
     * @param projectName optional string Project name.
     * @return
     */
    public static String listCubes(int offset,
                                   int limit,
                                   String cubeName,
                                   String projectName) {
        String method = "GET";
        String para = "/cubes?offset=" + offset
                + "&limit=" + limit
                + "&cubeName=" + cubeName
                + "&projectName=" + projectName;
        return excute(para, method, null);
    }

    /**
     * @param cubeName Cube name.
     * @return
     */
    public static String getCubeDes(String cubeName) {
        String method = "GET";
        String para = "/cube_desc/" + cubeName;
        return excute(para, method, null);

    }


    /**
     * @param cubeName
     * @return
     */
    public static String getCube(String cubeName) {
        String method = "GET";
        String para = "/cubes/" + cubeName;
        return excute(para, method, null);

    }


    /**
     * @param modelName Data model name, by default it should be the same with Cube name.
     * @return
     */
    public static String getDataModel(String modelName) {
        String method = "GET";
        String para = "/model/" + modelName;
        return excute(para, method, null);

    }

    /**
     * @param cubeName cubeName Cube name.
     * @return
     */
    public static String enableCube(String cubeName) {

        String method = "PUT";
        String para = "/cubes/" + cubeName + "/enable";
        return excute(para, method, null);

    }

    /**
     * @param cubeName Cube name.
     * @return
     */
    public static String disableCube(String cubeName) {

        String method = "DELETE";
        String para = "/cubes/" + cubeName + "";
        return excute(para, method, null);

    }

    /**
     * @param cubeName Cube name.
     * @return
     */
    public static String purgeCube(String cubeName) {

        String method = "PUT";
        String para = "/cubes/" + cubeName + "/purge";
        return excute(para, method, null);

    }


    /**
     * @param jobId Job id
     * @return
     */
    public static String resumeJob(String jobId) {

        String method = "PUT";
        String para = "/jobs/" + jobId + "/resume";
        return excute(para, method, null);

    }


    /**
     * startTime - required long Start timestamp of data to build, e.g. 1388563200000 for 2014-1-1
     * endTime - required long End timestamp of data to build
     * buildType - required string Supported build type: ‘BUILD’, ‘MERGE’, ‘REFRESH’
     *
     * @param cubeName Cube name.
     * @return
     */
    public static String buildCube(String cubeName, String body) {
        String method = "PUT";
        String para = "/cubes/" + cubeName + "/rebuild";

        return excute(para, method, body);
    }


    /**
     * @param jobId Job id.
     * @return
     */
    public static String discardJob(String jobId) {

        String method = "PUT";
        String para = "/jobs/" + jobId + "/cancel";
        return excute(para, method, null);

    }

    /**
     * @param jobId Job id.
     * @return
     */
    public static String getJobStatus(String jobId) {

        String method = "GET";
        String para = "/jobs/" + jobId;
        return excute(para, method, null);

    }

    /**
     * @param jobId  Job id.
     * @param stepId Step id; the step id is composed by jobId with step sequence id;
     *               for example, the jobId is “fb479e54-837f-49a2-b457-651fc50be110”, its 3rd step id
     *               is “fb479e54-837f-49a2-b457-651fc50be110-3”,
     * @return
     */
    public static String getJobStepOutput(String jobId, String stepId) {
        String method = "GET";
        String para = "/" + jobId + "/steps/" + stepId + "/output";
        return excute(para, method, null);
    }

    /**
     * @param tableName table name to find.
     * @return
     */
    public static String getHiveTable(String tableName) {
        String method = "GET";
        String para = "/tables/" + tableName;
        return excute(para, method, null);
    }

    /**
     * @param tableName table name to find.
     * @return
     */
    public static String getHiveTableInfo(String tableName) {
        String method = "GET";
        String para = "/tables/" + tableName + "/exd-map";
        return excute(para, method, null);
    }


    /**
     * @param projectName will list all tables in the project.
     * @param extOptional boolean set true to get extend info of table.
     * @return
     */
    public static String getHiveTables(String projectName, boolean extOptional) {
        String method = "GET";
        String para = "/tables?project=" + projectName + "&ext=" + extOptional;
        return excute(para, method, null);
    }


    /**
     * @param tables  table names you want to load from hive, separated with comma.
     * @param project the project which the tables will be loaded into.
     * @return
     */
    public static String loadHiveTables(String tables, String project) {
        String method = "POST";
        String para = "/tables/" + tables + "/" + project;
        return excute(para, method, null);
    }

    /**
     * @param type   ‘METADATA’ or ‘CUBE’
     * @param name   Cache key, e.g the Cube name.
     * @param action ‘create’, ‘update’ or ‘drop’
     * @return
     */
    public static String wipeCache(String type, String name, String action) {
        String method = "POST";
        String para = "/cache/" + type + "/" + name + "/" + action;
        return excute(para, method, null);
    }


    public static String query(String body) {
        String method = "POST";
        String para = "/query";

        return excute(para, method, body);
    }


    private static String excute(String para, String method, String body) {

        StringBuilder out = new StringBuilder();
        try {
            URL url = new URL(baseURL + para);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding+"");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (body != null) {
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
                os.close();
            }
            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                out.append(line);
            }
            in.close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }


    public static void main(String[] args) {
        System.out.println(KylinRestFulApi.getCube("product_tour_source_month_index"));
//        KylinRestFulApi.disableCube("product_tour_city_month_index");
    }


}
