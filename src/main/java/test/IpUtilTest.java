package test;

import yxl.Region.util.IpUtil;
import yxl.Region.util.PosUtil;

import java.io.IOException;

/**
 * @Author: yxl
 * @Date: 2022/7/26 15:38
 */
public class IpUtilTest {

    public static void main(String[] args) throws IOException {
        IpUtil ipUtil = new IpUtil();
        String localIp = ipUtil.getLocalIp();
        System.out.println(localIp);
        PosUtil posUtil = new PosUtil();
        System.out.println(posUtil.getPosByIp(localIp));
    }
}
