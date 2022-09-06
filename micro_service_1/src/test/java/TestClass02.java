import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.company.micro_service_1.bean.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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
}
