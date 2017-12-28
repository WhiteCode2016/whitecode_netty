package NettyExample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by White on 2017/12/28.
 */
public class NettyBusinessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("business");
        ctx.writeAndFlush("business");
    }
}
