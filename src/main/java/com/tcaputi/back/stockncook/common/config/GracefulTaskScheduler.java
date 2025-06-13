package com.tcaputi.back.stockncook.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class GracefulTaskScheduler extends ThreadPoolTaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(GracefulTaskScheduler.class);
    private static final int AWAIT_TERMINATION_SECONDS = 60;
    private static final int POOL_SIZE = 10;

    public GracefulTaskScheduler() {
        this.setPoolSize(POOL_SIZE);
        this.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        this.setWaitForTasksToCompleteOnShutdown(true);
        this.setThreadNamePrefix("StockNCook");
        log.info("GracefulTaskScheduler initialized with {}s timeout and {} threads", AWAIT_TERMINATION_SECONDS, POOL_SIZE);
    }

    @Override
    public void destroy() {
        long startTime = System.currentTimeMillis();
        log.info("Start graceful shutdown of TaskScheduler (max {}s)", AWAIT_TERMINATION_SECONDS);
        this.getScheduledThreadPoolExecutor().setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        super.destroy();
        long endTime = System.currentTimeMillis();
        log.info("TaskScheduler shutdown completed in {} ms", endTime - startTime);
    }
}
