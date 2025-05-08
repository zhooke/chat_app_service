package com.tensua.blogservice.research.designMode;

/**
 * @author zhooke
 * @since 2025/4/1 10:04
 **/
public class DesignMode {

    /**
     * 简单工厂模式
     */
    public static abstract class Fruit {
        public abstract void eat();
    }

    public static class Apple extends Fruit {

        @Override
        public void eat() {
            System.out.println("apple");
        }
    }

    public static class FruitFactory {
        public Fruit productFruit(String productName) {
            if (productName.equalsIgnoreCase("apple")) {
                return new Apple();
            } else {
                return null;
            }

        }
    }

    public static void main(String[] var) {
        FruitFactory fruitFactory = new FruitFactory();
        Fruit apple = fruitFactory.productFruit("apple");
        apple.eat();
    }

}
