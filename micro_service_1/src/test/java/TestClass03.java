import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
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

    @Test
    public void testMethod13() {
        // 所有key计算过程一致的时候适用
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().maximumSize(2)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了！");
                        return "cache-" + key;
                    }
                });

        System.out.println(loadingCache.getUnchecked("key1"));
        System.out.println(loadingCache.getUnchecked("key1"));

        System.out.println(loadingCache.getUnchecked("key2"));
        System.out.println(loadingCache.getUnchecked("key2"));
    }

    @Test
    public void testMethod14() throws ExecutionException {
        // 不同key不同计算方式的情况
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        Object cacheKey1 = cache.get("key1", () -> {
            System.out.println("key1真正计算了");
            return "key1计算方式";
        });
        System.out.println(cacheKey1);

        cacheKey1 = cache.get("key1", () -> {
            System.out.println("key1真正计算了");
            return "key1计算方式";
        });
        System.out.println(cacheKey1);

        Object cacheKey2 = cache.get("key2", () -> {
            System.out.println("key2真正计算了");
            return "key2计算方式";
        });
        System.out.println(cacheKey2);

        cacheKey2 = cache.get("key2", () -> {
            System.out.println("key2真正计算了");
            return "key2计算方式";
        });
        System.out.println(cacheKey2);
    }

    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (flag) {
                // do sth
            }
            System.out.println("end");
        }).start();
        Thread.sleep(1000);
        flag = false;
    }

    @Test
    public void testMethod15() throws ExecutionException {
        // 直接put进去
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        cache.put("key1", "cache-key1");
        // 缓存中没有值会使用后边的
        System.out.println(cache.get("key1", () -> "callable cache-key1"));
    }

    @Test
    public void testMethod16() {
        // leetCode 338
        System.out.println(countBitPart1(5));
        System.out.println(countBitPart1(4));
        System.out.println(countBitPart1(3));
        System.out.println(countBitPart1(2));
        System.out.println(countBitPart1(1));
        System.out.println(countBitPart1(0));
        System.out.println(Arrays.toString(countBits1(5)));
        System.out.println(Arrays.toString(countBits2(5)));
        //
        System.out.println(Arrays.toString(countBits3(5)));

//        System.out.println(countBitPart2(5, new int[6]));
    }

    /**
     * 暴力解法
     */
    public int[] countBits1(int n) {
        int[] ints = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            ints[i] = countBitPart1(i);
        }
        return ints;
    }

    public int countBitPart1(int n) {
        if (n == 0 || n == 1) return n;
        int count = 0;
        int temp = n >>> 1;
        if (temp << 1 != n) {
            count += 1;
        }
        return count + countBitPart1(n >>> 1);
    }

    /**
     * 记忆数组
     */
    public int[] countBits2(int n) {
        if (n == 0) return new int[]{0};
        int[] ints = new int[n + 1];
        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = n; i >= 0; i--) {
            ints[i] = countBitPart2(i, arr);
        }
        return ints;
    }

    public int countBitPart2(int n, int[] arr) {
        if (n == 0 || arr[n] != 0) return arr[n];
        int count = 0;
        int temp = n >>> 1;
        if (temp << 1 != n) {
            count += 1;
        }
        // c(1) + c(2)
        int result = count + countBitPart2(n >>> 1, arr);
        arr[n] = result;
        return result;
    }

    /**
     * 动态规划
     */
    public int[] countBits3(int n) {
        if (n == 0) return new int[]{0};
        int[] ints = new int[n + 1];
        ints[0] = 0;
        ints[1] = 1;
        for (int i = 2; i <= n; i++) {
            int index, temp;
            temp = i >>> 1;
            index = temp << 1 == i ? 0 : 1;
            ints[i] = ints[index] + ints[temp];
        }
        return ints;
    }

    @Test
    public void testMethod17() {
        // 设置了最大缓存条目为3 如果maximumSize传入0，则所有key都将不进行缓存！
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().maximumSize(3)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });

        System.out.println("第一次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("开始剔除");
        loadingCache.getUnchecked("key4");

        System.out.println("第三次访问");
        loadingCache.getUnchecked("key3");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod18() {
        // weigher 设置权重值 如果所有缓存的key的权重之和大于了我们指定的最大权重，那么将执行LRU淘汰策略：
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().maximumWeight(6).weigher((key, value) -> {
                    if (key.equals("key1")) {
                        return 1;
                    }
                    if (key.equals("key2")) {
                        return 2;
                    }
                    if (key.equals("key3")) {
                        return 3;
                    }

                    if (key.equals("key4")) {
                        return 1;
                    }
                    return 0;
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });

        System.out.println("第一次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("开始剔除");
        loadingCache.getUnchecked("key4");
        loadingCache.getUnchecked("key3");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod19() throws Exception {
        // expireAfterAccess 当某个缓存key自最后一次访问（读取或者写入）超过指定时间后，那么这个缓存key将失效。
        // expireAfterWrite 这个方法只关心写入
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });

        System.out.println("第一次访问（写入）");
        loadingCache.getUnchecked("key1");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");

        TimeUnit.SECONDS.sleep(3);
        System.out.println("过3秒后访问");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod20() throws Exception {
        // expireAfterWrite 这个方法只关心写入
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });
        for (int i = 0; i < 4; i++) {
            System.out.println(new Date());
            loadingCache.getUnchecked("key1"); // 首次执行的时候，为写入
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Test
    public void testMethod21() throws ExecutionException {
        // 不同key不同计算方式的情况
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        Object cacheKey1 = cache.get("key1", () -> {
            System.out.println("key1真正计算了");
            return "key1计算方式";
        });
        System.out.println(cacheKey1);

        cacheKey1 = cache.get("key1", () -> {
            System.out.println("key1真正计算了");
            return "key1计算方式";
        });
        System.out.println(cacheKey1);

        Object cacheKey2 = cache.get("key2", () -> {
            System.out.println("key2真正计算了");
            return "key2计算方式";
        });
        System.out.println(cacheKey2);

        cacheKey2 = cache.get("key2", () -> {
            System.out.println("key2真正计算了");
            return "key2计算方式";
        });
        System.out.println(cacheKey2);
    }

    @Test
    public void testMethod22() throws ExecutionException {
        // 直接put进去
        Cache<Object, Object> cache = CacheBuilder.newBuilder().build();
        cache.put("key1", "cache-key1");
        // 缓存中没有值会使用后边的
        System.out.println(cache.get("key1", () -> "callable cache-key1"));
    }

    @Test
    public void testMethod23() {
        // 设置了最大缓存条目为3 如果maximumSize传入0，则所有key都将不进行缓存！
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().maximumSize(3)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });

        System.out.println("第一次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("开始剔除");
        loadingCache.getUnchecked("key4");

        System.out.println("第三次访问");
        loadingCache.getUnchecked("key3");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod24() {
        // weigher 设置权重值 如果所有缓存的key的权重之和大于了我们指定的最大权重，那么将执行LRU淘汰策略：
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().maximumWeight(6).weigher((key, value) -> {
                    if (key.equals("key1")) {
                        return 1;
                    }
                    if (key.equals("key2")) {
                        return 2;
                    }
                    if (key.equals("key3")) {
                        return 3;
                    }

                    if (key.equals("key4")) {
                        return 1;
                    }
                    return 0;
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });

        System.out.println("第一次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key3");

        System.out.println("开始剔除");
        loadingCache.getUnchecked("key4");
        loadingCache.getUnchecked("key3");
        loadingCache.getUnchecked("key2");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod25() throws Exception {
        // expireAfterAccess 当某个缓存key自最后一次访问（读取或者写入）超过指定时间后，那么这个缓存key将失效。
        // expireAfterWrite 这个方法只关心写入
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });

        System.out.println("第一次访问（写入）");
        loadingCache.getUnchecked("key1");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");

        TimeUnit.SECONDS.sleep(3);
        System.out.println("过3秒后访问");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod26() throws Exception {
        // expireAfterWrite 这个方法只关心写入
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });
        for (int i = 0; i < 4; i++) {
            System.out.println(new Date());
            loadingCache.getUnchecked("key1"); // 首次执行的时候，为写入
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Data
    private static class MyKey {
        String key;

        public MyKey(String key) {
            this.key = key;
        }
    }

    @Test
    public void testMethod27() throws Exception {
        // CacheBuilder.weakKeys() 当我们使用了weakKeys() 后，Guava cache将以弱引用 的方式去存储缓存key,
        // 那么根据弱引用的定义：当发生垃圾回收时，不管当前系统资源是否充足，弱引用都会被回收
        // CacheBuilder.weakValues() 针对的是缓存值！
        // CacheBuilder.softValues() 软引用.当发生垃圾回收时，只有当系统资源不足时，才会回收！。
        // 主动清除缓存
        // Cache.invalidate(key)
        // Cache.invalidateAll(keys)
        // Cache.invalidateAll()
        LoadingCache<MyKey, String> loadingCache = CacheBuilder.newBuilder().weakKeys()
                .build(new CacheLoader<MyKey, String>() {
                    @Override
                    public String load(MyKey key) {
                        System.out.println(key.getKey() + "真正计算了");
                        return "cached-" + key.getKey();
                    }
                });

        MyKey key = new MyKey("key1");
        System.out.println("第一次访问");
        loadingCache.getUnchecked(key);
        System.out.println(loadingCache.asMap());

        System.out.println("第二次访问");
        loadingCache.getUnchecked(key);
        System.out.println(loadingCache.asMap());

        System.out.println("key失去强引用GC后访问");
        key = null;
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(loadingCache.asMap());
    }

    @Test
    public void testMethod28() throws Exception {
        // 缓存失效监听器
        // 如果缓存失效后，我不再进行任何操作，那么这个缓存监听器就得不到调用！
        // Guava cache也提供给我们主动清理的方法：Cache.cleanUp()
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().removalListener(notification -> {
            System.out.printf("缓存 %s 因为 %s 失效了，它的value是%s%n", notification.getKey(), notification.getCause(),
                    notification.getValue());
        }).expireAfterAccess(3, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                System.out.println(key + "真正计算了");
                return "cached-" + key;
            }
        });

        System.out.println("第一次访问（写入）");
        loadingCache.getUnchecked("key1");

        System.out.println("第二次访问");
        loadingCache.getUnchecked("key1");
        TimeUnit.SECONDS.sleep(3);

        System.out.println("3秒后");
        loadingCache.getUnchecked("key1");
    }

    @Test
    public void testMethod29() throws InterruptedException {
        // CacheBuilder中提供了refreshAfterWrite 用来指定缓存key写入多久后重新进行计算并缓存
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) {
                        System.out.println(key + "真正计算了");
                        return "cached-" + key;
                    }
                });
        for (int i = 0; i < 3; i++) {
            loadingCache.getUnchecked("key1");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    private int invokeCount = 0;

    public int realAction(int num) {
        invokeCount++;
        System.out.printf("当前执行第 %d 次,num:%d%n", invokeCount, num);
        if (num <= 0) {
            throw new IllegalArgumentException();
        }
        return num;
    }

    @Test
    public void testMethod30() {
        // 通过RetryerBuilder来构造一个重试器，通过RetryerBuilder可以设置什么时候需要重试（即重试时机）、停止重试策略、失败等待时间间隔策略、任务执行时长限制策略
        Retryer<Integer> retryer = RetryerBuilder.<Integer>newBuilder()
                // 非正常进行重试
                .retryIfRuntimeException()
                // 偶数则进行重试
                .retryIfResult(result -> result % 2 == 0)
                // 设置最大执行次数3次
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)).build();

        try {
            invokeCount = 0;
            retryer.call(() -> realAction(0));
        } catch (Exception e) {
            System.out.println("执行0，异常：" + e.getMessage());
        }

        try {
            invokeCount = 0;
            retryer.call(() -> realAction(1));
        } catch (Exception e) {
            System.out.println("执行1，异常：" + e.getMessage());
        }

        try {
            invokeCount = 0;
            retryer.call(() -> realAction(2));
        } catch (Exception e) {
            System.out.println("执行2，异常：" + e.getMessage());
        }
    }

    @Test
    public void testMethod31() {
        System.out.println(new Date());
        RateLimiter rateLimiter = RateLimiter.create(10);
    }

    @Test
    public void testMethod32() {
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(false);
        list.add(true);
        list.add(false);
        list.add(null);
        List<Boolean> collect = list.stream().sorted((o1, o2) -> o1 == null ? 1 : o2.compareTo(o1)).collect(Collectors.toList());
        System.out.println(collect);
    }

    private String reg = "([A|C|G|T|I|D|\\d]?[G|N|u|l|P|r|e|s|n|t]*)\\.?([A|C|G|T|I|D|\\d]?[G|N|u|l|P|r|e|s|n|t]*)";
    private String resultFormat = "{}:{}";

    @Test
    public void testMethod33() {
        System.out.println(convertResult("Present.Null"));
        System.out.println(convertResult("Null.Present"));
        System.out.println(convertResult("T"));
        System.out.println(convertResult("CC"));
        System.out.println(convertResult("1G"));
        System.out.println(convertResult("1G.2G"));
        System.out.println(convertResult("2G"));
        System.out.println(convertResult("Pass"));
        System.out.println(convertResult("Fail"));
        System.out.println("==================");
        System.out.println(convertResult(""));
        System.out.println(convertResult("ID"));
        System.out.println(convertResult("DI"));
        System.out.println(convertResult("II"));
        System.out.println(convertResult("D"));
        System.out.println(isDifferenceWithinFivePercent(new BigDecimal("0.10"), new BigDecimal("0.1905")));
        System.out.println(isDifferenceWithinFivePercent(new BigDecimal("0.10"), new BigDecimal("0.08")));
        System.out.println(isDifferenceWithinFivePercent(new BigDecimal("0.10"), new BigDecimal("0.0405")));
        System.out.println(resortGeneResult("AG"));
        System.out.println(resortGeneResult("GA"));
        System.out.println(resortGeneResult("CG"));
        System.out.println(resortGeneResult("GC"));
        System.out.println(resortGeneResult("AA"));
    }


    public boolean isDifferenceWithinFivePercent(BigDecimal a, BigDecimal b) {
        BigDecimal difference = a.subtract(b).abs(); // 计算绝对差值
        BigDecimal fivePercent = new BigDecimal("0.05"); // 5%
        return difference.compareTo(fivePercent) <= 0; // 判断绝对差值是否小于等于5%
    }

    public String convertResult(String result) {
        if (Objects.equals(result, "Pass") || Objects.equals(result, "Fail")) {
            return result;
        }
        String g1 = ReUtil.get(reg, result, 1);
        String g2 = ReUtil.get(reg, result, 2);
        if (StrUtil.isEmpty(g2)) {
            if (StrUtil.isEmpty(g1)) {
                return "";
            } else {
                return StrUtil.format(resultFormat, g1, g1);
            }
        } else {
            if (Objects.equals(g2, "Present") && Objects.equals(g1, "Null")) {
                return StrUtil.format(resultFormat, g2, g1);
            } else {
                return StrUtil.format(resultFormat, g1, g2);
            }
        }
    }

    public static String resortGeneResult(String geneResult) {
        if (StrUtil.isNotEmpty(geneResult)) {
            return Arrays.stream(geneResult.split("")).sorted().collect(Collectors.joining());
        }
        return null;
    }

    @Test
    public void testMethod34() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("C");
        list.add("C");
        list.add("Q");
        list.add("Q");
        list.add("W");
        list.add("W");
        for (int i = 0; i < list.size(); i++) {
            String s1 = list.get(i);
            if (i != 0) {
                String s2 = list.get(i - 1);
                if (Objects.equals(s1, s2)) {
                    list.remove(i);
                }
            }
        }
        System.out.println(list);
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("1", "1");
    }

    @Test
    public void testMethod35() {
        int i = -10;
        if (i >>> 1 << 1 == i) {
            System.out.println("偶数");
        } else {
            System.out.println("奇数");
        }
        System.out.println(i);
    }

    @Test
    public void testMethod36() {
        System.out.println(StrUtil.repeat("0", String.valueOf(1000).length()));
        System.out.println(StrUtil.repeat("0", String.valueOf(10000).length()));
        System.out.println(StrUtil.repeat("0", String.valueOf(10).length()));
    }

    @Test
    public void testMethod37() {
        System.out.println(func((a, b) -> a + b, 10, 2));
        System.out.println(func((a, b) -> a - b, 10, 2));
        System.out.println(func((a, b) -> a * b, 10, 2));
        System.out.println(func((a, b) -> a / b, 10, 2));
        System.out.println(func((a, b) -> Math.max(a, b), 10, 2));
        System.out.println(func((a, b) -> Math.min(a, b), 10, 2));


        System.out.println(func2((a, b) -> a + b, 10, 2));
        System.out.println(func2((a, b) -> a - b, 10, 2));
        System.out.println(func2((a, b) -> a * b, 10, 2));
        System.out.println(func2((a, b) -> a / b, 10, 2));
        System.out.println(func2((a, b) -> Math.max(a, b), 10, 2));
        System.out.println(func2((a, b) -> Math.min(a, b), 10, 2));

    }

    public <T> T func(BiFunction<T, T, T> biFunction, T a, T b) {
        return biFunction.apply(a, b);
    }

    public <T> T func2(BinaryOperator<T> binaryOperator, T a, T b) {
        return binaryOperator.apply(a, b);
    }

    @Test
    public void testMethod38() {
        // 队列
        // 基于链表实现的 双向队列 LinkedList允许元素为null
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("向队首加入元素");
        linkedList.addLast("向队尾加入元素");

        boolean offerFirst = linkedList.offerFirst("向队首加入元素返回是否插入成功");
        boolean offerLast = linkedList.offerLast("向队尾加入元素,返回是否插入成功");

        // 返回队头元素(不移除)，队列为empty抛出 NoSuchElementException
        String getFirst = linkedList.getFirst();
        // 返回队尾元素(不移除)，队列为empty抛出 NoSuchElementException
        String getLast = linkedList.getLast();

        // 移除并返回队头元素，队列为empty抛出 NoSuchElementException
        String first = linkedList.removeFirst();
        // 移除并返回队尾元素，队列为empty抛出 NoSuchElementException
        String last = linkedList.removeLast();

        // 移除并返回队头元素，队列为empty 返回null
        String pollFirst = linkedList.pollFirst();
        // 移除并返回队尾元素，队列为empty 返回null
        String pollLast = linkedList.pollLast();

        // 向栈中压入元素
        linkedList.push("新元素");
        // 返回并移除队首元素，队列为empty抛出 NoSuchElementException
        String pop = linkedList.pop();

        System.out.println(linkedList);
    }

    @Test
    public void testMethod39() {
        // 队列
        // 基于链表实现的 双向队列 ArrayDeque不允许元素为null NPE
        Deque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst("向队首加入元素");
        arrayDeque.addLast("向队尾加入元素");

        boolean offerFirst = arrayDeque.offerFirst("向队首加入元素返回是否插入成功");
        boolean offerLast = arrayDeque.offerLast("向队尾加入元素,返回是否插入成功");

        // 返回队头元素(不移除)，队列为empty抛出 NoSuchElementException
        String getFirst = arrayDeque.getFirst();
        // 返回队尾元素(不移除)，队列为empty抛出 NoSuchElementException
        String getLast = arrayDeque.getLast();

        // 移除并返回队头元素，队列为empty抛出 NoSuchElementException
        String first = arrayDeque.removeFirst();
        // 移除并返回队尾元素，队列为empty抛出 NoSuchElementException
        String last = arrayDeque.removeLast();

        // 移除并返回队头元素，队列为empty 返回null
        String pollFirst = arrayDeque.pollFirst();
        // 移除并返回队尾元素，队列为empty 返回null
        String pollLast = arrayDeque.pollLast();

        // 向栈中压入元素
        arrayDeque.push("新元素");
        // 返回并移除队首元素，队列为empty抛出 NoSuchElementException
        String pop = arrayDeque.pop();

        System.out.println(arrayDeque);
    }


    @Test
    public void testMethod40() {
        StringBuilder stringBuilder = new StringBuilder("58").append("tongCheng");
        String str1 = stringBuilder.toString();
        System.out.println(str1 == str1.intern());

        StringBuilder stringBuilder2 = new StringBuilder("ja").append("va");
        String str2 = stringBuilder2.toString();
        System.out.println(str2 == str2.intern());
    }

    @Test
    public void testMethod41() {
        /*
        给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。

        你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。

        你可以按任意顺序返回答案。
        * */
        // leetCode 两数之和

        TestClass03 testClass03 = new TestClass03();


        for (int i : testClass03.twoSum(new int[]{2, 7, 11, 15}, 9)) {
            System.out.println(i);
        }
        System.out.println();
        for (int i : testClass03.twoSum(new int[]{3, 2, 4}, 6)) {
            System.out.println(i);
        }
        System.out.println();
        for (int i : testClass03.twoSum(new int[]{3, 3}, 6)) {
            System.out.println(i);
        }

    }

    public int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        if (length == 2) {
            return nums[0] + nums[1] == target ? new int[]{0, 1} : new int[]{};
        }
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }


    public int[] twoSum2(int[] nums, int target) {
        int length = nums.length;
        if (length == 2) {
            return nums[0] + nums[1] == target ? new int[]{0, 1} : new int[]{};
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            int num = target - nums[i];
            if (map.containsKey(num)) {
                return new int[]{map.get(num), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }

    @Test
    public void testMethod42() {
        Thread a = new Thread(() -> {
            System.out.printf("%s被阻塞\n", Thread.currentThread().getName());
            LockSupport.park();
            System.out.printf("%s被唤醒\n", Thread.currentThread().getName());
        }, "A");
        a.start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread b = new Thread(() -> {
            System.out.printf("%s去唤醒\n", Thread.currentThread().getName());
            LockSupport.unpark(a);
        }, "B");
        b.start();
    }

    @Test
    public void testMethod43() {
        /*Lock lock = new ReentrantLock();
        lock.lock();*/
        // 三个线程顺序执行 用lockSupport实现
        Thread t3 = new Thread(() -> {
            LockSupport.park();
            System.out.printf("%s执行了\n", Thread.currentThread().getName());
        }, "T3");
        Thread t2 = new Thread(() -> {
            LockSupport.park();
            System.out.printf("%s执行了\n", Thread.currentThread().getName());
            LockSupport.unpark(t3);
        }, "T2");
        Thread t1 = new Thread(() -> {
            System.out.printf("%s执行了\n", Thread.currentThread().getName());
            LockSupport.unpark(t2);
        }, "T1");
        t1.start();
        t2.start();
        t3.start();

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
    }


    @Test
    public void testMethod44() throws Exception {
        // 队列
        // 基于链表实现的 双向队列 ArrayDeque不允许元素为null NPE
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        linkedBlockingQueue.remove();
        System.out.println(linkedBlockingQueue);
        Class<LinkedBlockingQueue> linkedBlockingQueueClass = LinkedBlockingQueue.class;
        Constructor<LinkedBlockingQueue> constructor = linkedBlockingQueueClass.getConstructor();
        LinkedBlockingQueue linkedBlockingQueue1 = constructor.newInstance();
    }


    public static void permutation(String[] arr, int start, int end) {
        if (start == end) {
            System.out.println(String.join("", arr));
        } else {
            for (int i = start; i <= end; i++) {
                if (!isDuplicate(arr, start, i)) {
                    swap(arr, start, i);
                    permutation(arr, start + 1, end);
                    swap(arr, start, i);
                }
            }
        }
    }

    public static boolean isDuplicate(String[] arr, int start, int end) {
        for (int i = start; i < end; i++) {
            if (arr[i].equals(arr[end])) {
                return true;
            }
        }
        return false;
    }

    public static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Test
    public void testMethod45() {
        String[] arr = {"a", "b", "c"};
        permutation(arr, 0, arr.length - 1);
    }

    static {
        _i = 20;
    }

    public static int _i = 10;

    @Test
    public void testMethod46() {
        System.out.println(TestClass03._i);
    }

    // @EqualsAndHashCode
    @Test
    public void testMethod47() {
        List<Pair<String, Double>> pairArrayList = new ArrayList<>(2);
        pairArrayList.add(new Pair<>("version1", 8.3));
        pairArrayList.add(new Pair<>("version2", null));
        // 抛出 NullPointerException 异常
        Map<String, Double> map = pairArrayList.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));

        log.info("123");
    }

    @Test
    public void testMethod48() {
        //初始化
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        List<Integer> subList = arrayList.subList(1, 2);

        //修改
        System.out.println(" modify arrayList");
        arrayList.remove(0);

        //遍历
        for (Integer integer : subList) {
            System.out.println(" " + integer);//?????
        }
    }

}
