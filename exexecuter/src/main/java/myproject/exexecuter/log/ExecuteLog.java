package myproject.exexecuter.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecuteLog implements AutoCloseable {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private FileWriter fileWritter = null;

    public ExecuteLog(String fileName) {
        createLogFile(fileName);

        try {
            this.fileWritter = new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws Exception {

        fileWritter.flush();
        fileWritter.close();
    }

    public void createLogFile(String fileName) {

        File file = new File(fileName);
        try {
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String logDetail(StackTraceElement call, String appendLog) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(simpleDateFormat.format(new Date())).append(" ")
                .append("[" + call.getFileName().replace("java", "") + call.getMethodName())
                .append("-" + call.getLineNumber() + "]").append(" ")
                .append(appendLog != null ? appendLog : "");
        System.out.println(buffer.toString());
        return buffer.toString();
    }

    public void log(String log) {
        StackTraceElement callInfo = new Throwable().getStackTrace()[1];
        log = logDetail(callInfo, log);
        write(log + "\r\n");
    }

    public void log(String log, boolean isLogDetail) {
        write(log + "\r\n");
    }

    public void write(String stringLog) {
        try {
            fileWritter.write(stringLog);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        ExecuteLog executeLog = new ExecuteLog("test.log");
        executeLog.log("你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦你的地位撒擦");
        try {
            executeLog.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
