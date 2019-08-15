package org.ssm.dufy.util;

import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.formula.functions.T;
import org.ssm.dufy.entity.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil {
	//定义jackson对象
	private static final ObjectMapper MAPPER=new ObjectMapper();
	
	/**
	 * 将对象转换成json字符串
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj){
		try {
		 	String result=MAPPER.writeValueAsString(obj);
			return result;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将json结果集转换成对象
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData,Class<T> beanType){
		try {
			T t =MAPPER.readValue(jsonData,beanType);
			return t;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
	public static <T>List<T> jsonToList(String jsonData,Class<T> beanType){
		JavaType javaType=MAPPER.getTypeFactory().constructParametricType(List.class,beanType);
		try {
			List<T> list= MAPPER.readValue(jsonData,javaType);
			return list;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		User user=new User("wwq","男","111111","1111111111112");
	 	System.out.println(objectToJson(user)); 
	 	System.out.println(jsonToPojo(objectToJson(user),User.class));
	}
}
