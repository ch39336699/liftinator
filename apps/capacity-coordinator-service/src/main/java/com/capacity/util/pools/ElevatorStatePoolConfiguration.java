package com.capacity.util.pools;

import com.capacity.services.ElevatorState;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.capacity")
public class ElevatorStatePoolConfiguration {

  @Value("${baseMDB.pool.maxSize: 2}")
  private int rulesProcMaxSize;

  @Value("${baseMDB.pool.minIdle: 2}")
  private int rulesProcMinIdle;

  @Value("${baseMDB.pool.minEvictableIdleTime: 2}")
  private int rulesProcMinEvictableIdleTime;

  @Value("${baseMDB.pool.timeBetweenEvictionRuns: 2}")
  private int rulesProcTimeBetweenEvictionRuns;

  @Bean("ElevatorStateTargetPool")
  public CommonsPool2TargetSource baseMDBTargetSource() {
    final CommonsPool2TargetSource commonsPoolTargetSource = new CommonsPool2TargetSource();
    commonsPoolTargetSource.setTargetBeanName("com.capacity.services.ElevatorState");
    commonsPoolTargetSource.setTargetClass(ElevatorState.class);
    commonsPoolTargetSource.setMaxSize(rulesProcMaxSize);
    commonsPoolTargetSource.setMinIdle(rulesProcMinIdle);
    commonsPoolTargetSource.setMinEvictableIdleTimeMillis(rulesProcMinEvictableIdleTime);
    commonsPoolTargetSource.setTimeBetweenEvictionRunsMillis(rulesProcTimeBetweenEvictionRuns);
    return commonsPoolTargetSource;
  }

}

