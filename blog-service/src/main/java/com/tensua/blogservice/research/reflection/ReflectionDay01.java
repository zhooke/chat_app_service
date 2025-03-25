package com.tensua.blogservice.research.reflection;

import com.tensua.blogservice.operator.login.entity.UserInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhooke
 * @since 2025/3/19 09:45
 **/
public class ReflectionDay01 {

    public static void reflectionBasic() {
        //第一种方式 Class.forName(...)
        try {
            Class<?> userinfo = Class.forName("com.tensua.blogservice.operator.login.entity.UserInfo");
            //获取所有声明的方法
            /**
             * getDeclaredMethods()返回的成员不包括继承的成员
             * getMethods()返回的包括继承的成员
             */

            Method[] methods = userinfo.getDeclaredMethods();
            Method[] userinfoMethods = userinfo.getMethods();
            for (Method method : methods) {
                System.out.println("方法：" + method.getName());
            }
            //获取所有声明的构造函数
            Constructor<?>[] constructors = userinfo.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                System.out.println("构造方法：" + constructor.getName());
            }
            //获取所有声明的字段
            Field[] fields = userinfo.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("字段：" + field.getName());
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //第二种方式 类名.class
        Class<UserInfo> userInfoClass = UserInfo.class;
        Method[] methods = userInfoClass.getDeclaredMethods();
        Constructor<?>[] constructors = userInfoClass.getDeclaredConstructors();
        Field[] declaredFields = userInfoClass.getDeclaredFields();

        //第三种方式 对象.getClass()
        UserInfo userInfo = new UserInfo();
        Class<? extends UserInfo> getClassUserinfo = userInfo.getClass();
        Method[] getClassMethod = getClassUserinfo.getDeclaredMethods();
        Constructor<?>[] declaredConstructors = getClassUserinfo.getDeclaredConstructors();
        Field[] declaredFields1 = getClassUserinfo.getDeclaredFields();

        //基本类型的包装类可以通过其type属性获取对应的反射
        Class<Integer> type = Integer.TYPE;
        Method[] integerMethod = type.getMethods();
        Constructor<?>[] integerDeclaredConstructors = type.getDeclaredConstructors();
        Field[] integerDeclaredFields = type.getDeclaredFields();

    }

    public static void main(String[] args) {
        reflectionBasic();
    }
}
