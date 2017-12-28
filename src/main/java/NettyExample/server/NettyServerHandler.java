package NettyExample.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by White on 2017/12/27.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server channelRead");
        System.out.println(ctx.channel().remoteAddress() + "->Server : "+ msg.toString());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断是否IdleStateEvent事件
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            String type = "";
            if (state == IdleState.READER_IDLE) {
                type = "Read Idle";
                System.out.println(ctx.channel().remoteAddress()+"超时类型：" + type);
                throw new Exception(type);
            }
        } else {
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[ "+ ctx.channel().remoteAddress() + " ]在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[ "+ ctx.channel().remoteAddress() + " ]离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当有异常发生时，关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
