package com.tensua.blogservice.research.basic;

/**
 * @author zhooke
 * @since 2025/3/25 13:49
 **/
public class JdkBasicDay01 {

    public static void calculationBasic() {
        /**
         * byte 是一种基本数据类型，它的取值范围是 -128 到 127。
         * 这是因为 byte 类型使用了 8 位（1 字节）来存储数据，
         * 并且它是带符号的类型，最高位用于表示符号（0 表示正数，1 表示负数），
         * 剩下的 7 位用于表示数值。
         */
        byte a = 127;
        byte b = 127;
        //byte c = 128;
        //a = a + b;
        a += b;
        System.out.println(a);
    }

    public static void main(String[] args) {
        calculationBasic();//-2
    }
}
