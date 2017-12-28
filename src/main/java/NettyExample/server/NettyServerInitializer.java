package NettyExample.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by White on 2017/12/27.
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private static final int READ_IDEL_TIMEOUT = 5;
    private static final int WRITE_IDEL_TIMEOUT = 0;
    private static final int ALL_IDEL_TIMEOUT = 0;

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIMEOUT,WRITE_IDEL_TIMEOUT,ALL_IDEL_TIMEOUT, TimeUnit.SECONDS));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new NettyServerHandler());
    }
}
