package org.ssm.dufy.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisTemplateUtil {
	
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private static RedisTemplate redisTemplate;
	
	private static final String LOCK_PREFIX="redisTemplate";

	private static final long LOCK_EXPIRE=8080;

	
	/**
	 * 最终加强分布式锁
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean lock(String key){
		final String lock=LOCK_PREFIX+key;
		//利用lambda表达式
		return (Boolean)redisTemplate.execute(new RedisCallback<Object>(){

			@Override
			public Object doInRedis(RedisConnection  redisConnection) throws DataAccessException {
				long expireAt=System.currentTimeMillis()+LOCK_EXPIRE+1;
				Boolean acquire=redisConnection.setNX(lock.getBytes(),String.valueOf(expireAt).getBytes());
				if(acquire){
					return true;
				}else{	
					byte[] value=redisConnection.get(lock.getBytes());
					if (Objects.nonNull(value) && value.length > 0) {
						long expireTime=Long.parseLong(new String(value));
						if(expireTime<System.currentTimeMillis()){
							// 如果锁已经过期
							byte[] oldValue=redisConnection.getSet(lock.getBytes(),String.valueOf(System.currentTimeMillis()+LOCK_EXPIRE+1).getBytes());
							//防止死锁
							return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
						}
					}	
				}
				return false;
			}
			
		});
	}
	
	/**
	 * 删除key
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static void delete(String key){
		redisTemplate.delete(key);
	}
	
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 秒
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean expire(String key,long time){
		try{
			if(time>0){
				redisTemplate.expire(key,time,TimeUnit.SECONDS);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据key获取过期时间
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static long getExpire(String key){
		return redisTemplate.getExpire(key,TimeUnit.SECONDS);
	}
	
	/**
	 * 判断key是否存在
	 * @param key 键值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasKey(String key){
		try{
			return redisTemplate.hasKey(key);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 删除缓存
	 * @param key 可以传一个或传多个
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	public static void del(String ... key){
		if(key !=null && key.length>0){
			if(key.length==1){
				redisTemplate.delete(key);
			}else{
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}
	
	/////////String相关类操作
	
	/**
	 *	获取缓存 
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		return key==null ? null :redisTemplate.opsForValue().get(key);
	}
	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean set(String key,String value){
		try{
			redisTemplate.opsForValue().set(key, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加缓存并设置过期时间
	 * @param key 键
	 * @param value 值
	 * @param time时间(秒) 
	 * @return true成功，false失败
	 */
	@SuppressWarnings("unchecked")
	public static boolean set(String key,String value,long time){
		try{
			if(time>0){
				redisTemplate.opsForValue().set(key, value,time,TimeUnit.SECONDS);
			}else{
				set(key, value);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 递增
	 * @param key
	 * @param delta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static long incr(String key,long delta){
		if(delta>0){
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}
	
	/**
	 * 递减
	 * @param key
	 * @param delta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static long decr(String key,long delta){
		if(delta<0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}
	
	/////////哈希相关操作/////////////////
	
	/**
	 * 设置一组Map的键值对
	 * HashGet
	 * @param key 键，不能为null
	 * @param item 项，不能为null
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public static Object hget(String key,String item){
		return redisTemplate.opsForHash().get(key,item);
	}
	
	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	@SuppressWarnings("unchecked")
	public static Map<Object,Object> hmget(String key){
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * 添加一个map类型的值
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功，false失败
	 */
	@SuppressWarnings("unchecked")
	public static boolean hmset(String key,Map<String,Object> map){
		try{
			redisTemplate.opsForHash().putAll(key,map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加一个Map类型的值并设置过期时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hmset(String key,Map<String,Object> map,long time){
		try{
			redisTemplate.opsForHash().putAll(key,map);
			if(time>0){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 向一个hash表中存入数据，如果不存在将创建
	 * @param key
	 * @param item
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hset(String key,String item,Object value){
		try{
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/**
	 * 向一张hash表中放入数据，如果不存在将创建,设置过期时间
	 * @param key
	 * @param item
	 * @param value
	 * @param time
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hset(String key,String item,Object value,long time){
		try{
			redisTemplate.opsForHash().put(key,item, value);
			if(time>0){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/**
	 * 删除hash表的值
	 * @param key
	 * @param item
	 */
	@SuppressWarnings("unchecked")
	public static void hdel(String key,Object... item){
		redisTemplate.opsForHash().delete(key, item);
	}
	
	/**
	 * 判断hash表中是否有该项的值
	 * @param key
	 * @param item
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hHasKey(String key,String item){
		return redisTemplate.opsForHash().hasKey(key,item);
	}
	
	/**
	 * 递增，
	 * @param key
	 * @param item
	 * @param by
	 * @return
	 */
	public static double hincr(String key,String item,double by){
		return redisTemplate.opsForHash().increment(key,item,by);
	}
	
	/**
	 * 递减
	 * @param key
	 * @param item
	 * @param by
	 * @return
	 */
	public static double hdecr(String key,String item,double by){
		return redisTemplate.opsForHash().increment(key,item,-by);
	}
	
	//////////////Set类型操作//////////////////////
	
	/**
	 * 根据key获取set中的所有值
	 * @param key
	 * @return
	 */
	public static Set<Object> sGet(String key){
		try{
			return redisTemplate.opsForSet().members(key);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据value从一个set中查询，是否存在
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean sHasKey(String key,Object value){
		try{
			return redisTemplate.opsForSet().isMember(key,value);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 将数据存放到set集合
	 * @param key
	 * @param values
	 * @return
	 */
	public static long sSet(String key,Object... values){
		try{
			return redisTemplate.opsForSet().add(key, values);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 添加一个set并设置过期时间
	 * @param key
	 * @param time
	 * @param values
	 * @return
	 */
	public static long sSetAndTime(String key,long time,Object... values){
		try{
			Long count=redisTemplate.opsForSet().add(key, values);
			if(time>0){
				expire(key, time);
			}
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取set缓存的长度
	 * @param key
	 * @return
	 */
	public static long sGetSetSize(String key){
		try{
			return redisTemplate.opsForSet().size(key);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
		
	/**
	 * 移出值为value的
	 * @param key
	 * @param values
	 * @return
	 */
	public static long setRemove(String key,Object... values){
		try{
			Long count=redisTemplate.opsForSet().remove(key, values);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	//=========list类型相关操作
	/**
	 * 获取List缓存的内容
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Object> lGet(String key,long start,long end){
		try{
			return redisTemplate.opsForList().range(key, start, end);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取list的缓存长度
	 * @param key
	 * @return
	 */
	public static long lGetListSize(String key){
		try{
			return redisTemplate.opsForList().size(key);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/**
	 * 通过检索，获取list	中的值
	 * @param key
	 * @param index
	 * @return
	 */
	public static Object lGetIndex(String key,long index){
		try{
			return redisTemplate.opsForList().index(key, index);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将list放入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean lSet(String key,Object value){
		try{
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 存入缓存list,设置超时时长
	 * @param key
	 * @param value
	 * @param time
	 * @return
	 */
	public static boolean lSet(String key,Object value,long time){
		try{
			redisTemplate.opsForList().rightPushAll(key, value);
			if(time>0 ){
				expire(key, time);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean lSet(String key,List<Object> value){
		try{
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 将list放入缓存,设置超时时长
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean lSet(String key,List<Object> value,long time){
		try{
			redisTemplate.opsForList().rightPushAll(key, value);
			if(time>0) expire(key, time);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	  /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    @SuppressWarnings("unchecked")
	public static boolean lUpdateIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    @SuppressWarnings("unchecked")
	public static long lRemove(String key,long count,Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    
}
