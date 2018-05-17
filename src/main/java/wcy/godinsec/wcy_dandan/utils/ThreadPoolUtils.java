package wcy.godinsec.wcy_dandan.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @time 2017/8/10 14:25
 * @des
 */

public class ThreadPoolUtils {
    private static ExecutorService exector = Executors.newCachedThreadPool();
    public static ExecutorService getService(){
        if(exector.isShutdown()){
            exector = Executors.newCachedThreadPool();
        }
        return exector;
    }

    /**
     * 设置固定数量的线程池
     */
    private static ExecutorService exector2 = Executors.newFixedThreadPool(4);
    public static ExecutorService getFixedThreadPool(){
        if(exector2.isShutdown()){
            exector2 = Executors.newFixedThreadPool(4);
        }
        return exector2;
    }
}
