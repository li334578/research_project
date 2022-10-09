import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.timingwheel.TimerTask;
import cn.hutool.cron.timingwheel.TimerTaskList;
import cn.hutool.cron.timingwheel.TimingWheel;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date 1/8/2022 0001 下午 1:54
 * @Description 测试类
 * @Version 1.0.0
 * @Author liwenbo
 */
@Slf4j
public class TestClass02 {

    @Test
    public void testMethod1() {
        BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(10);
        bitMapBloomFilter.add("aaa");
        bitMapBloomFilter.add("ccc");
        bitMapBloomFilter.add("bbb");
        // 查找
        boolean abc = bitMapBloomFilter.contains("abc");


        long mNum = NumberUtil.div(String.valueOf(10), String.valueOf(5)).longValue();
        long size = mNum * 1024L * 1024L * 8L;

        System.out.println(mNum);
        System.out.println(size);
    }


    @Test
    public void testMethod2() {

        String content = "ZZZaaabbbccc中文1234";
        String resultExtractMulti = ReUtil.extractMulti("(\\w)aa(\\w)", content, "$1-$2");

        System.out.println(resultExtractMulti);
    }

    @Test
    public void testMethod3() {
//        Student student = new Student(1L, "张三", 18);
//        Student clone = ObjectUtil.clone(student);
//        System.out.println(student.hashCode());
//        System.out.println(clone.hashCode());
    }

    @Test
    public void testMethod4() {
        String username = "lwb008";
        int index = Math.abs(username.hashCode()) % username.length();
        System.out.println(index);

        Digester md5 = new Digester(DigestAlgorithm.MD5);
        Digester md52 = new Digester(DigestAlgorithm.MD5);
        String c = String.valueOf(username.charAt(index));
        md5.setSalt(c.getBytes(StandardCharsets.UTF_8));
        String hex = md5.digestHex("123456");
        String hex2 = md52.digestHex("123456");
        System.out.println(hex);
        System.out.println(hex2);
    }


    @Test
    public void testMethod5() throws Exception {
        Map<Integer, Integer> map = new HashMap<>(7, 0.7f);
        // 通过反射获取容量变量capacity,并调用map对象
        Method capacity = map.getClass().getDeclaredMethod("capacity");
        capacity.setAccessible(true);
        System.out.println(capacity.invoke(map));
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        System.out.println(capacity.invoke(map));
        map.put(6, 6);
        System.out.println(capacity.invoke(map));
        map.put(7, 7);
        System.out.println(capacity.invoke(map));
        // 三大方法
        Executors.newFixedThreadPool(5);
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        // 七大参数 核心线程数 最大线程数 空闲线程存活时间 存活时间的单位 创建线程的工厂 存放任务的队列 拒绝策略

        // 四种拒绝策略 disCardPolicy 静默拒绝 disOldestPolicy 丢弃队首 尝试把当前任务加进去
        // abortPolicy 直接拒绝 抛出异常 callerRunsPolicy 调用人执行


        // callerRunsPolicy 调用者执行
        // disCardPolicy 静默拒绝
        // disOldestPolicy 把队首任务丢弃 新任务加入
        // abortPolicy 直接拒绝 并抛出异常 (RejectExecutionException)

        // disCardPolicy 静默拒绝
        // disOldestPolicy 丢弃队首任务 将新任务加入队尾
        // abortPolicy 直接拒绝 抛出RejectExecutionException异常
        // callerRunsPolicy 调用线程执行该任务

        // 三次握手

        // a -> seq=x b

        // a seq=y ack=x+1 <- b

        // a ack=y+1 -> b


    }

    @Test
    public void testMethod6() {
        ThreadPoolExecutor myThreadPool = new ThreadPoolExecutor(2, 5, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            myThreadPool.submit(() -> System.out.println(Thread.currentThread().getName()));
        }
    }

    @Test
    public void testMethod7() {
        System.out.println(101 / 20 + 1);
        ConcurrentHashMap<Integer, Integer> cMap = new ConcurrentHashMap<>();
        cMap.put(2, 2);
        System.out.println(cMap.get(1));
    }

    @Test
    public void testMethod8() {
        System.out.println(DateUtil.format(DateUtil.date(), "yyyyddMM"));

        if (ReUtil.isMatch("CGSH20220304" + "\\d{4}", "CGSH202203040002")) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }

        String g0 = ReUtil.getGroup0("CGSH20220304(\\d{4})", "CGSH202203040002");
        String g1 = ReUtil.getGroup1("CGSH20220304(\\d{4})", "CGSH202203040002");
        System.out.println(g0);
        System.out.println(g1);
        Integer integer = Integer.valueOf(g1);
        System.out.println(NumberUtil.decimalFormat("0000", integer + 1));
        System.out.println(NumberUtil.decimalFormat("0000", 562));
    }

    @Test
    public void testMethod9() {
        System.out.println(CalendarUtil.calendar().getTime());
    }

    @Test
    public void testMethod10() {
        ExecutorService executorService = Executors.newWorkStealingPool();
    }

    @Test
    public void testMethod11() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(list);
    }


    @Test
    public void testMethod12() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("2".equals(item)) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }


    @Test
    public void testMethod13() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.removeIf("2"::equals);
        System.out.println(list);
    }


    @Test
    public void testMethod1_1() {
        // 正则表达式，提取xxx.xxx.xxx.xxx，将IP地址从接口返回结果中提取出来
        String rexp = ".*\"ip\":\"(.*)\",\"geo.*";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher("{\"ip\":\"240e:38a:8e31:1d00:fcce:c0e9:b91:f047\",\"geo-ip\":\"https://getjsonip.com/#plus\",\"API Help\":\"https://getjsonip.com/#docs\"}");
        String res = "";
        while (mat.find()) {
            res = mat.group(1);
            break;
        }
        log.info(res);
    }

    @Test
    public void testMethod1_2() throws InterruptedException {
        TimerTask timerTask = new TimerTask(() -> {
            log.info("yyy");
        }, 0L);
        TimingWheel timingWheel = new TimingWheel(1L, 20, 0L, taskList -> {
            TimerTaskList taskList1 = taskList;
            taskList1.addTask(timerTask);
        });
        Thread.sleep(1000L);
    }

    @Test
    public void testMethod1_3() {
        int a = 1;
        int b = a << 1;
        int c = a << 2;
        log.info("b:" + b);
        log.info("c:" + c);
//        method(null);
        method(1L);
        method2(new MyEntityCondition());
//        throw Exceptions.NOT_FIND_ROLE.exception();
    }

    private String method(@NonNull Long id) {
        log.info("id is [{}]", id);
        return "xx";
    }

    private String method2(@NonNull MyEntityCondition condition) {
        log.info("id is [{}]", condition.getId());
        log.info("name is [{}]", condition.getName());
        Lists.newArrayList();
        CollUtil.newArrayList();
        return "xx";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class MyEntityCondition {
        @NotNull
        private Long id;
        @NonNull
        private String name;
    }

    @Test
    public void testMethod1_4() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行step 1");
            return "step1 result";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行step 2");
            return "step2 result";
        });
        cf1.thenCombine(cf2, (result1, result2) -> {
            System.out.println(result1 + " , " + result2);
            System.out.println("执行step 3");
            return "step3 result";
        }).thenAccept(System.out::println).exceptionally(err -> {
            log.error("e" + err.getCause());
            return null;
        });
    }

    @Test
    public void testMethod14() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        map.forEach((k, v) -> {

        });
    }

}
