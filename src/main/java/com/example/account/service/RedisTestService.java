package com.example.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.stereotype.Service;

import java.rmi.server.ExportException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisTestService {

    private final RedissonClient redissonClient;

    public String getLock(){
        RLock lock = redissonClient.getLock("sampleLock");


        try{
            boolean isLock = lock.tryLock(1,5, TimeUnit.SECONDS);
            if(!isLock){
                log.error("=================lock acquisition failed============");
                return "Lock failed";
            }
        } catch (Exception e){
            log.error("Redis lock failed");
        }

        return "get lock success";
    }
}
