import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * author yg
 * description
 * date 2020/1/28
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8).substring(0, req.length - System.getProperty(Constant.SEP).length());
        System.out.println("receive:" + body + ",counter:" + ++counter);
        String time = Constant.REQ.equalsIgnoreCase(body) ? new Date().toString() : Constant.BAD_REQ;
        time = time + System.getProperty(Constant.SEP);
        ByteBuf buffer = Unpooled.copiedBuffer(time.getBytes());
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
