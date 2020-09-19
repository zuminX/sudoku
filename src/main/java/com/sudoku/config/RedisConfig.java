package com.sudoku.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
    Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

    redisCacheConfigurationMap.put("rankList", this.getRedisCacheConfigurationWithTtl(3600));

    return redisCacheConfigurationMap;
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setValueSerializer(getObjectJackson2JsonRedisSerializer());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    return new RedisCacheManager(
        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
        this.getRedisCacheConfigurationWithTtl(0),
        this.getRedisCacheConfigurationMap()
    );
  }

  @Bean
  public KeyGenerator simpleKG() {
    return (o, method, objects) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(o.getClass().getSimpleName());
      sb.append(".");
      sb.append(method.getName());
      sb.append("[");
      Arrays.stream(objects).map(Object::toString).forEach(sb::append);
      sb.append("]");

      return sb.toString();
    };
  }

  private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(int seconds) {
    return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
        RedisSerializationContext
            .SerializationPair
            .fromSerializer(getObjectJackson2JsonRedisSerializer())
    ).entryTtl(Duration.ofSeconds(seconds));
  }

  private Jackson2JsonRedisSerializer<Object> getObjectJackson2JsonRedisSerializer() {
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    // 解决jackson2无法反序列化LocalDateTime的问题
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    om.registerModule(new JavaTimeModule());
    jackson2JsonRedisSerializer.setObjectMapper(om);
    return jackson2JsonRedisSerializer;
  }
}
