package org.ssm.dufy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CommonUtils {

	
	public static String getXh(){
		String xh=null;
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
		xh=sf.format(new Date());
		Random random=new Random();
		for(int i=0;i<4;i++){
			xh+=random.nextInt(9);
		}
		return xh;
	}
	
	
	public static String DateToString(Date date,String style){
		String dateToString = null;
		SimpleDateFormat sf=null;
		if(style !="" && style !=null){
			sf=new SimpleDateFormat(style);
			dateToString=sf.format(date);
		}else{
			sf=new SimpleDateFormat("yyyy-MM-dd");
			dateToString=sf.format(date);
		}
		return dateToString;
	}
	
	public static Date DateToStringToDate(Date date,String style){
		Date date2=null;
		String dateToString = null;
		SimpleDateFormat sf=null;
		if(style !="" && style !=null){
			sf=new SimpleDateFormat(style);
			dateToString=sf.format(date);
			try {
				date2=sf.parse(dateToString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			sf=new SimpleDateFormat("yyyy-MM-dd");
			dateToString=sf.format(date);
			try {
				date2=sf.parse(dateToString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date2;
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getGxsjc(){
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result=sf.format(date);
		return result;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(DateToStringToDate(new Date(),"yyyy-MM-dd HH:mm:ss") );
	}
}
