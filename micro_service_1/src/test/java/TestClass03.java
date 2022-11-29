import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * @Date 28/11/2022 0028 下午 3:37
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class TestClass03 {
    @Test
    public void testMethod1() {
        // 获取上下边界内的随机数 [origin,bound)
        ThreadLocalRandom.current().nextInt(0, 10);
    }

    @Test
    public void testMethod2() {
        String str = "120,130";
        String s1 = ReUtil.get(",(\\d+)$", str, 1);
        String s2 = ReUtil.get(",?(\\d+)$", "130", 1);
        String s21 = ReUtil.get(",?(\\d+)$", "wadea", 1);
        String s3 = ReUtil.get("find(?:able)?", "find", 0);
        Pattern pattern = Pattern.compile("find(?:able)");
        String s31 = ReUtil.get(pattern, "find", 0);
        String s4 = ReUtil.get("find(?:able)", "findable", 0);
        log.info("xxx");
    }
}
