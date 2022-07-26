package yxl.Region.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yxl
 * @Date: 2022/7/26 15:43
 */

@Configuration
public class UtilsConfig {

    @Bean
    public IpUtil getIpUtil() {
        return new IpUtil();
    }

    @Bean
    public PosUtil getPosUtil() {
        return new PosUtil();
    }
}
