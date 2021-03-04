package com.zsw.server.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author ZhangShaowei on 2021/3/4 17:04
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String LF = " \r\n";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("A client has connected to server: {}", ctx.channel().remoteAddress());
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + ". " + LF);
        ctx.write("It is " + new Date() + " now. " + LF);
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String response = "";
        boolean close = false;
        if (StringUtil.isNullOrEmpty(msg)) {
            response = "Please type something.";
        } else if ("bye".equalsIgnoreCase(msg)) {
            response = "Have a good day.";
            close = true;
        } else {
            response = "Server has received your message: \"" + msg + "\".";
        }
        response += LF;
        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        if (close) {
            log.warn("A client disconnected: {}", channelFuture.channel().remoteAddress());
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
