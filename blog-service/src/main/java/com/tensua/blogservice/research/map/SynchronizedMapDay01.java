package com.tensua.blogservice.research.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhooke
 * @since 2025/4/16 10:05
 **/
public class SynchronizedMapDay01 {

    private static final Map<String, String> aliasMap = new ConcurrentHashMap<>(16);

    /**
     * aliasMap 是一个共享的可变状态（ConcurrentHashMap），可能会被多个线程同时访问或修改。
     * 移除操作不仅涉及 remove 方法调用，还包含后续的空值检查逻辑，这两步操作必须保证原子性，避免并发问题。
     * 如果不在这里加锁，可能会出现以下问题：
     * 线程 A 调用 remove 后，线程 B 同时修改了 aliasMap，导致线程 A 的空值检查结果不一致。
     */
    private static void synchronizedMap(String alias1, String alias2) {

        Thread thread1 = new Thread(() -> {
            synchronized (aliasMap) {
            String name = aliasMap.remove(alias1);
            System.out.println(name);

            if (name == null) {
                System.out.println("name is null");
                throw new IllegalStateException("No alias '" + alias1 + "' registered");
            }
            }
        });

        Thread thread2 = new Thread(() -> aliasMap.put("alias1", alias2));

        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        aliasMap.put("alias1","a");
        aliasMap.put("alias2","b");
        aliasMap.put("alias3","c");
        synchronizedMap("alias1","123");
        System.out.println(aliasMap);
    }
}
