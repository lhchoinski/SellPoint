package com.system.sales.component;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


@Component
@RequiredArgsConstructor
public class DistributedLockComponent {

    private final RedissonClient redissonClient;

    public <T> void executeWithLock(String lockKey, long waitTimeSec, long leaseTimeSec, Supplier<T> action) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean acquired = false;

        try {
            acquired = lock.tryLock(waitTimeSec, leaseTimeSec, TimeUnit.SECONDS);

            if (!acquired) {
                throw new IllegalStateException("Unable to acquire lock: " + lockKey);
            }

            action.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while trying to acquire lock: " + lockKey, e);
        } catch (Exception e) {
            throw new RuntimeException("Error executing action with lock: " + lockKey, e);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
