import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    public void testMethod7() {
        Pattern.compile("\\d").splitAsStream("1879465464654").forEach(System.out::println);
    }

    @Test
    public void testMethod8() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        threadPoolExecutor.submit(() -> System.out.println(123));

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(() -> System.out.println(123));
    }

    @Test
    public void testMethod9() {
        ThreadLocal<String> local = new ThreadLocal<>();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
            local.set(Thread.currentThread().getName() + ":" + i);
            System.out.println("线程：" + Thread.currentThread().getName() + ",local:" + local.get());
            countDownLatch.countDown();
        }).start());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMethod10() {
        Integer i1 = new Integer(100);
        Integer i2 = new Integer(100);
        System.out.println(i1 == i2);
    }

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2000), new ThreadPoolExecutor.CallerRunsPolicy());
    @Test
    public void testMethod11() {
        CountDownLatch countDownLatch = new CountDownLatch(50);
        long begin = System.currentTimeMillis();
        List<Integer> collect = IntStream.range(0, 50).parallel().mapToObj(i -> {
            FutureTask<Integer> future = new FutureTask<>(() -> {
                if (i < 5) {
                    TimeUnit.SECONDS.sleep(5);
                }
                if (!Thread.currentThread().isInterrupted()) {
                    log.info("finalI = {} 当前线程是 {}", i, Thread.currentThread().getName());
                }
                return i;
            });
            threadPoolExecutor.submit(future);
            try {
                return future.get(1, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException | TimeoutException e) {
                log.error("走到异常了 {}", i);
                countDownLatch.countDown();
                future.cancel(true);
                return null;
            }
        }).filter(Objects::nonNull).peek(item -> countDownLatch.countDown()).collect(Collectors.toList());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        collect.forEach(System.out::println);
        threadPoolExecutor.shutdown();
        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");
    }
    @Test
    public void testMethod12() {
        Faker faker = new Faker();

        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton

        String streetAddress = faker.address().streetAddress();


        log.info("name is {}, {} , {} , {}", name, firstName, lastName, streetAddress);
    }


    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException{
        new Thread(() -> {
            while (flag) {
                // do sth
            }
            System.out.println("end");
        }).start();
        Thread.sleep(1000);
        flag = false;
    }
}
