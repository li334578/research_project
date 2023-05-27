import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Data;
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
}
