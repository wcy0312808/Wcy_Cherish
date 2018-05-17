package wcy.godinsec.wcy_dandan.proxy.proxy_factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2017/8/24 16:54
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 这是用一种JDK形式实现的动态代理工厂类，就是利用Proxy.newProxyInstance来实现
 * 缺点就是它proxy不需要实现接口，但是在对象目标里必须实现接口才能使用
 */
public class ProxyFactoryJDK {
    //维护一个目标对象
    private Object target;

    //给目标对象生成代理对象
    public ProxyFactoryJDK(Object target) {
        this.target = target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        LogUtils.e("method name:" + method.getName());
                        Object returnValue = method.invoke(target, args);
                        return returnValue;
                    }
                }
        );
    }


}
