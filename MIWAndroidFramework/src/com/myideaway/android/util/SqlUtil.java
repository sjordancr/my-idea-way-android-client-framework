package com.myideaway.android.util;

public class SqlUtil {
	public static String showSql(String sql, Object[] args){
		if(args == null){
			return sql;
		}
		
		sql = sql.replace("?", "#");
		for(Object arg : args){
			if(arg == null){
				sql = sql.replaceFirst("#", "null");
			}else{
				sql = sql.replaceFirst("#", arg.toString());
			}
			
		}
		
		return "Show sql:" + sql + "";
	}
}
