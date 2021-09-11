package com.company.research_spring.proxy;

import com.company.research_spring.anno.RoutingSwitch;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: liwenbo
 * @Date: 2021年09月09日
 * @Description:
 */
public class RoutingBeanProxyFactory {

    public static Object createProxy(Class targetClass, Map<String, Object> beans) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(targetClass);
        proxyFactory.addAdvice(new VersionRoutingMethodInterceptor(targetClass, beans));
        return proxyFactory.getProxy();
    }

    static class VersionRoutingMethodInterceptor implements MethodInterceptor {

        private String classSwitch;

        private Object beanOfSwitchOn;

        private Object beanOfSwitchOff;

        public VersionRoutingMethodInterceptor(Class targetClass, Map<String, Object> beans) {
            String interfaceName = StringUtils.uncapitalize(targetClass.getSimpleName());
            if (targetClass.isAnnotationPresent(RoutingSwitch.class)) {
                //获取注解上的字符串
                this.classSwitch = ((RoutingSwitch) targetClass.getAnnotation(RoutingSwitch.class)).value();
            }
            this.beanOfSwitchOn = beans.get(this.buildBeanName(interfaceName, true));
            this.beanOfSwitchOff = beans.get(this.buildBeanName(interfaceName, false));

        }

        /**
         * 生成bean的名字
         *
         * @param interfaceName
         * @param isSwitchOn
         * @return
         */
        private String buildBeanName(String interfaceName, boolean isSwitchOn) {
            return interfaceName + (isSwitchOn ? "ImplV2" : "ImplV1");
        }


        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Method method = invocation.getMethod();
            String switchName = this.classSwitch;
            if (method.isAnnotationPresent(RoutingSwitch.class)) {
                switchName = method.getAnnotation(RoutingSwitch.class).value();
            }
            //判断switchName是否为空
            if (switchName.equals("")) {
                throw new IllegalStateException("value is blank" + method.getName());
            }
            return invocation.getMethod().invoke(getTargetBean(switchName));

        }

        public Object getTargetBean(String switchName) {
            boolean switchOn;
            if ("V1".equals(switchName)) {
                switchOn = false;
            } else if ("V2".equals(switchName)) {
                switchOn = true;
            } else {
                switchOn = false;
            }
            return switchOn ? beanOfSwitchOn : beanOfSwitchOff;
        }
    }
}
