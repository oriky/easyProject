package com.zhousj.common.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 雪花算法生成id
 *
 * @author zhousj
 * @date 2021/1/6
 */
@SuppressWarnings("unused")
public class SnowFlake {

    private static final Logger logger = LoggerFactory.getLogger(SnowFlake.class);

    /**
     * 机器id，5bit，最大不超过31
     */
    private final long workerId;

    /**
     * 机房id，5bit，最大不超过31
     */
    private final long dataCenterId;

    /**
     * 1ms内最大自增序列
     */
    private static final long SEQUENCE = (1 << 12) - 1;

    /**
     * 上次生成id记录的自增序列
     */
    private long lastSequence;

    /**
     * 上次生成id时间
     */
    private long lastTimeStamp;

    /**
     * 默认起始时间
     */
    private static final long START_TIME_STAMP = 1585644268888L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12L;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5L;

    /**
     * 数据中心占用的位数
     */
    private final static long DATA_CENTER_BIT = 5L;


    public SnowFlake() {
        long workerId = 0L;
        long dataCenterId = 0L;
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String ip = InetAddress.getLocalHost().getHostAddress();
            logger.info("hostName: {}, hostAddress: {}", hostName, ip);
            workerId = hostName.hashCode() & 31;
            dataCenterId = ip.hashCode() & 31;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
        logger.info("雪花算法创建，机器workerId：{}，机房dataCenterId：{}", workerId, dataCenterId);
    }

    public SnowFlake(boolean random) {
        long workerId = 0L;
        long dataCenterId = 0L;
        if (random) {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            workerId = threadLocalRandom.nextLong(1 << MACHINE_BIT);
            dataCenterId = threadLocalRandom.nextLong(1 << DATA_CENTER_BIT);
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        logger.info("雪花算法创建，机器workerId：{}，机房dataCenterId：{}", workerId, dataCenterId);
    }

    public SnowFlake(long workerId, long dataCenterId) {
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        logger.info("雪花算法创建，机器workerId：{}，机房dataCenterId：{}", workerId, dataCenterId);
    }

    /**
     * 获取下一个id
     *
     * @return long型十进制id
     */
    public synchronized long nextId() {
        long currentTimeStamp = System.currentTimeMillis();
        if (currentTimeStamp < lastTimeStamp) {
            throw new IllegalArgumentException("服务器时间可能被回调.");
        } else if (currentTimeStamp == lastTimeStamp) {
            lastSequence = (lastSequence + 1) & SEQUENCE;
            if (lastSequence == 0L) {
                currentTimeStamp = nextMillis(currentTimeStamp);
            }
        } else {
            lastSequence = 0L;
        }
        lastTimeStamp = currentTimeStamp;
        return ((currentTimeStamp - START_TIME_STAMP) << (SEQUENCE_BIT + DATA_CENTER_BIT + MACHINE_BIT)) |
                (dataCenterId << (SEQUENCE_BIT + DATA_CENTER_BIT)) |
                (workerId << SEQUENCE_BIT) | lastSequence;
    }

    /**
     * 获取下一毫秒
     *
     * @param lastTimestamp 当前时间
     * @return 下一1ms时间
     * @author zhousj
     */
    private long nextMillis(long lastTimestamp) {
        long timestamp;
        while ((timestamp = System.currentTimeMillis()) <= lastTimestamp) {
            // noting to do, waiting for next millionSecond.
        }
        return timestamp;
    }
}
