package com.tensua.blogservice.research.thread;

/**
 * @author zhooke
 * @since 2025/3/25 21:19
 **/
public class ThreadOfVirtualDay01 {

    public static void threadOfVirtualBasic() {
        Thread.ofVirtual().start(() -> System.out.println("hello " + Thread.currentThread().getName()));
        System.out.println("当前线程：" + Thread.currentThread());
    }

    public static void main(String[] args) {
        threadOfVirtualBasic();
    }
}
