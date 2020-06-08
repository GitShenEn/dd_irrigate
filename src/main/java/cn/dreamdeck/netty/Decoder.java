package cn.dreamdeck.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        Object o = decode(ctx, in);
        if(o != null) {
            out.add(o);
        }
    }                    //此代码中Message类中的数据皆为常量

    private Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        //可读长度readableBytes必须大于基本长度才处理
        if(in.readableBytes() < Message.BYTE_BASE){
            //长度短了，数据包不完整，需要等待后面的包来
            //FrameDecoder： return null就是等待后面的包，
            //
            //
            // return一个解码的对象就是向下传递。
            return null;
        }

        //防止socket字节流攻击
        if(in.readableBytes() > Message.BYTE_SOCKET){
            in.skipBytes(in.readableBytes());
        }

        //获取帧头
        String head = ByteBufUtil.hexDump(in.readBytes(Message.BYTE_FRAME_HEAD));
        if(!Message.HEAD_FLAG.equalsIgnoreCase(head)) {
            //找不到帧头,丢弃多余数据
            return null;
        }

        return in;
    }
}
