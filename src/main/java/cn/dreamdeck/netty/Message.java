package cn.dreamdeck.netty;

public class Message {
    public static final Boolean CHECK_SUCCESS = true;//接收成功

    public static final Boolean CHECK_FAILURE = false;//接收失败

    public static final String SUCCESS = "00";//接收成功

    public static final String FAILURE = "01";//接收失败

    public static final String FRAME_HEAD = "FEFE";//数据帧头

    public static final String FRAME_END = "EEEE";//数据帧尾

    public static final String REGISTER_DATA_LENGTH = "0003";//注册应答帧有效数据长度

    public static final String HEART_DATA_LENGTH = "0001";//心跳应答帧有效数据长度

    public static final String STATUS_DATA_LENGTH = "0001";//设备状态应答帧有效数据长度

    public static final String IP_SWITCHING_DATA_LENGTH = "0006";//ip切换数据帧长度



    public static final String ONLINE = "00";//设备离线

    public static final String NOT_ONLINE = "01";//设备在线

    //命令码
    public static final String COMMAND_REGISTER = "10";//注册帧命令码

    public static final String COMMAND_HEART_BEAT = "11";//心跳帧命令码

    public static final String COMMAND_STATUS = "12";//终端状态帧命令码

    public static final String COMMAND_EQUIPMENT_LIST = "80";//网关设备列表帧命令码

    public static final String COMMAND_IP_SWITCHING = "8A";//ip切换帧命令码

    //截取数据常量(所占字节数)
    public static final Integer BYTE_BASE = 1 + 1 + 1 + 1 + 1 +1 + 1 + 2;//基本数据帧

    public static final Integer BYTE_FRAME_HEAD = 1;

    public static final Integer BYTE_VERSION = 1;//地址码

    public static final Integer BYTE_FRAME_NO = 1;//功能码

    public static final Integer BYTE_ADDRESS_HIGH = 1;//起始地址高位

    public static final Integer BYTE_ADDRESS_LOW = 1;//起始地址低位

    public static final Integer BYTE_DATA_HIGH = 1;//数据位高

    public static final Integer BYTE_DATA_LOW = 1;//数据位低

    public static final Integer BYTE_CRC = 2;//crc校验码


    //截取字符串常量
    public static final Integer START_INDEX = 0; //截取初始位置

    public static final Integer LENGTH_STATUS_ONE_EQUIPMENT = 18;//网关发送终端状态帧---一个设备数据

    public static final Integer LENGTH_STRING_HEX = 2;//2个字符对应一个16进制数据

    //安全
    public static final Integer BYTE_SOCKET = 2048;

    //格式
    public static final String HEAD_FLAG = "dd";
}
