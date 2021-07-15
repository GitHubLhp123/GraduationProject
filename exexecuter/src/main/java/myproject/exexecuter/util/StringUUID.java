package myproject.exexecuter.util;

import java.util.UUID;

public class StringUUID {
    public static String UUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
