package com.company.research_redission.anno;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-08-01 10:20:11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisDistLock {
    /**
     * 锁的名称。 如果lockName可以确定，直接设置该属性。
     */
    String lockName() default "";

    /**
     * <pre>
     *     获取注解的方法参数列表的某个参数对象的某个属性值来作为lockName。因为有时候lockName是不固定的。
     *     当param不为空时，可以通过argNum参数来设置具体是参数列表的第几个参数，不设置则默认取第一个。
     * </pre>
     */
    String param() default "";

    /**
     * 获得锁名时拼接前后缀用到的分隔符
     *
     * @return
     */
    String separator() default ":";

    /**
     * 锁超时时间。 超时时间过后，锁自动释放 默认8秒。 建议： 尽量缩减需要加锁的逻辑。
     */
    long leaseTime() default 8L;

    /**
     * 时间单位。默认为秒。
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
