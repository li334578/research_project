import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
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

    public static void perm(char[] buf, int start, int end) {
        if (start == end) {//当只要求对数组中一个字母进行全排列时，只要就按该数组输出即可（特殊情况）
            for (int i = 0; i <= end; i++) {
                System.out.print(buf[i]);
            }
            System.out.println();
        } else {//多个字母全排列（普遍情况）
            for (int i = start; i <= end; i++) {//（让指针start分别指向每一个数）
                char temp = buf[start];//交换数组第一个元素与后续的元素
                buf[start] = buf[i];
                buf[i] = temp;
                perm(buf, start + 1, end);//后续元素递归全排列
                temp = buf[start];//将交换后的数组还原
                buf[start] = buf[i];
                buf[i] = temp;
            }
        }
    }

    public List<String> perm2(List<String> setNo) {
        if (CollUtil.isEmpty(setNo) || setNo.size() == 1) {
            return setNo;
        } else {
            return perm3(setNo, 0, setNo.size() - 1);
        }
    }
}
