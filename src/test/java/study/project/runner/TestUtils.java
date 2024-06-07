package study.project.runner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {
    public static String getUniqueName(String value) {
        return value + new SimpleDateFormat("HHmmssSS").format(new Date());
    }
}
