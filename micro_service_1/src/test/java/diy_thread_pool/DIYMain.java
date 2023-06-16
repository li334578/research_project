package diy_thread_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Date 15/6/2023 0015 上午 10:57
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
public class DIYMain {
    public static void main(String[] args) {
        DIYMyBlockingQueue instance = DIYMyBlockingQueue.getInstance(50, 100);
        DIYMyThreadPool diyMyThreadPool = new DIYMyThreadPool(5, 10, 10, TimeUnit.SECONDS,
                instance, Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        instance.setDiyMyThreadPool(diyMyThreadPool);
        for (int i = 1; i <= 10000; i++) {
            DIYMyTask diyMyTask = new DIYMyTask(i);
            diyMyThreadPool.execute(diyMyTask);
        }
        System.out.println("1234");
        diyMyThreadPool.shutdown();
    }
}
