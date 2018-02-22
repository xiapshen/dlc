package com.happygo.dlc.api.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * ClassName: DlcHealthIndicator <br/>
 * Description: 自定义系统健康指标检测 <br/>
 * Date: 2017/10/24 14:13 <br/>
 *
 * @author sxp(1378127237 qq.com)<br/>
 * @version 1.0 <br/>
 */
@Component
public class DlcHealthIndicator implements HealthIndicator {

    /**
     * Health health.
     *
     * @return the health
     */
    @Override
    public Health health() {
        return Health.up()
                .withDetail("timestamp", System.currentTimeMillis()).build();
    }
}