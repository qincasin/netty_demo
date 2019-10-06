package com.qjx.dp.decorator;

/**
 * 装饰模式client
 *
 * BufferedInputSteam  extends FilterInputStream (BufferedInputSteam 相当于ConcreteDecorator2 或者 ConcreteDecorator1 )
 *
 * FilerInputStream extends InputStream (FilerInputStream 相当于 Decorator类)
 *
 *  InputStream 相当于 Component
 */
public class Client {
    public static void main(String[] args) {

        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));


        component.doSomething();

    }
}
