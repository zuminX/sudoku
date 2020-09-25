package com.sudoku.framework.config;

import static java.time.Duration.ofDays;
import static java.time.Duration.ofHours;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
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

  /**
   * 获取Redis缓存配置
   *
   * @return 缓存配置对象
   */
  private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
    Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
    //设置rankList的过期时间为一小时
    redisCacheConfigurationMap.put("rankList", getRedisCacheConfigurationWithTtl(ofHours(1L)));
    //设置statisticsUserData的过期时间为一天
    redisCacheConfigurationMap.put("statisticsUserData", getRedisCacheConfigurationWithTtl(ofDays(1L)));

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

  /**
   * 缓存管理器
   *
   * @param redisConnectionFactory Redis连接工厂
   * @return 缓存管理器对象
   */
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    return new RedisCacheManager(
        RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
        this.getRedisCacheConfigurationWithTtl(Duration.ZERO),
        this.getRedisCacheConfigurationMap()
    );
  }

  /**
   * 简单的Key生成方法
   *
   * @return Key生成器对象
   */
  @Bean
  public KeyGenerator simpleKG() {
    return new KeyGeneratorTemplateMethod() {
      @Override
      public String parameterToString(Object... parameter) {
        return Arrays.stream(parameter).map(Object::toString).collect(Collectors.joining("-"));
      }
    }.getKeyGenerator();
  }

  /**
   * 对LocalDateTime进行特殊处理的Key生成方法
   *
   * @return Key生成器对象
   */
  @Bean
  public KeyGenerator localDateTimeKG() {
    return new KeyGeneratorTemplateMethod() {
      @Override
      public String parameterToString(Object... parameter) {
        return Arrays.stream(parameter)
            .map(object -> object instanceof LocalDateTime ? ((LocalDateTime) object).toLocalDate().toString() : object.toString())
            .collect(Collectors.joining("-"));
      }
    }.getKeyGenerator();
  }

  /**
   * 使用Ttl获取Redis缓存配置
   *
   * @param ttl 过期时间
   * @return Redis缓存配置
   */
  private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Duration ttl) {
    return RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(getObjectJackson2JsonRedisSerializer())
    ).entryTtl(ttl);
  }

  /**
   * 获取Redis序列化器
   *
   * @return Jackson2JsonRedis序列化器对象
   */
  private Jackson2JsonRedisSerializer<Object> getObjectJackson2JsonRedisSerializer() {
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    //解决jackson2无法反序列化LocalDateTime的问题
    om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    om.registerModule(new JavaTimeModule());
    jackson2JsonRedisSerializer.setObjectMapper(om);
    return jackson2JsonRedisSerializer;
  }

  /**
   * Key生成器的模板方法
   */
  private abstract static class KeyGeneratorTemplateMethod {

    /**
     * 获取Key生成器
     *
     * @return Key生成器对象
     */
    public KeyGenerator getKeyGenerator() {
      return (o, method, objects) -> o.getClass().getSimpleName()
          + "."
          + method.getName()
          + "["
          + parameterToString(objects)
          + "]";
    }

    /**
     * 将参数转化为字符串
     *
     * @param parameter 参数
     * @return 转化后的字符串
     */
    public abstract String parameterToString(Object... parameter);
  }
}
