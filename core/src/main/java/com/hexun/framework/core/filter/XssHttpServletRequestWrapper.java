package com.hexun.framework.core.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * xss过滤
 * 
 * @author zhoudong
 * 
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private static Pattern[] patterns = new Pattern[] {
			// Script fragments
			Pattern.compile("<scripts>(.*?)</scripts>",Pattern.CASE_INSENSITIVE),
			// lonely script tags
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
			Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// eval(...)
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// expression(...)
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// vbscript:...
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			// onload(...)=...
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 规范化后请求参数map
	 */
	private Map<String, String[]> sanitized;

	// getParameterValues()
	public String[] getParameterValues(String paramter) {
		String[] values = super.getParameterValues(paramter);

		if (values == null) {
			return values;
		}

		int count = values.length;
		String[] encodeValues = new String[count];

		for (int i = 0; i < count; i++) {
			encodeValues[i] = stripXSS(values[i]);
		}

		return encodeValues;

	}

	// getParameter()
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		return stripXSS(value);
	}

	// getParameterMap()
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> maps = (HashMap) super.getParameterMap();

		if (sanitized == null)
			sanitized = sanitizeParamMap(maps);
		return sanitized;
	}

	/**
	 * 规范请求参数
	 * 
	 * @param raw
	 * @return
	 */
	private Map<String, String[]> sanitizeParamMap(Map<String, String[]> raw) {
		Map<String, String[]> res = new HashMap<String, String[]>();
		if (raw == null)
			return res;

		for (String key : (Set<String>) raw.keySet()) {
			String[] rawVals = raw.get(key);
			String[] snzVals = new String[rawVals.length];
			for (int i = 0; i < rawVals.length; i++) {
				snzVals[i] = stripXSS(rawVals[i]);
			}
			res.put(key, snzVals);
		}
		return res;
	}

	// getHeader()
	public String getHeader(String name) {
		String value = super.getHeader(name);

		return stripXSS(value);
	}

	/**
	 * NOTE: It's highly recommended to use the ESAPI library and uncomment the
	 * following line to avoid encoded attacks. value =
	 * ESAPI.encoder().canonicalize(value);
	 * */
	private String stripXSS(String value) {

		if (value != null) {
			// Avoid null characters
			value = value.replaceAll("\0", "");

			for (Pattern scriptPattern : patterns) {
				value = scriptPattern.matcher(value).replaceAll("");
			}
		}
		return value;
	}
}