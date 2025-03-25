package com.tensua.blogservice.research.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhooke
 * @since 2025/3/18 09:42
 **/
@Slf4j
public class ThreadDay01 {

    public static void threadDaemonBasic() {
        Thread thread = new Thread(() -> System.out.println("hello world one " + Thread.currentThread()));
        //设置为守护线程
        thread.setDaemon(Boolean.TRUE);
        thread.start();
        System.out.println("hello world tow " + Thread.currentThread());
    }

    public static void threadExecutorsBasic() {
        CustomThreadFactory customThreadFactory = new CustomThreadFactory("MyThread");

        try (ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10, customThreadFactory);
             ExecutorService cachedThreadPool = Executors.newCachedThreadPool(customThreadFactory);
             ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(customThreadFactory);
             ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10, customThreadFactory);
        ) {// 使用 executorService 执行任务
            fixedThreadPool.submit(() -> System.out.println("task is running on " + Thread.currentThread().getName()));
        } catch (Exception e) {
            log.error("线程出错", e);
        }
    }

    public static void queueBasic() {
        //由数组组成的有界阻塞队列
        ArrayBlockingQueue<T> arrayBlockingQueue = new ArrayBlockingQueue<T>(10);
        //由链表组成的有界阻塞队列
        LinkedBlockingQueue<T> linkedBlockingQueue = new LinkedBlockingQueue<>();
        //支持优先级排序的无界阻塞队列
        PriorityBlockingQueue<T> priorityBlockingQueue = new PriorityBlockingQueue<>();
        //使用优先级队列实现的无界阻塞队列
        DelayQueue delayQueue = new DelayQueue();
        //不存储元素的阻塞队列 每个插入操作必须等待另一个线程的移除操作
        SynchronousQueue<T> synchronousQueue = new SynchronousQueue<>();
        //链表结构组成的无界阻塞队列
        LinkedTransferQueue<T> linkedTransferQueue = new LinkedTransferQueue<>();
        //链表结构组成的双向阻塞队列
        LinkedBlockingDeque<T> linkedBlockingDeque = new LinkedBlockingDeque<>();
    }

    public static void callableBasic() {
        CustomCallable task = new CustomCallable();
        try (ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();) {
            Future<Integer> future = singleThreadExecutor.submit(task);
            Integer integer = future.get();
            System.out.println("任务线程返回结果： " + integer);
        } catch (Exception e) {
            log.error("线程出错", e);
        }
    }

    public static void main(String[] args) {
        //threadDaemonBasic();
        callableBasic();
    }

    static class CustomCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000);
            return 100;
        }
    }

    public static class CustomThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public CustomThreadFactory(String name) {
            group = Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-" + name + "-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
