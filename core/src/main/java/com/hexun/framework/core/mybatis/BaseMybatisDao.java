package com.hexun.framework.core.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.hexun.framework.core.page.MysqlDialect;
import com.hexun.framework.core.page.Page;

public class BaseMybatisDao extends SqlSessionDaoSupport  {

	protected final Log logger = LogFactory.getLog(BaseMybatisDao.class);
	 @Resource
     public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
     }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<?> findByPageBySqlId(String sqlId,
			Map<String, Object> params, Integer pageNo, Integer pageSize) {
		
		pageNo = null == pageNo? 1 : pageNo ;
		pageSize = null == pageSize? 10 : pageSize;
		
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Configuration c = this.getSqlSession().getConfiguration();
		BoundSql boundSql = c.getMappedStatement(sqlId).getBoundSql(params);
		String sqlcode = boundSql.getSql();
		logger.debug("findByPageBySqlId sql = " + sqlcode);
		try {
			Connection conn = this.getSqlSession().getConnection();
			
			int offset = (page.getPageNo() - 1) * page.getPageSize();
			
			params.put("page_sql", " limit " + offset + "," + pageSize +" ");
			
			List resultList = this.getSqlSession().selectList(sqlId, params);
			page.setList(resultList);
			PreparedStatement ps = 
				getPreparedStatement(boundSql.getSql(), 
										boundSql.getParameterMappings(),
											params ,
												conn,
												//分页不查所有,在当前页基础上 + pageSize 的平方
												0,pageNo * pageSize + pageSize * pageSize);
			ps.execute();
			ResultSet set = ps.getResultSet();
		
			while(set.next()){
				page.setTotalCount(set.getInt(1));
			}
		} catch (Exception e) {
			logger.error("jdbc.error.code.findByPageBySqlId",e);
			throw new RuntimeException("jdbc.error.code.findByPageBySqlId", e);
		}
		return page;
	}
	/**
	 * 查询分页, 用户表多关联查询
	 * @param sqlId
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<?> findByPage(String sqlId,String countSqlId,
			Map<String, Object> params, Integer pageNo, Integer pageSize) {
		
		pageNo = null == pageNo? 1 : pageNo ;
		pageSize = null == pageSize? 10 : pageSize;
		
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		Configuration c = this.getSqlSession().getConfiguration();
		
		BoundSql boundSql = c.getMappedStatement(sqlId).getBoundSql(params);
		BoundSql countSql = c.getMappedStatement(countSqlId).getBoundSql(params);
		
		String sqlcode = boundSql.getSql();
		logger.debug("findByPageBySqlId sql = " + sqlcode);
		try {
			Connection conn = this.getSqlSession().getConnection();
			
			int offset = (page.getPageNo() - 1) * page.getPageSize();
			
			params.put("page_sql", " limit " + offset + "," + pageSize +" ");
			
			List resultList = this.getSqlSession().selectList(sqlId, params);
			page.setList(resultList);
			PreparedStatement ps = 
				getPreparedStatement(countSql.getSql(), 
											boundSql.getParameterMappings(),
													params ,
														conn,
														//分页不查所有,在当前页基础上 + pageSize 的平方
														0,pageNo * pageSize + pageSize * pageSize);
			ps.execute();
			ResultSet set = ps.getResultSet();
		
			while(set.next()){
				page.setTotalCount(set.getInt(1));
			}
		} catch (Exception e) {
			logger.error("jdbc.error.code.findByPageBySqlId",e);
			throw new RuntimeException("jdbc.error.code.findByPageBySqlId", e);
		}
		return page;
	}
	
	
	public PreparedStatement getPreparedStatement(String sql,List<ParameterMapping> parameterMappingList,
			Map<String ,Object> params, Connection conn,Integer begin,Integer size) throws SQLException {
			/**
			 * 分页根据数据库分页
			 */
			MysqlDialect o = new MysqlDialect();
			
			PreparedStatement ps=conn.prepareStatement(o.getCountSqlString(sql,begin,size));
			int index=1;
			for(int i = 0; i < parameterMappingList.size(); i++) {
				ps.setObject(index++, params.get(parameterMappingList.get(i).getProperty()));
			}
				return ps;
			}
			
	public Map<String, Object> callProcedureUtils(Map<String, Object> params, String proName) {
		this.getSqlSession().selectList(proName, params);
		return params;
	}
}
