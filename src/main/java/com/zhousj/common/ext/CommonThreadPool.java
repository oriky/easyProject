package com.zhousj.common.ext;

import com.zhousj.common.ext.thread.ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 全局单例线程池
 *
 * @author zhousj
 * @date 2021/1/22
 */
@SuppressWarnings("unused")
public class CommonThreadPool {

    private CommonThreadPool() {
    }

    private static final int PROCESS_NUM = Runtime.getRuntime().availableProcessors();

    private static final ThreadPool THREAD_POOL = ThreadPool.newThreadPool();

    public static CommonThreadPool getThreadPool() {
        return ThreadPoolHolder.COMMON_THREAD_POOL;
    }

    public void execute(Runnable r) {
        THREAD_POOL.execute(r);
    }

    public Future<?> submit(Runnable r) {
        return THREAD_POOL.submit(r);
    }

    public <T> Future<T> submit(Callable<T> call) {
        return THREAD_POOL.submit(call);
    }

    public <T> Future<T> submit(Runnable r, T t) {
        return THREAD_POOL.submit(r, t);
    }

    private static class ThreadPoolHolder {
        private static final CommonThreadPool COMMON_THREAD_POOL = new CommonThreadPool();
    }
}
