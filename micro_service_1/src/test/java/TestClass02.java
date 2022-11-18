import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.timingwheel.TimerTask;
import cn.hutool.cron.timingwheel.TimerTaskList;
import cn.hutool.cron.timingwheel.TimingWheel;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.icepear.echarts.Bar;
import org.icepear.echarts.render.Engine;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
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

    @Test
    public void testMethod15() {
        Instant now = Instant.now();
        System.out.println(now);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTimeFormatter.format(now));
    }

    @Test
    public void testMethod16() {
        String param = null;
        switch (param) {
            // 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
                // fixme
        }

    }

    @Test
    public void testMethod17() {
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("t2");
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            System.out.println("t3");
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t3.start();
        t2.start();
        t1.start();
    }

    @Test
    public void testMethod18() {
        ReentrantLock lock = new ReentrantLock();
        ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
        Semaphore semaphore = new Semaphore(5);
        semaphore.release(5);
        try {
            semaphore.acquire(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        cyclicBarrier.getParties();
    }

    @Test
    public void testMethod19() {
        ExecutorService executorService = Executors.newFixedThreadPool(3, new NamedThreadFactory("myThread-", false));
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            log.info("C1" + Thread.currentThread().getName());
        }, executorService);

        CompletableFuture<Void> c2 = CompletableFuture.runAsync(() -> {
            log.info("C2" + Thread.currentThread().getName());
        }, executorService);

        log.info("main ");
        CompletableFuture.allOf(c1, c2);
    }

    @Test
    public void testMethod20() {
        ExecutorService executorService = Executors.newFixedThreadPool(3, new NamedThreadFactory("myThread-", false));
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("come in");
            int a = 1 / 0;
            return ThreadLocalRandom.current().nextInt(10);
        }, executorService).whenComplete((v, e) -> {
            log.info("v{}  e{}", v, e);
        }).exceptionally(e -> {
            log.error("报错了 .{}", e.getMessage());
            return 0;
        });

        Integer join = integerCompletableFuture.join();
        log.info("join {}", join);
    }

    @Test
    public void testMethod21() {
        CompletableFuture.supplyAsync(() -> 1).thenRun(() -> {
            System.out.println("嘿嘿");
        }).join();

        CompletableFuture.supplyAsync(() -> 1).thenAccept(item -> System.out.println(item * 2)).join();

        Integer result = CompletableFuture.supplyAsync(() -> 1).thenApply(item -> item * 2).join();
        log.info("result {}", result);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
        });

