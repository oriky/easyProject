package com.zhousj.common.ext.thread;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程创建工厂
 *
 * @author zhousj
 * @date 2021-1-6
 */
@SuppressWarnings("unused")
public class ThreadFactory implements java.util.concurrent.ThreadFactory {

    private final AtomicInteger count = new AtomicInteger(0);

    private String prefix;

    private ThreadGroup group;

    private boolean daemon;

    private int priority;

    private ThreadFactory() {

    }


    private ThreadFactory(TreadPoolFactoryBuilder builder) {
        this.group = getGroup();
        this.prefix = builder.getPrefix();
        this.daemon = builder.isDaemon();
        this.priority = builder.getPriority();
    }


    public static TreadPoolFactoryBuilder builder() {
        return new TreadPoolFactoryBuilder();
    }

    /**
     * 获取默认线程组
     */
    private ThreadGroup getGroup() {
        SecurityManager manager = System.getSecurityManager();
        Optional<SecurityManager> optional = Optional.ofNullable(manager);
        return optional.map(SecurityManager::getThreadGroup).orElseGet(Thread.currentThread()::getThreadGroup);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(this.group, r
                , "pool-" + Optional.ofNullable(prefix).orElse("common-") + "thread-" + count.incrementAndGet());
        thread.setDaemon(this.daemon);
        thread.setPriority(Math.max(this.priority, Thread.MIN_PRIORITY));
        return thread;
    }


    /**
     * 线程工厂内部builder类
     */
    public static class TreadPoolFactoryBuilder {

        private String prefix;

        private boolean daemon;

        /**
         * 线程优先级，默认5
         **/
        private int priority = Thread.NORM_PRIORITY;

        private TreadPoolFactoryBuilder() {

        }


        public TreadPoolFactoryBuilder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public TreadPoolFactoryBuilder daemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public TreadPoolFactoryBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public ThreadFactory build() {
            return new ThreadFactory(this);
        }

        public String getPrefix() {
            return prefix;
        }

        public boolean isDaemon() {
            return daemon;
        }

        public int getPriority() {
            return priority;
        }
    }

}
