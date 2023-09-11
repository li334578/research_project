package com.example.micro_service_5_zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@Slf4j
class MicroService5ZkApplicationTests {

    @Test
    void contextLoads() {
    }


    static ZooKeeper zooKeeper;

    @BeforeAll
    public static void before() throws IOException, InterruptedException {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper("192.168.36.12:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            log.info("【初始化ZooKeeper连接状态....】={}", zooKeeper.getState());

        } catch (Exception e) {
            log.error("初始化ZooKeeper连接异常....】={}", e);
        }
    }

    @Test
    public void testMethod1() throws InterruptedException, KeeperException {
        zooKeeper.create("/java1", "node1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    @AfterAll
    public static void after() throws InterruptedException {
        zooKeeper.close();
        System.out.println("after");
    }
}