//        voidCompletableFuture.applyToEither()
    }

    @Test
    public void testMethod22() {
        ThreadLocal<Integer> threadLocal1 = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };
        ThreadLocal<Integer> threadLocal2 = ThreadLocal.withInitial(() -> 0);

        ThreadLocal<Integer> threadLocal3 = ThreadLocal.withInitial(() -> 0);

        LongAdder longAdder = new LongAdder();
    }

    @Test
    public void testMethod23() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(list.get(i), "B")) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }

    public boolean myMethod1() {
        log.info(" 模拟操作redis");
        return false;
    }

    @Test
    public void testMethod24() {
        for (int i = 0; i < 5; i++) {
            // 自旋操作 每五秒重试一次 5 次后不再重试
            try {
                if (myMethod1()) {
                    break;
                }
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("end ....");
    }

    @Test
    public void testMethod25() {
        int count = 0;
        while (count < 5 && !myMethod1()) {
            // 进来了说明失败了
            try {
                count++;
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("end ....");
    }

    @Test
    public void testMethod26() {
        StampedLock stampedLock = new StampedLock();
    }

    /**
     * 给你一个长度固定的整数数组 arr，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
     * <p>
     * 注意：请不要在超过该数组长度的位置写入元素。
     * <p>
     * 要求：请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/duplicate-zeros
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public void duplicateZeros(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == 0) {
                int[] tempArr = new int[arr.length - i];
                System.arraycopy(arr, i + 1, tempArr, 0, arr.length - i - 1);
                arr[i + 1] = 0;
                System.arraycopy(tempArr, 0, arr, i + 2, arr.length - i - 2);
                i++;
            }
        }
    }

    @Test
    public void testMethod27() {
        int[] ints = {0, 1, 7, 6, 0, 2, 0, 7};
        duplicateZeros(ints);

        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void testMethod28() {
        Lock lock = new ReentrantLock();
        StampedLock stampedLock = new StampedLock();

        String str = "aa bb,cc,dd";
        str.split(",|\\s");
        str.split("/[\\s,]/g");
        String replace = str.replace("/[\\s,]/", "1");
        Pattern p = Pattern.compile("/[\\s,]/g");
        Matcher s = p.matcher("s");
        log.info("dxd");
    }

    public void duplicateZeros2(int[] arr) {
        int n = arr.length;
        int top = 0;
        int i = -1;
        while (top < n) {
            i++;
            if (arr[i] != 0) {
                top++;
            } else {
                top += 2;
            }
        }
        int j = n - 1;
        if (top == n + 1) {
            arr[j] = 0;
            j--;
            i--;
        }
        while (j >= 0) {
            arr[j] = arr[i];
            j--;
            if (arr[i] == 0) {
                arr[j] = arr[i];
                j--;
            }
            i--;
        }
    }


//    // Jedis 缓存对象
//    private JedisClient jedisClient;
//    // 商品信息XML映射类
//    private ProductInfoMapper productInfoMapper;
//    // 缓存业务KEY前缀
//    private static final String PRODUCT_KEY = "项目名.模块名.业务名.";
//    // 锁-实例
//    private Lock lock = new ReentrantLock();
//
//    /**
//     * 获取商品图片路径
//     * @param id
//     */
//    public /** synchronized */ String getProductImgUrlById(String id){
//        // 获取缓存
//        String product = jedisClient.get(PRODUCT_KEY + id);
//        if (null == product) {
//            // 如果没有获取锁等待3秒，SECONDS代表：秒
//            try {
//                if (lock.tryLock(3, TimeUnit.SECONDS)) {
//                    try {
//                        // 获取锁后再查一次，查到了直接返回结果
//                        product = jedisClient.get(PRODUCT_KEY + id);
//                        if (null == product) {
//                            // 查询数据库
//                            product = productInfoMapper.getProductInfoById(id);
//                            if (null == product) {
//                                // 假设有10000人的并发量，第一次查也没有数据，
//                                // 那么设定为"null"，加入有效期6秒
//                                jedisClient.setex((PRODUCT_KEY+id), "null", 6);
//                                return product;
//                            }
//                            jedisClient.set((PRODUCT_KEY + id), product);
//                            return product;
//                        }
//                        return product;
//                    } catch (Exception e) {
//                        product = jedisClient.get(PRODUCT_KEY + id);
//                    } finally {
//                        // 释放锁（成功、失败都必须释放，如果是lock.tryLock()方法会一直阻塞在这）
//                        lock.unlock();
//                    }
//                } else {
//                    product = jedisClient.get(PRODUCT_KEY + id);
//                }
//            } catch (InterruptedException e) {
//                product = jedisClient.get(PRODUCT_KEY + id);
//            }
//        }
//        return product;
//    }


    @Test
    public void testMethod29() {
        @Data
        @AllArgsConstructor
        class UserBean implements Serializable {
            private Integer id;
            private String name;
            private Integer age;

//            @Override
//            protected UserBean clone() throws CloneNotSupportedException {
//                UserBean u1 = new UserBean(this.id, this.name, this.age);
//                return u1;
//            }
        }

        class UserBeans implements Cloneable {

        }
        UserBean userBean1 = new UserBean(1, "张三1", 20);
        UserBean userBean2 = new UserBean(2, "张三2", 20);
        UserBean userBean3 = new UserBean(3, "张三3", 20);
        UserBean userBean4 = new UserBean(4, "张三4", 20);
        UserBean userBean5 = new UserBean(5, "张三5", 20);
        UserBean userBean6 = new UserBean(6, "张三6", 20);
        UserBean userBean7 = new UserBean(7, "张三7", 20);
        List<UserBean> userBeans = new ArrayList<>();
        userBeans.add(userBean1);
        userBeans.add(userBean2);
        userBeans.add(userBean3);
        userBeans.add(userBean4);
        userBeans.add(userBean5);
        userBeans.add(userBean6);
        userBeans.add(userBean7);
        List<UserBean> userBeans2 = new ArrayList<>();
        userBeans2.addAll(userBeans);
//        for (UserBean userBean : userBeans) {
//            try {
//                userBeans2.add(userBean.clone());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        userBean1.setAge(30);
        System.out.println("1231");
    }

    @Test
    public void testMethod30() {
        System.out.println(ReUtil.getGroup1("(\\d*)-", "1735-WB001-wb001-1"));
    }

    @Test
    public void testMethod31() {
        @Data
        @ToString
        class UserBean {
            private Integer age = 1;
            private String name;
        }
        UserBean userBean1 = new UserBean();
        userBean1.setName("张三");
        UserBean userBean2 = new UserBean();
        userBean2.setAge(userBean1.getAge() + 1);
        userBean2.setName("李四");
        log.info(userBean1.toString());
        log.info(userBean2.toString());
    }

    @Test
    public void testMethod32() {
        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("execute ...");
                phaser.arrive();
            }).start();
        }

        phaser.awaitAdvance(0);
        log.info("end");
    }

    @Test
    public void testMethod33() {
        System.out.println(39 / 20 + 1);
    }

    @Test
    public void testMethod34() throws com.taobao.api.ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?access_token=token");
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent("测试文本消息 小钉");
        request.setText(text);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList("手机号"));
