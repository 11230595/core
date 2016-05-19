package com.hexun.framework.core.page;

import com.hexun.framework.core.utils.StringUtils;

public class MysqlDialect implements Dialect {
	protected static final String SQL_END_DELIMITER = ";";
	
	public String getLimitSqlString(String sql, int offset, int limit) {		
		sql = sql.trim();
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length()- 11 );
			isForUpdate = true;
		}
		
		if(offset < 0){
		    offset = 0;
		}
		
		StringBuffer pagingSelect = new StringBuffer();

		pagingSelect.append(sql +" limit "+offset+"," +limit);
		

		if ( isForUpdate ) {
			pagingSelect.append( " for update" );
		}
		
		return pagingSelect.toString();
	}

	public String getCountSqlString(String sql,Integer begin,Integer size) {
		sql = trim(sql);
		StringBuffer sb = new StringBuffer(sql.length() + 10);
		sb.append("SELECT COUNT(1) AS " + RS_COLUMN + " FROM  ( ");
		sb.append(sql);

		if(!StringUtils.isBlank(begin,size)){
			sb.append(" limit " );
			sb.append(begin);
			sb.append("," );
			sb.append(size);
		}
		sb.append(")_a ");
		
		
		return sb.toString();
	}

	public boolean supportsLimit() {
		return true;
	}

	private static String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1
					- SQL_END_DELIMITER.length());
		}
		return sql;
	}
}
