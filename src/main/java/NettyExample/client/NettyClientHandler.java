package NettyExample.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * Created by White on 2017/12/27.
 */
@ChannelHandler.Sharable
class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       /* String message = (String) msg;
        System.out.println(message);
        if (message.equals("HeartBeat")) {
            ctx.write("has read message from server");
            ctx.flush();
        }
        ReferenceCountUtil.release(msg);*/
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // 将心跳内容发送给服务器
                ctx.writeAndFlush(Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("写超时", CharsetUtil.UTF_8)).duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端[ "+ctx.channel().localAddress()+" ]已连接，时间:" + new Date());
        // 转发消息到下一个ChannelHandler的channelActive处理
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端[ "+ctx.channel().localAddress()+" ]已关闭，时间:" + new Date());
    }
}
