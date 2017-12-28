package NettyExample.client;

import NettyExample.ConnectionWatchdog;
import NettyExample.common.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by White on 2017/12/27.
 */
public class NettyClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

        final ConnectionWatchdog watchdog = new ConnectionWatchdog(bootstrap, new HashedWheelTimer(),
                NettyConstant.PORT,NettyConstant.HOST, true) {

            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        this,
                        new NettyClientInitializer()
                };
            }
        };
        ChannelFuture future;
        try {
            synchronized (bootstrap) {
                bootstrap.handler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(watchdog.handlers());
                    }
                });
                future = bootstrap.connect(NettyConstant.HOST,NettyConstant.PORT);
            }
            future.sync();
            // 控制台输入
          /*  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                future.channel().writeAndFlush(in.readLine() + "\r\n");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

