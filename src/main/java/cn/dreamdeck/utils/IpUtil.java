package cn.dreamdeck.utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IpUtil {

    public static boolean ping(String ipAddress){
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = false;     // 当返回值是true时，说明host是可用的，false则不可。
        try {
            status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;

//        try {
//            return 0==Runtime.getRuntime().exec("ping -c 1 "+ipAddress).waitFor();
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    public static boolean isIp(String ip){//判断是否是一个IP
        boolean b = false;
        ip = trimSpaces(ip);
        if(ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            String s[] = ip.split("\\.");
            if(Integer.parseInt(s[0])<255)
                if(Integer.parseInt(s[1])<255)
                    if(Integer.parseInt(s[2])<255)
                        if(Integer.parseInt(s[3])<255)
                            b = true;
        }
        return b;
    }

    public static String trimSpaces(String ip){//去掉IP字符串前后所有的空格
        while(ip.startsWith(" ")){
            ip= ip.substring(1,ip.length()).trim();
        }
        while(ip.endsWith(" ")){
            ip= ip.substring(0,ip.length()-1).trim();
        }
        return ip;
    }
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
        }
        return "";
    }


    public static void main(String[] args) throws Exception {
        String ipAddress = "10.1.11.115";
        System.out.println(ping(ipAddress));
    }
}