// isAtAll类型如果不为Boolean，请升级至最新SDK
        at.setIsAtAll(true);
//        at.setAtUserIds(Arrays.asList("109929", "32099"));
        request.setAt(at);

        request.setMsgtype("link");
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl("https://www.dingtalk.com/");
        link.setPicUrl("");
        link.setTitle("时代的火车向前开");
        link.setText("这个即将发布的新版本，创始人小钉称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
        request.setLink(link);

        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("杭州天气");
        markdown.setText("#### 杭州天气 @18737843824\n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
                "> 小钉 ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n" +
                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
        request.setMarkdown(markdown);
        OapiRobotSendResponse response = client.execute(request);
    }


    @Test
    public void testMethod35() {
//        AtomicInteger
//        Collections.synchronizedCollection()
//        Lock lock = new ReentrantLock();
//        Condition condition = lock.newCondition();
//        condition.
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(1, 1);
        boolean a = stampedReference.compareAndSet(1, 2, 1, 2);
        boolean b = stampedReference.compareAndSet(2, 3, 1, 2);
        log.info("A result is :" + a);
        log.info("B result is :" + b);
    }
    @Test
    public void testMethod36() {
        Bar bar = new Bar()
                .setLegend()
                .setTooltip("item")
                .addXAxis(new String[]{"Matcha Latte", "Milk Tea", "Cheese Cocoa", "Walnut Brownie"})
                .addYAxis()
                .addSeries("2015", new Number[]{43.3, 83.1, 86.4, 72.4})
                .addSeries("2016", new Number[]{85.8, 73.4, 65.2, 53.9})
                .addSeries("2017", new Number[]{93.7, 55.1, 82.5, 39.1});
        Engine engine = new Engine();
        // The render method will generate our EChart into a HTML file saved locally in the current directory.
        // The name of the HTML can also be set by the first parameter of the function.
        engine.render("index.html", bar);
    }

}
