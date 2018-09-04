package zhanj;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MessageHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private ThreadPoolExecutor pool;
    private Worker worker;

    public MessageHandler() {
        this.pool = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(10));
        this.worker = new Worker();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data, 0, data.length);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                worker.handle(data);
            }
        });
    }
}
