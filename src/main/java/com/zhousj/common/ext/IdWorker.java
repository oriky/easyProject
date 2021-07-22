package com.zhousj.common.ext;

/**
 * 单例模式雪花算法生成id
 *
 * @author zhousj
 * @date 2021/1/6
 */
@SuppressWarnings("unused")
public class IdWorker {

    public static String generate() {
        return String.valueOf(getSnowFlake().nextId());
    }

    public static long generateLong() {
        return getSnowFlake().nextId();
    }

    private static SnowFlake getSnowFlake() {
        return SnowFlakeHolder.SNOW_FLAKE;
    }

    /**
     * 内部类方式创建单例
     */
    private static class SnowFlakeHolder {
        private static final SnowFlake SNOW_FLAKE = new SnowFlake();
    }
}
