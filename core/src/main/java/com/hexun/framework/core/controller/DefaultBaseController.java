package com.hexun.framework.core.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hexun.framework.core.properties.PropertiesUtils;

/**
 * 基础controller
 * 
 * @author zhoudong
 *
 */
public class DefaultBaseController implements ServletConfigAware {
	
	// -- header 常量定义 --//
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;

	protected static final int PAGE_SIZE = 10; // 每页的记录数
	protected static final int PAGE_NO = 1; // 当前页号

	//结果集返回Map
	protected Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
	
	/**
	 * 获取应用绝对路径
	 * @author zhoudong
	 * @return
	 */
	protected String getWebApplicationAbsolutePath(HttpServletRequest request) {
		String realPath = getRequest().getSession().getServletContext().getRealPath("/");
		return realPath;
	}

	/**
	 * 取得HttpServletRequest的简化函数.
	 * @author zhoudong
	 */
	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 取得session
	 * @author zhoudong
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 取得HttpServletResponse的简化函数.
	 * @author zhoudong
	 */
	protected HttpServletResponse getResponse() {
		return ((ServletWebRequest) RequestContextHolder.getRequestAttributes())
				.getResponse();
	}
	/**
	 * 获取modelandview对象
	 * 不可以携带参数
	 * @author zhoudong
	 * @return
	 */
	protected ModelAndView getModelAndView(String viewName) {
		return commModelAndView(viewName);
	}
	/**
	 * 获取modelandview对象
	 * 可以带一对参数
	 * @author zhoudong
	 * @return
	 */
	protected ModelAndView getModelAndView(String viewName,String modelKey,Object modelValue) {
		ModelAndView mav = commModelAndView(viewName);
		mav.addObject(modelKey, modelValue);
		return mav;
	}
	/**
	 * 获取modelandview对象
	 * 可以带两对参数
	 * @author zhoudong
	 * @return
	 */
	protected ModelAndView getModelAndView(String viewName,String modelKey,Object modelValue,String modelKey1,Object modelValue1) {
		ModelAndView mav = commModelAndView(viewName);
		mav.addObject(modelKey, modelValue);
		mav.addObject(modelKey1, modelValue1);
		return mav;
	}
	/**
	 * 公共的填充ModelAndView的方法
	 * @author zhoudong
	 * @param viewName
	 * @return
	 */
	private ModelAndView commModelAndView(String viewName){
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("baseUrl", PropertiesUtils.getString("BASE_URL"));
		mav.addObject("realPath", getWebApplicationAbsolutePath(getRequest()));
		return mav;
	}
	
	/**
	 * 重定向
	 * @author zhoudong
	 * @param redirectUrl
	 * @return
	 */
	protected ModelAndView redirect(String redirectUrl){
		return new ModelAndView(new RedirectView(redirectUrl));
	}
	
	public void setServletConfig(ServletConfig servletConfig) {

	}
}
