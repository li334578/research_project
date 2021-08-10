package com.company.research_redission.aspect;

import com.company.research_redission.anno.RedisDistLock;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-08-01 10:21:21
 */
@Aspect
@Order(-1)
@Log4j2
public class RedisDistLockAspect {
    /**
     * 分布式锁 key
     */
    public static final String REDIS_DIST_LOCK_PREFIX = "distlock:%s";

    @Resource
    private RedissonClient redissonClient;

    /**
     * 切入点为加了 @RedissonLock 注解
     */
    @Pointcut("@annotation(com.company.research_redission.anno.RedisDistLock)")
    public void RedissonLockAspect() {

    }

    @Around("RedissonLockAspect()")
    public Object lockAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = null;
        RedisDistLock redisDistLock;
        RLock rLock = null;
        long start = 0L;
        try {
            method = getPointMethod(proceedingJoinPoint);
            // 获取方法上的分布式锁注解
            redisDistLock = method.getAnnotation(RedisDistLock.class);
            long expireTime = redisDistLock.leaseTime();
            String teamSequence = getTeamSequence(proceedingJoinPoint);
            String distLockKey = String.format(REDIS_DIST_LOCK_PREFIX, teamSequence);
            System.out.println("加锁 key  distLockKey = " + distLockKey);
            start = System.currentTimeMillis();
            //获取锁
            rLock = redissonClient.getLock(distLockKey);
            //设置超时
            rLock.lock(expireTime, redisDistLock.timeUnit());
            return proceedingJoinPoint.proceed();
        } finally {
            try {
                if (rLock != null && rLock.isLocked()) {
                    rLock.unlock();
                }
                log.info("锁释放完成");
            } catch (Exception e) {
                log.error("redis 分布式锁释放异常!", e);
            }
            String methodName = method.getName();
            log.info(">> " + methodName + " cost " + (System.currentTimeMillis() - start) + " ms");
        }
    }

    /**
     * 获取切入点的方法
     *
     * @param joinPoint
     * @return
     */
    private Method getPointMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }


    /**
     * 通过方法参数获取车队序列号的值
     *
     * @param joinPoint
     * @return
     */
    private String getTeamSequence(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 参数名
        String[] parameterNames = methodSignature.getParameterNames();
        // 参数值对象
        Object[] parameterValues = joinPoint.getArgs();

        Method method = getPointMethod(joinPoint);
        RedisDistLock rLock = method.getAnnotation(RedisDistLock.class);
        String teamSequence = null;

        String lockName = rLock.lockName();
        String param = rLock.param();
        String separator = rLock.separator();

        if (StringUtils.isEmpty(lockName)) {
            lockName = method.getName();
        }

        if (!StringUtils.isEmpty(parameterValues)) {

            for (int i = 0; i < parameterNames.length; i++) {
                //未设置pram考虑 取第一个参数值
                if (StringUtils.isEmpty(param)) {
                    param = parameterValues[i].toString();
                    teamSequence = lockName + separator + param;
                    break;
                } else if (param.equalsIgnoreCase(parameterNames[i])) {
                    teamSequence = lockName + separator + parameterValues[i].toString();
                    break;
                } else {
                    teamSequence = reflectTeamSequence(parameterValues[i], param);
                    if (StringUtils.isEmpty(teamSequence)) {
                        continue;
                    }
                    teamSequence = lockName + separator + teamSequence;
                    break;
                }
            }
            if (!StringUtils.isEmpty(teamSequence)) {
                return teamSequence;
            }
        }
        throw new RuntimeException("锁ID为空");
    }

    /**
     * 反射获取 注解制定的参数
     *
     * @param param 方法上的参数对象
     * @return
     */
    private String reflectTeamSequence(Object param, String paramName) {
        if (param == null) {
            return "";
        }
        // 1. 获取其从父类继承下来的所有字段( Object.class 排除)
        List<Field> fieldList = new ArrayList<>();
        Class<?> clazz = param.getClass();
        while (clazz != null && !clazz.equals(Object.class)) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        // 2. 返回参数的值
        for (Field field : fieldList) {
            field.setAccessible(true);
            // 字段名称
            String fieldName = field.getName();
            Object fieldValue;
            if (paramName.equalsIgnoreCase(fieldName)) {
                try {
                    fieldValue = field.get(param);
                    if (!StringUtils.isEmpty(fieldValue)) {
                        return fieldValue.toString();
                    }
                } catch (IllegalAccessException e) {
                    // 已经设置了accessible ...
                    log.error("join pointer reflect get paramName error!", e);
                }
                break;
            }
        }
        return "";
    }

}
