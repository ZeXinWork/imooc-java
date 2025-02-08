package com.imooc.first.domain.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenService {

    @Autowired
    private RedissonClient redissonClient;

    public boolean saveUserToken(Integer userId, String token) {
        // 获取 Redis 中的 bucket
        RBucket<String> bucket = redissonClient.getBucket(Constants.USER_TOKEN_KEY + userId);

        // 删除旧的 Token，确保只有一个有效的 Token
        bucket.delete(); // 删除现有的 Token

        // 设置新的 Token，并设置过期时间 24小时
        bucket.set(token, Constants.TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        return true; // 返回成功
    }

    public String getUserToken(String userId) {
        RBucket<String> bucket = redissonClient.getBucket(Constants.USER_TOKEN_KEY + userId);
        return bucket.get(); // 获取用户的 Token
    }

    public void removeUserToken(Integer userId) {
        RBucket<String> bucket = redissonClient.getBucket(Constants.USER_TOKEN_KEY + userId);
        bucket.delete(); // 删除用户的 Token
    }
}
