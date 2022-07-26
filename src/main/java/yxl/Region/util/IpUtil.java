package yxl.Region.util;

import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: yxl
 * @Date: 2022/7/26 15:23
 */

public class IpUtil {
    public String getLocalIp() {
        InetAddress ip4;
        try {
            ip4 = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        if (ip4 != null)
            return ip4.getHostAddress();
        return null;
    }
}
