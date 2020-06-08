package cn.dreamdeck.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//物联网 开启检测端口 并写入数据库
@Component
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
		    //此处注入数据源操作sql   执行插入设备上传的数据
		    //  将当前客户端连接 存入map   实现控制设备下发 参数
		    public  static Map<String, ChannelHandlerContext> map = new HashMap<String, ChannelHandlerContext>();

			public  static Map<String, String> resuleMap = new HashMap<String, String>();

			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				String url=ctx.channel().remoteAddress().toString();
				map.put(url,ctx);
				System.out.println("有新客户端连接接入。。。"+ctx.channel().remoteAddress()+" "+ctx.channel().id() );
			}

			public void channelInactive(ChannelHandlerContext ctx) throws Exception {
				String url=ctx.channel().remoteAddress().toString();
				map.remove(url);
				//System.out.println("失去连接"+ctx.channel().remoteAddress());
			}
		   
		    /**
		     * 获取数据
		     * @param ctx 上下文
		     * @param msg 获取的数据
		     * @throws UnsupportedEncodingException
		     */
		    @Override
		    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
				String url=ctx.channel().remoteAddress().toString();//设备请求地址（个人将设备的请求地址当作 map 的key）
				//System.out.println("url地址"+url);
		    	//msg为接收到的客户端传递的数据   个人这边直接传的json 数据
		    	ByteBuf readMessage= (ByteBuf) msg;
		    	//解析客户端json 数据
		        //JSONObject json=JSONObject.fromObject(readMessage.toString(CharsetUtil.UTF_8));
				String prefixValue = ByteBufUtil.hexDump(readMessage);
		       // System.out.println("接收到的数据"+prefixValue);
				resuleMap.put(url,prefixValue);
		        //获取客户端的请求地址  取到的值为客户端的 ip+端口号


		        int users=0; 
		        //设备请求的 服务器端的地址   用作监听设备请求的那个端口  
		        String servicePort=ctx.channel().localAddress().toString();
		        //判断端口如果客户端请求的端口号为9898   就是写入第一张表   这样可以实现 设备传递数据参数不一致 
		        // System.out.println("向："+servicePort.substring(servicePort.length()-4, servicePort.length())+" 端口写入数据");
		        if(servicePort.substring(servicePort.length()-4, servicePort.length()).equals("9898")){
//				        	Product_data product_data=new Product_data(); 
//			        		//设备请求地址  存入数据库  下方controller层 通过设备id查询此地址   取到map种存入的 ChannelHandlerContext 实现数据下发
//					        product_data.setUrl(url);
//					        product_data.setJson(readMessage.toString(CharsetUtil.UTF_8));//设备请求时原生数据  
//					        product_data.setDeviceID(json.get("deviceID").toString());//设备数据1
//					        product_data.setPower1(json.get("power1").toString());//设备数据2
//					        product_data.setPower2(json.get("power2").toString());//设备数据3
//					        product_data.setPower3(json.get("power3").toString());//设备数据4
//					        product_data.setAcquisitionTime(TimeUtile.showDate());//时间 (个人整的当前时间工具类  替换成自己获取当前时间的方法即可）
					        //执行写入操作    此处写你们要插入的表操作语句即可
					        //users = product_dataServiceImpl.add_Device_shuju(product_data);
		         }else{
		        	 		//否则取另外的值 进行写入 数据库
//		        	 		Product_data product_data=new Product_data(); 
//		        	 		//设备请求地址  存入数据库  下方controller层 通过设备id查询此地址   取到map种存入的 ChannelHandlerContext 实现数据下发
//					        product_data.setUrl(url);
//					        product_data.setJson(readMessage.toString(CharsetUtil.UTF_8));//设备请求时原生数据  
//					        product_data.setDeviceID(json.get("deviceID").toString());//设备数据1
//					        product_data.setData1(json.get("data1").toString());//设备数据2
//					        product_data.setData2(json.get("data2").toString());//设备数据3
//					        product_data.setData3(json.get("data3").toString());//设备数据4
//					        product_data.setAcquisitionTime(TimeUtile.showDate());//时间 (个人整的当前时间工具类  替换成自己获取当前时间的方法即可）
					        //执行写入操作    此处写你们要插入的表操作语句即可
					        //users = product_dataServiceImpl.add_Device_data(product_data);
		         }
		        String rmsg;
		        if(users>0){
		        		rmsg="11 02 00 C4 00 16 ";//返回成功的信息
		         }else{
		        	 	rmsg="0";//返回失败的信息
		         }
		        ByteBuf message= Unpooled.copiedBuffer(rmsg.getBytes());//处理返回的信息
		      	 //ctx.write(in2);//返回信息 
		        ctx.writeAndFlush(message);//返回信息 
			   	 //刷新缓存区
			   	ctx.flush();
		    }
		    
		    @Override
		    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		   		 cause.printStackTrace();
		   		 ctx.close();
		    }
		public static String convertByteBufToString(ByteBuf buf) {
			String str;

			if(buf.hasArray()) { // 处理堆缓冲区
				str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
			} else { // 处理直接缓冲区以及复合缓冲区
				byte[] bytes = new byte[buf.readableBytes()];
				buf.getBytes(buf.readerIndex(), bytes);
				str = new String(bytes, 0, buf.readableBytes());
			}
			return str;
		}
}
