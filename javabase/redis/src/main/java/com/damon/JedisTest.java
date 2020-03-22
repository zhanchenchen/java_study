package com.damon;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JedisTest {
    private static final Jedis jedis = new Jedis("localhost", 6379);
    // 线程池
    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);
    static{
        jedis.set("stock", "10");    // 设置初始库存
    }
    public static void main(String[] args) {
        saleGoods();
    }

    // 测试库存
    public static void saleGoods() {
        for (int x = 0; x < 20; x++) {
            new Thread(() -> {
                Jedis jedis = new Jedis("localhost", 6379);
                Long stock = jedis.decr("stock");
                if (stock >= 0) { // 库存充足，消费成功
                    System.err.println("消费成功，剩余库存：" + stock);
                } else {    // 库存不足，归零
                    Long remainder = jedis.incr("stock");
                    System.err.println("购买失败，剩余库存：" + remainder);
                }
            }).start();
        }
    }

    // 测试incr原子操作
    public static void executeThreads() {
        for (int x = 0; x < 10; x++) {
            new Thread(() -> {
                Jedis jedis = new Jedis("localhost", 6379);
                Long goods = jedis.incr("goods");
                System.err.println(goods);
                jedis.close();
            }).start();
        }
    }
}
