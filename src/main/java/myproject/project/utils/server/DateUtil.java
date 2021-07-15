package myproject.project.utils.server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    //获取今天的日期
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
