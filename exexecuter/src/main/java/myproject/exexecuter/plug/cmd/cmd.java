package myproject.exexecuter.plug.cmd;

import myproject.exexecuter.datax.LogStatistics;
import myproject.exexecuter.util.ProcessUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static myproject.exexecuter.datax.DataXConstant.*;

public class cmd {


    public static void cmdProcess(List<String> cmdArr) {
        final Process process;
        try {
            process = Runtime.getRuntime().exec(cmdArr.toArray(new String[cmdArr.size()]));
            String prcsId = ProcessUtil.getProcessId(process);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuffer errorStringBuffer = new StringBuffer();
            System.out.println("prcsId" + prcsId);

            while ((line = reader.readLine()) != null) {
                System.out.println(line);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String args[]) {
        List<String> cmdArr = new ArrayList<>();
        cmdArr.add("python");
        cmdArr.add("F:\\SoftFile\\datax\\datax\\bin\\datax.py ");
        cmdArr.add("F:\\SoftFile\\datax\\datax\\bin\\stream2stream.json ");

       // cmdProcess(cmdArr);

        cmdArr.clear();
        cmdArr.add("java");
        cmdArr.add("-version");

        cmdProcess(cmdArr);

    }


}
