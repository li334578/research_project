import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
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

    private List<String> perm3(List<String> setNo, int start, int end) {
        if (start == end) {
            return setNo;
        } else {
            for (int i = start; i <= end; i++) {
                String temp = setNo.get(start);
                setNo.set(start, setNo.get(i));
                setNo.set(i, temp);
                perm3(setNo, start + 1, end);
                temp = setNo.get(start);
                setNo.set(start, setNo.get(i));
                setNo.set(i, temp);
            }
        }
        return null;
    }

    @Test
    public void testMethod4() {
//        CountDownLatch countDownLatch = new CountDownLatch(100 * 1000);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 1000; j++) {
//                new Thread(() -> {
//                    List<String> setList = new ArrayList<>();
//                    setList.add("T398");
//                    setList.add("T291");
//                    setList.add("T001");
//                    ArrayList<ArrayList<String>> sortResult = AllSort.getSort(new ArrayList<>(setList));
//                    sortResult.stream().map(item -> String.join("|", item)).forEach(System.out::println);
//                    countDownLatch.countDown();
//                }).start();
//            }
//        }
//
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        log.info("总耗时为{} ms,每次耗时为{}", end - start, (end - start) / 1000);

        List<String> setList = new ArrayList<>();
        setList.add("T345-4");
        setList.add("T347-4");
        setList.add("T380-1");
        setList.add("T425");
        setList.add("T291");
        // T345-4|T347-4|T380-1|T425|T291
        ArrayList<ArrayList<String>> sortResult = AllSort.getSort(new ArrayList<>(setList));
        sortResult.stream().map(item -> String.join("|", item)).forEach(System.out::println);
    }

    @Test
    public void testMethod5() {
        Map<String, Integer> map = new HashMap<>();
        Set<String> allEmployeeSet = new HashSet<>();
        allEmployeeSet.add("zhangsan");
        allEmployeeSet.add("lisi");
        allEmployeeSet.add("wangwu");
        for (String employee : allEmployeeSet) {
            // 每个员工给三千底薪
            map.computeIfAbsent(employee, v -> 3000);
        }
        // 奖金
        Set<String> bonusEmployeeSet = new HashSet<>();
        bonusEmployeeSet.add("zhangsan");
        bonusEmployeeSet.add("lisi");
        for (String employee : bonusEmployeeSet) {
            map.computeIfPresent(employee, (k, v) -> v + 500);
        }
        log.info("======");
        map.forEach((k, v) -> log.info("{}的工资为{}", k, v));
    }

    @Test
    public void testMethod6() {
        Map<String, Integer> map = new HashMap<>();
        Set<String> allEmployeeSet = new HashSet<>();
        allEmployeeSet.add("zhangsan");
        allEmployeeSet.add("lisi");
        allEmployeeSet.add("wangwu");
        for (String employee : allEmployeeSet) {
            // 每个员工给三千底薪
            map.computeIfAbsent(employee, v -> 3000);
        }
        // 有奖金的员工的基础工资的和
        AtomicReference<Integer> bonusCount = new AtomicReference<>(0);
        // 奖金
        Set<String> bonusEmployeeSet = new HashSet<>();
        bonusEmployeeSet.add("zhangsan");
        bonusEmployeeSet.add("lisi");
        for (String employee : bonusEmployeeSet) {
            map.computeIfPresent(employee, (k, v) -> {
                bonusCount.updateAndGet(v1 -> v1 + v);
                return v + 500;
            });
        }
        log.info("======");
        map.forEach((k, v) -> log.info("{}的工资为{}", k, v));
        log.info("有奖金的员工的基础工资的和{}", bonusCount.get());
    }
}
