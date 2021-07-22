package com.zhousj.common.ext.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;


/**
 * @author zhousj
 */
@SuppressWarnings("unused")
public class ThreadPool extends ThreadPoolExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    /**
     * 任务调度前操作，可以按需实现接口
     */
    private BiConsumer<Thread, Runnable> beforeConsumer;

    /**
     * 任务调度后操作，可以按需实现接口
     */
    private BiConsumer<Runnable, Throwable> afterConsumer;

    /**
     * 默认拒接策略，抛出异常
     */
    private static final RejectedExecutionHandler DEFAULT_HANDLER = new AbortPolicy();


    /**
     * 服务器cpu处理器数
     */
    private static final int PROCESSORS_NUM = Runtime.getRuntime().availableProcessors();


    /**
     * 默认创建工厂，线程优先级5，非守线程
     */
    private static final ThreadFactory DEFAULT_FACTORY = ThreadFactory.builder()
            .priority(Thread.NORM_PRIORITY).daemon(false).prefix("common-").build();


    /**
     * 设置任务调度前操作
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (beforeConsumer != null) {
            beforeConsumer.accept(t, r);
        }
    }

    /**
     * 设置任务调度后操作
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (afterConsumer != null) {
            afterConsumer.accept(r, t);
        }
    }


    /**
     * 构建线程池,核心线程默认为服务器的cpu处理器数量，最大线程为核心线程数两倍
     *
     * @return 线程池对象ThreadPool
     * @author zhousj
     * @see ThreadPool#newThreadPool(int, int)
     */
    public static ThreadPool newThreadPool() {
        return newThreadPool(PROCESSORS_NUM, PROCESSORS_NUM << 1);
    }


    /**
     * 构建线程池
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @return 线程池对象ThreadPool
     * @author zhousj
     * @see ThreadPool#newThreadPool(int, int, ThreadFactory)
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize) {
        return newThreadPool(coreSize, maxSize, DEFAULT_FACTORY);
    }


    /**
     * 构建线程池
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param queue    等待阻塞队列
     * @return 线程池对象ThreadPool
     * @author zhousj
     * @see ThreadPool#newThreadPool(int, int, BlockingQueue, ThreadFactory)
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, BlockingQueue<Runnable> queue) {
        return newThreadPool(coreSize, maxSize, queue, DEFAULT_FACTORY);
    }


    /**
     * 构建线程池,阻塞队列容量默认100w
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param factory  线程创建工厂
     * @return 线程池对象ThreadPool
     * @author zhousj
     * @see ThreadPool#newThreadPool(int, int, long, TimeUnit, BlockingQueue, ThreadFactory)
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, ThreadFactory factory) {
        return newThreadPool(coreSize, maxSize, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000000), factory);
    }


    /**
     * 构建线程池
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param queue    等待阻塞队列
     * @param factory  线程创建工厂
     * @return 线程池对象ThreadPool
     * @author zhousj
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, BlockingQueue<Runnable> queue, ThreadFactory factory) {
        return newThreadPool(coreSize, maxSize, 0L, TimeUnit.MILLISECONDS, queue, factory);
    }


    /**
     * 构建线程池，阻塞队列容量默认100w
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param keepTime 等待时间
     * @param timeUnit 时间单位
     * @return 线程池对象ThreadPool
     * @author zhousj
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, long keepTime, TimeUnit timeUnit) {
        return newThreadPool(coreSize, maxSize, keepTime, timeUnit, new ArrayBlockingQueue<>(1000000), DEFAULT_FACTORY);
    }


    /**
     * 构建线程池
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param keepTime 等待时间
     * @param timeUnit 时间单位
     * @param queue    阻塞等待队列
     * @return 线程池对象ThreadPool
     * @author zhousj
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, long keepTime, TimeUnit timeUnit, BlockingQueue<Runnable> queue) {
        return newThreadPool(coreSize, maxSize, keepTime, timeUnit, queue, DEFAULT_FACTORY, DEFAULT_HANDLER);
    }


    /**
     * 构建线程池
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param keepTime 等待时间
     * @param timeUnit 时间单位
     * @param queue    阻塞等待队列
     * @param factory  线程创建工厂
     * @return 线程池对象ThreadPool
     * @author zhousj
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, long keepTime, TimeUnit timeUnit, BlockingQueue<Runnable> queue, ThreadFactory factory) {
        return newThreadPool(coreSize, maxSize, keepTime, timeUnit, queue, factory, DEFAULT_HANDLER);
    }


    /**
     * 构建线程池
     *
     * @param coreSize 核心线程数
     * @param maxSize  最大线程数
     * @param keepTime 等待时间
     * @param timeUnit 时间单位
     * @param queue    阻塞等待队列
     * @param factory  线程创建工厂
     * @param handler  拒接策略
     * @return 线程池对象ThreadPool
     * @author zhousj
     */
    public static ThreadPool newThreadPool(int coreSize, int maxSize, long keepTime, TimeUnit timeUnit, BlockingQueue<Runnable> queue
            , ThreadFactory factory, RejectedExecutionHandler handler) {
        logger.info("custom thread-pool init, coreSize:{}, maxSize:{}, keepTime:{}L, timeUnit:{}", coreSize, maxSize, keepTime, timeUnit);
        return new ThreadPool(coreSize, maxSize, keepTime, timeUnit, queue, factory, handler);
    }



    public void setBeforeConsumer(BiConsumer<Thread, Runnable> beforeConsumer) {
        this.beforeConsumer = beforeConsumer;
    }


    public void setAfterConsumer(BiConsumer<Runnable, Throwable> afterConsumer) {
        this.afterConsumer = afterConsumer;
    }


    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = newThreadPool(4, 8);
        CountDownLatch latch = new CountDownLatch(54);
        for (int i = 0; i < 100; i++) {
            pool.execute(latch::countDown);
        }
        latch.await(1000, TimeUnit.MILLISECONDS);
        List<Runnable> runnableList = pool.shutdownNow();
        if (runnableList.size() > 0) {
            System.out.println("undo task: " + runnableList.size());
        }
        System.out.println("main thread exit");
    }
}
