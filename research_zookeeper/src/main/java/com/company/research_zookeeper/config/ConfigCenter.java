package com.company.research_zookeeper.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-08-21 22:07:44
 */
@Slf4j
public class ConfigCenter {

    private final static String CONNECT_STR = "192.168.114.134:2181";

    private final static Integer SESSION_TIMEOUT = 30 * 1000;


    private static ZooKeeper zooKeeper;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Watcher watcher = new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getType() == Event.EventType.None
                        && watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    log.info("连接已建立");
                    countDownLatch.countDown();
                }
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged
                        && Objects.equals(watchedEvent.getPath(), "/myConfig")) {
                    log.info("PATH:{}", watchedEvent.getPath());
                    byte[] data = zooKeeper.getData("/myConfig", this, null);
                    ObjectMapper objectMapper = new ObjectMapper();
                    ZooBean newZooBean = objectMapper.readValue(new String(data), ZooBean.class);
                    log.info("数据发生变化 {}", newZooBean);
                }
            }
        };
        zooKeeper = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, watcher);
        countDownLatch.await();
        ZooBean zooBean = new ZooBean();
        zooBean.setKey("key1");
        zooBean.setValue("v1");

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] data = objectMapper.writeValueAsBytes(zooBean);
        String s = zooKeeper.create("/myConfig", data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        log.info("s:->{}", s);
        byte[] dataOriginal = zooKeeper.getData("/myConfig", watcher, null);

        ZooBean zooBeanOriginal = objectMapper.readValue(dataOriginal, ZooBean.class);
        log.info("原始数据:{}", zooBeanOriginal);

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
