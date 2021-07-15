package myproject.exexecuter.jobhandler;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import myproject.exexecuter.datax.Constants;
import myproject.exexecuter.datax.LogStatistics;
import myproject.exexecuter.log.ExecuteLog;
import myproject.exexecuter.mail.server.MailerServiceImpl;
import myproject.exexecuter.mail.server.Stmp;
import myproject.exexecuter.plug.Plug;
import myproject.exexecuter.plug.mongodb.reader.Column;
import myproject.exexecuter.plug.mongodb.reader.MongodbReader;
import myproject.exexecuter.plug.mongodb.reader.MongodbReaderJson;
import myproject.exexecuter.plug.oss.OssToSftp;
import myproject.exexecuter.plug.oss.OssToSftpMeta;
import myproject.exexecuter.plug.parameter.ParameterMap;
import myproject.exexecuter.plug.sftp.SftpToSftp;
import myproject.exexecuter.plug.sftp.SftpToSftpMeta;
import myproject.exexecuter.util.JdbcTemplateUtils;
import myproject.exexecuter.plug.mysql.reader.MysqlReader;
import myproject.exexecuter.plug.mysql.reader.MysqlReaderJson;
import myproject.exexecuter.plug.mysql.writer.MysqlWriter;
import myproject.exexecuter.plug.mysql.writer.MysqlWriterJson;
import myproject.exexecuter.util.ProcessUtil;
import myproject.exexecuter.util.StringUUID;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static myproject.exexecuter.datax.DataXConstant.*;

/**
 * Created by wanggenshen
 * Date: on 2018/7/7 16:28.
 * Description: 打印任意内容
 */

public class ExecutorJob implements Job {
    Map<String, Object> map = new HashMap<String, Object>();
    JdbcTemplateUtils jdbcTemplateUtils = null;
    JdbcTemplate jdbcTemplate = null;
    ExecuteLog executeLog = null;
    Gson gson = new Gson();
    String plugName = "";

    public static void main(String[] args) throws ScriptException {
        Gson gson = new Gson();

        String json = "[{\"name\":\"db\",\"type\":\"String\"},{\"name\":\"user\",\"type\":\"String\"},{\"name\":\"_id\",\"type\":\"String\"}]";
        List<Column> columns = gson.fromJson(json, new TypeToken<List<Column>>() {
        }.getType());

        System.out.println(columns.size());
    }

