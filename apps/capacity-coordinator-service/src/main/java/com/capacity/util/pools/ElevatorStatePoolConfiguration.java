package com.capacity.util.pools;

import com.capacity.services.ElevatorState;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/*
 * Project: liftinator
 * File: CapacityCoordinatorApplication.java
 * Author: Chris Harper
 * The ElevatorStatePoolConfiguration class configures a connection or object pool
 * for managing instances of the ElevatorState class. The class utilizes Apache Commons Pool (specifically CommonsPool2TargetSource)
 * for pooling, which is useful for managing resources such as database connections,
 * HTTP clients, or other expensive-to-create objects.
 */
@Configuration
@ComponentScan(basePackages = "com.capacity")
public class ElevatorStatePoolConfiguration {

  @Value("${ElevatorStateTargetPool.pool.maxSize:10}")
  private int maxSize;

  @Value("${ElevatorStateTargetPool.pool.minIdle:2}")
  private int minIdle;

  @Value("${ElevatorStateTargetPool.pool.minEvictableIdleTime:2}")
  private int minEvictableIdleTime;

  @Value("${ElevatorStateTargetPool.pool.timeBetweenEvictionRuns:2}")
  private int timeBetweenEvictionRuns;

  @Bean("ElevatorStateTargetPool")
  public CommonsPool2TargetSource baseMDBTargetSource() {
    final CommonsPool2TargetSource commonsPoolTargetSource = new CommonsPool2TargetSource();
    commonsPoolTargetSource.setTargetBeanName("com.capacity.services.ElevatorState");
    commonsPoolTargetSource.setTargetClass(ElevatorState.class);
    commonsPoolTargetSource.setMaxSize(maxSize);
    commonsPoolTargetSource.setMinIdle(minIdle);
    commonsPoolTargetSource.setMinEvictableIdleTimeMillis(minEvictableIdleTime);
    commonsPoolTargetSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRuns);
    return commonsPoolTargetSource;
  }

}

