package Heart;

import io.netty.channel.ChannelHandler;

/**
 * Created by White on 2017/12/26.
 */
public interface ChannelHandlerHolder {
    ChannelHandler[] handlers();
}
