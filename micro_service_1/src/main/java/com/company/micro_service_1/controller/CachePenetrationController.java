package com.company.micro_service_1.controller;

import com.company.micro_service_1.bean.Product;
import com.company.micro_service_1.service.ProductService;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cache/")
public class CachePenetrationController {

    @Resource
    private ProductService productService;

    @Resource
    private Redisson redisson;


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
}
