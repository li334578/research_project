package com.company.micro_service_1.controller;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.util.BooleanUtil;
import com.company.micro_service_1.bean.Product;
import com.company.micro_service_1.service.ProductService;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cache/")
public class CachePenetrationController {

    @Resource
    private ProductService productService;

    @Resource
    private Redisson redisson;

    private BitMapBloomFilter filter;


    @RequestMapping("/getProductById1/{productId}")
    public Product cacheMethod1(@PathVariable("productId") Long productId) {
        String productRedisKey = "productKey:" + productId;
        RBucket<Product> productRBucket = redisson.getBucket(productRedisKey);
        RLock lock = redisson.getLock("productKey" + productId);
        for (; ; ) {
            if (Objects.isNull(productRBucket.get())) {
                if (lock.tryLock()) {
                    try {
                        // 加锁成功
                        Product product = productService.getById(productId);
                        if (Objects.isNull(product)) {
                            productRBucket.set(new Product());
                        } else {
                            productRBucket.set(product);
                        }
                        return productRBucket.get();
                    } finally {
                        if (lock.isHeldByCurrentThread()) {
                            lock.unlock();
                        }
                    }
                } else {
                    // 加锁失败
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // 直接返回
                return productRBucket.get();
            }
        }
    }


    @PostConstruct
    public void initial() {
        List<Product> list = productService.list();
        filter = new BitMapBloomFilter(list.size());
        // redisson的布隆过滤器
        RBloomFilter<Object> productList = redisson.getBloomFilter("productList");
        //初始化,预计元素数量为100000000,期待的误差率为4%
        productList.tryInit(100000000,0.04);
        // 添加元素
        productList.add("123");

        list.forEach(item -> {
            filter.add(String.valueOf(item.getId()));
            // 将数据缓存到redis中
//            String productRedisKey = "productKey:" + item.getId();
//            RBucket<Product> productRBucket = redisson.getBucket(productRedisKey);
//            productRBucket.set(item);
        });
    }

    @RequestMapping("/getProductById2/{productId}")
    public Product cacheMethod2(@PathVariable("productId") Long productId) {
        if (filter.contains(String.valueOf(productId))) {
            String productRedisKey = "productKey:" + productId;
            RBucket<Product> productRBucket = redisson.getBucket(productRedisKey);
            return productRBucket.get();
        }
        return null;
    }
}
