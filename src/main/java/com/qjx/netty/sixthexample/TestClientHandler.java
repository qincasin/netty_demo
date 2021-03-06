package com.qjx.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    //连接刚激活
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (0 == random) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(
                            MyDataInfo.Person.newBuilder()
                                    .setName("张三")
                                    .setAge(20)
                                    .setAddress("南京")
                                    .build()
                    ).build();

        } else if (1 == random) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(
                            MyDataInfo.Dog.newBuilder()
                                    .setName("一只狗")
                                    .setAge(1)
                                    .build()
                    ).build();
        } else {

            myMessage = MyDataInfo.MyMessage.newBuilder().setDateType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(
                            MyDataInfo.Cat.newBuilder()
                                    .setName("一只猫")
                                    .setCity("南京")
                                    .build()
                    ).build();

        }

        ctx.channel().writeAndFlush(myMessage);
    }
}
