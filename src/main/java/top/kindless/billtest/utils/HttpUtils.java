package top.kindless.billtest.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpUtils {

    public static String getIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (checkIP(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(',');
            if (index != -1) {
                ip = ip.substring(0, index);
            }
        }
        if (checkIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (checkIP(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (checkIP(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }

    private static boolean checkIP(String ip) {
        return ip != null && ip.length() != 0 && !"unkown".equalsIgnoreCase(ip)
                && ip.split("\\.").length == 4;
    }
}
