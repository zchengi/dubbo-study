package com.stylefeng.guns.core.util;

/**
 * 令牌桶: 对业务有一定容忍度
 * <p>
 * TODO: 加锁实现
 *
 * @author cheng
 *         2019/1/16 21:50
 */
public class TokenBucket {

    /**
     * 容量
     */
    private int bucketNums = 100;

    /**
     * 流速
     */
    private int rate = 1;

    /**
     * 令牌量
     */
    private int nowTokens;

    /**
     * 时间
     */
    private long timestamp = getNowTime();

    public boolean getToken() {

        // 记录获取令牌时间
        long nowTime = getNowTime();

        // 添加令牌（判断该有多少个令牌）
        nowTokens = nowTokens + (int) ((nowTime - timestamp) * rate);

        // 添加以后的令牌数量 与 桶的容量哪个小
        nowTokens = min(nowTokens);
        System.out.println("当前令牌数量: " + nowTokens);

        // 修改令牌时间
        timestamp = nowTime;

        // 判断令牌是否足够
        if (nowTokens >= 1) {
            nowTokens--;
            return true;
        } else {
            return false;
        }
    }

    private int min(int tokens) {

        if (bucketNums > tokens) {
            return tokens;
        } else {
            return bucketNums;
        }
    }

    private long getNowTime() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {

        TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 200; i++) {
            if (i == 10) {
                Thread.sleep(500);
            }

            System.out.println("第 " + i + " 次请求结果: " + tokenBucket.getToken());
        }
    }
}