    public void init() throws Exception {
        try {


            jdbcTemplateUtils = new JdbcTemplateUtils();
            jdbcTemplate = jdbcTemplateUtils.getMyslJdbcTemplate();
            getBatchNo(jdbcTemplate);
            insertJobTrigger(jdbcTemplate);
            String logFileName = map.get("jobName").toString() + map.get("batchNo").toString();
            String dataxFileName = map.get("jobName").toString();

            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                dataxFileName = "/bigdata/datax/json/" + dataxFileName + ".json";
                logFileName = "/bigdata/log/" + logFileName + ".log";
            } else {
                logFileName = "D:\\log\\" + logFileName + ".log";
                dataxFileName = "F:\\SoftFile\\datax\\datax\\json\\" + dataxFileName + ".json";
            }
            map.put("logFileName", logFileName);
            map.put("dataxFileName", dataxFileName);
            executeLog = new ExecuteLog(logFileName);
        } catch (Exception e) {
            throw e;
        }

    }

    public void getBatchNo(JdbcTemplate jdbcTemplate) {
        try {
            String jobName = map.get("jobName").toString();
            String sql = "select * from my_project.job  where job_name='" + jobName + "'";
            Map<String, Object> jobMap = jdbcTemplate.queryForList(sql).get(0);
            String cron = jobMap.get("cron").toString();
            String alertMail = jobMap.get("emails").toString();
            map.put("alertMail", alertMail);
            map.put("jobLabel", jobMap.get("job_label").toString());
            String batchType = "minute";
            String dateFormat = "";
            if (batchType.equals("daily")) {
                dateFormat = "YYYYMMdd";
            } else if (batchType.equals("hour")) {
                dateFormat = "YYYYMMddHH";
            } else if (batchType.equals("minute")) {
                dateFormat = "YYYYMMddHHmm";
            }
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            // 这个是重点，一行代码搞定
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            map.put("batchNo", simpleDateFormat.format(dates.get(0)));


        } catch (ParseException e) {
            e.printStackTrace();
            executeLog.log(e.getMessage());
        }
    }

    public void insertJobTrigger(JdbcTemplate jdbcTemplate) {
        try {
            String batchNo = map.get("batchNo").toString();
            String jobName = map.get("jobName").toString();
            String teamName = map.get("jobGroup").toString();
            String jobLabel = map.get("jobLabel").toString();
            InetAddress addr = InetAddress.getLocalHost();
            jdbcTemplate.execute("update  my_project.job_trigger  set is_valid=1    where job_name='" + jobName + "' and batch_no='" + batchNo + "'");
            String uuid = UUID.randomUUID().toString();
            jdbcTemplate.execute("insert into my_project.job_trigger(job_trig_id,job_name,batch_no,team_name,broker_ip,job_label,state)" +
                    "VALUES('" + uuid + "','" + jobName + "','" + batchNo + "','" + teamName + "','" + addr + "','" + jobLabel + "','run')");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            executeLog.log(e.getMessage());
        }

    }

    public void parseDataxJson(List<Plug> plugList) throws Exception {
        try {


            String dataxJson = "{\n" +
                    "    \"job\": {\n" +
                    "        \"content\": [{\n" +
                    "                \"reader\": @reader\n," +
                    "                \"writer\": @writer\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"setting\": {\n" +
                    "            \"errorLimit\": {\n" +
                    "                \"record\": 0,\n" +
                    "                \"percentage\": 0.02\n" +
                    "            },\n" +
                    "            \"speed\": {\n" +
                    "                \"byte\": 1048576,\n" +
                    "                \"channel\": 3\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n";
            String teamName = map.get("jobGroup").toString();
            for (Plug ele : plugList) {
                ele.setData(traversePlugData(ele.getData()));
                if (ele.getMeta().getName().equals("mysqlReader")) {
                    MysqlReader mysqlReader = new MysqlReader();
                    mysqlReader.setColumns(ele.getData().get("columns"));
                    mysqlReader.setDsName(ele.getData().get("dsName"));
                    mysqlReader.setDsSchema(ele.getData().get("dsSchema"));
                    mysqlReader.setTableName(ele.getData().get("tableName"));
                    MysqlReaderJson mysqlReaderJson = new MysqlReaderJson();
                    mysqlReaderJson.setTeamName(teamName);
                    mysqlReaderJson.setJdbcTemplate(jdbcTemplate);
                    mysqlReaderJson.setMysqlReader(mysqlReader);
                    dataxJson = dataxJson.replaceAll("@reader", mysqlReaderJson.toJson());

                } else if (ele.getMeta().getName().equals("mongodbReader")) {
                    MongodbReader mongodbReader = new MongodbReader();
                    mongodbReader.setCollectionName(ele.getData().get("collectionName"));

                    String json = "[{\"name\":\"db\",\"type\":\"String\"},{\"name\":\"user\",\"type\":\"String\"},{\"name\":\"_id\",\"type\":\"String\"}]";
                    List<Column> columns = gson.fromJson(json, new TypeToken<List<Column>>() {
                    }.getType());

                    mongodbReader.setColumn(columns);
                    mongodbReader.setDsName(ele.getData().get("dsName"));
                    mongodbReader.setDsSchema(ele.getData().get("dsSchema"));
                    MongodbReaderJson mongodbReaderJson = new MongodbReaderJson();
                    mongodbReaderJson.setTeamName(teamName);
                    mongodbReaderJson.setJdbcTemplate(jdbcTemplate);
                    mongodbReaderJson.setMongodbReader(mongodbReader);
                    dataxJson = dataxJson.replaceAll("@reader", mongodbReaderJson.toJson());

                    // System.out.println("=====================");
                    // System.out.println(dataxJson);
                } else if (ele.getMeta().getName().equals("mysqlWriter")) {
                    MysqlWriter mysqlWriter = new MysqlWriter();
                    mysqlWriter.setColumns(ele.getData().get("columns"));
                    mysqlWriter.setDsName(ele.getData().get("dsName"));
                    mysqlWriter.setDsSchema(ele.getData().get("dsSchema"));
                    mysqlWriter.setPreSql(ele.getData().get("preSql"));
                    mysqlWriter.setTableName(ele.getData().get("tableName"));
                    MysqlWriterJson mysqlWriterJson = new MysqlWriterJson();
                    mysqlWriterJson.setTeamName(teamName);
                    mysqlWriterJson.setJdbcTemplate(jdbcTemplate);
                    mysqlWriterJson.setMysqlWriter(mysqlWriter);
                    dataxJson = dataxJson.replaceAll("@writer", mysqlWriterJson.toJson());
                }
                map.put("dataxJson", dataxJson);
            }
        } catch (Exception e) {
            throw e;
        }


    }

    public void parsePlug(JdbcTemplate jdbcTemplate) throws Exception {
        try {
            String jobName = map.get("jobName").toString();
            String plugData = jdbcTemplate.queryForList("select plung_data from my_project.job_graph where job_name='" + jobName + "'").get(0).get("plung_data").toString();
            //  System.out.println(plugData);

            List<Plug> plugList = gson.fromJson(plugData, new TypeToken<List<Plug>>() {
            }.getType());
            Collections.sort(plugList);
            map.put("plugList", plugList);

            for (int index = 0; index < plugList.size(); index++) {

                if (plugList.get(index).getMeta().getName().contains("Reader")) {
                    //此为datax格式的数据
                    plugName = "name:Datax" + ",num:" + index;
                    List<Plug> plugListTemp = new ArrayList<Plug>();
                    plugListTemp.add(plugList.get(index));
                    plugListTemp.add(plugList.get(++index));
                    parseDataxJson(plugListTemp);
                    createDataxJsonFile();
                    execDataxCmd();

                }
                //设置参数
                plugName = "name:" + plugList.get(index).getMeta().getName() + ",num:" + index;
                plugList.get(index).setData(traversePlugData(plugList.get(index).getData()));
                if (plugList.get(index).getMeta().getName().equals("SftpToSftp")) {
                    parseSftpToSftp(plugList.get(index));
                }
                if (plugList.get(index).getMeta().getName().equals("OssToSftp")) {
                    parseOssToSftp(plugList.get(index));
                }

                if (plugList.get(index).getMeta().getName().equals("parameters")) {
                    parseParameters(plugList.get(index));
                }
                if (plugList.get(index).getMeta().getName().equals("cmd")) {
                    parseParameters(plugList.get(index));
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }



    public void parseParameters(Plug plugList) {
        String[] parameterMapList = plugList.getData().get("parameterList").toString().split(";");
        //  k=batchNo,value=20210225,type=String;
        executeLog.log("=======参数列表=======");
        for (String ele : parameterMapList) {
            if (ele.split("=|,").length == 6) {
                ParameterMap parameterMap = new ParameterMap(ele);
                executeLog.log(parameterMap.toString());
                map.put(parameterMap.getK(), parameterMap.getValue());
            }
        }
        executeLog.log("=======参数列表=======");
    }

    public Map<String, String> traversePlugData(Map<String, String> eleData) {

        Map<String, String> eleDataTemp = new HashMap<String, String>();
        eleDataTemp.putAll(eleData);
        for (Map.Entry<String, String> eleMap : eleData.entrySet()) {
            String mapKey = eleMap.getKey();
            String mapValue = eleMap.getValue();
            mapValue = traverseParameters(mapValue);
            eleDataTemp.put(mapKey, mapValue);
        }

        return eleDataTemp;

    }

    public String traverseParameters(String str) {


        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();
            str = str.replace("$(" + mapKey + ")", mapValue.toString());
        }
        return str;
    }

    public void parseSftpToSftp(Plug plugList) throws Exception {
        try {


            SftpToSftpMeta sftpToSftpMeta = new SftpToSftpMeta();
            sftpToSftpMeta.setFileNameList(plugList.getData().get("fileNameList").toString());
            sftpToSftpMeta.setSourceDsName(plugList.getData().get("sourceDsName").toString());
            sftpToSftpMeta.setTargetDsName(plugList.getData().get("targetDsName").toString());
            sftpToSftpMeta.setTargetFilePath(plugList.getData().get("targetFilePath").toString());
            SftpToSftp sftp = new SftpToSftp(sftpToSftpMeta, executeLog, jdbcTemplate);
        } catch (Exception e) {
            throw e;
        }
    }

    public void parseOssToSftp(Plug plugList) {
        try {

            OssToSftpMeta ossToSftpMeta = new OssToSftpMeta();
            ossToSftpMeta.setFileNameList(plugList.getData().get("fileNameList").toString());
            ossToSftpMeta.setSourceDsName(plugList.getData().get("sourceDsName").toString());
            ossToSftpMeta.setTargetDsName(plugList.getData().get("targetDsName").toString());
            ossToSftpMeta.setTargetFilePath(plugList.getData().get("targetFilePath").toString());
            OssToSftp ossToSftp = new OssToSftp(ossToSftpMeta, executeLog, jdbcTemplate);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createDataxJsonFile() throws Exception {
        try {
            String fileName = map.get("dataxFileName").toString();
            String dataxJson = map.get("dataxJson").toString();
            File dataxFile = new File(fileName);
            if (dataxFile.exists()) {
                dataxFile.delete();
            }
            dataxFile.createNewFile();
            FileWriter fileWriter = new FileWriter(dataxFile);
            fileWriter.write(dataxJson);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            executeLog.log(e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    public void deleteTempFile() {
        String fileName = map.get("dataxFileName").toString();
        File dataxFile = new File(fileName);
        if (dataxFile.exists()) {
            dataxFile.delete();
        }


    }

    public void destroy() {
        try {
            deleteTempFile();
            executeLog.log("==========================================任务参数");
            executeLog.log("==========================================任务结束");
            setJobRunState();
            jdbcTemplateUtils.close();
            executeLog.close();
        } catch (IOException e) {
            executeLog.log(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            executeLog.log(e.getMessage());
        }
    }


    public void sendAlertMail() {
        Object error = map.get("error");
        String batchNo = map.get("batchNo").toString();
        String jobName = map.get("jobName").toString();
        if (error != null) {

            executeLog.log("=====================");
            executeLog.log(map.get("alertMail").toString());
            //     MailerServiceImpl mailerService = new MailerServiceImpl();
            String[] mails = map.get("alertMail").toString().split(",");

            String content = jobName + batchNo + error.toString();
            executeLog.log("=====================" + content);
            //   mailerService.sendSimpleTextMailActual("任务失败警告", content, mails, null, null, null);
            executeLog.log("=====================");
            Stmp.sendMail(mails, content);
        }


    }

    public void setJobRunState() {

        String batchNo = map.get("batchNo").toString();
        String jobName = map.get("jobName").toString();
//        String teamName = map.get("jobGroup").toString();
//        String jobLabel = map.get("jobLabel").toString();
        Object error = map.get("error");


        String sql = "";
        if (error != null) {
            sql = "update my_project.job_trigger set state='error', message='" + error.toString().substring(1, 10) + "'  where job_name='" + jobName + "' and batch_no='" + batchNo + "';";

        } else {
            sql = "update my_project.job_trigger set state='success'  where job_name='" + jobName + "' and batch_no='" + batchNo + "';";
        }
        jdbcTemplate.update(sql);

    }

    public void execDataxCmd() {
        List<String> cmdArr = new ArrayList<>();
        String fileName = map.get("dataxFileName").toString();
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            cmdArr.add("python");
            cmdArr.add("/bigdata/datax/bin/datax.py");
            cmdArr.add(fileName);
        } else {
            cmdArr.add("python");
            cmdArr.add("F:\\SoftFile\\datax\\datax\\bin\\datax.py");
            cmdArr.add(fileName);
        }
        executeLog.log(cmdArr.toString());
        cmdProcess(cmdArr);

    }

    public void cmdProcess(List<String> cmdArr) {
        final Process process;
        try {
            process = Runtime.getRuntime().exec(cmdArr.toArray(new String[cmdArr.size()]));
            String prcsId = ProcessUtil.getProcessId(process);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuffer errorStringBuffer = new StringBuffer();
            LogStatistics logStatistics = new LogStatistics();
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(TASK_START_TIME_SUFFIX)) {
                    logStatistics.setTaskStartTime(subResult(line));
                } else if (line.contains(TASK_END_TIME_SUFFIX)) {
                    logStatistics.setTaskEndTime(subResult(line));
                } else if (line.contains(TASK_TOTAL_TIME_SUFFIX)) {
                    logStatistics.setTaskTotalTime(subResult(line));
                } else if (line.contains(TASK_AVERAGE_FLOW_SUFFIX)) {
                    logStatistics.setTaskAverageFlow(subResult(line));
                } else if (line.contains(TASK_RECORD_WRITING_SPEED_SUFFIX)) {
                    logStatistics.setTaskRecordWritingSpeed(subResult(line));
                } else if (line.contains(TASK_RECORD_READER_NUM_SUFFIX)) {
                    logStatistics.setTaskRecordReaderNum(Integer.parseInt(subResult(line)));
                } else if (line.contains(TASK_RECORD_WRITING_NUM_SUFFIX)) {
                    logStatistics.setTaskRecordWriteFailNum(Integer.parseInt(subResult(line)));
                }
                if (line.contains("经DataX智能分析,该任务最可能的错误原因是:")) {
                    flag = true;
                }
                if (flag) {
                    errorStringBuffer.append(line);
                }
                executeLog.log(line, false);
                System.out.println(line);
            }
            if (flag) {
                executeLog.log("=========我进来了4=================");
                map.put("error", errorStringBuffer.toString());
                sendAlertMail();
            }
            System.out.println("==========dataxLog===================");
            System.out.println(logStatistics);
            saveDataxTranNum(logStatistics);
        } catch (Exception e) {
            executeLog.log(e.getMessage());
        } finally {

        }
    }

    public void saveDataxTranNum(LogStatistics logStatistics) {
        String id = StringUUID.UUID();
        String batchNo = map.get("batchNo").toString();
        String jobName = map.get("jobName").toString();
        int num = logStatistics.getTaskRecordReaderNum();
        // plugName;
        jdbcTemplate.execute("update  my_project.jobTranNum_log  set is_valid=1    where job_name='" + jobName + "' and batch_no='" + batchNo + "' and plug_name='" + plugName + "'");

        jdbcTemplate.execute("insert into my_project.jobTranNum_log(id,job_name,batch_no,plug_name,num)" +
                "VALUES('" + id + "','" + jobName + "','" + batchNo + "','" + plugName + "','" + num + "')");


    }

    public String subResult(String line) {
        if (StringUtils.isBlank(line)) return Constants.STRING_BLANK;
        int pos = line.indexOf(Constants.SPLIT_SCOLON);
        if (pos > 0) return line.substring(pos + 1).trim();
        return line.trim();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {

            JobKey key = jobExecutionContext.getJobDetail().getKey();
            map.put("jobName", key.getName());
            map.put("jobGroup", key.getGroup());
            init();
            parsePlug(jdbcTemplate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            e.printStackTrace();
            String error = "";
            if (e.getMessage() == null) {
                error = e.getCause().getMessage();
            } else {
                error = e.getMessage();
            }
            executeLog.log(error);
            map.put("error", error);
            sendAlertMail();
        } finally {
            destroy();

        }
    }
}

