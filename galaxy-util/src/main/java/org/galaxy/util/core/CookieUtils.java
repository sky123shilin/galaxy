package org.galaxy.util.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Cookie工具类
 *
 * 基于spring mvc
 */
public class CookieUtils {

	// 默认缓存时间,单位/秒, 2H
	private static final int COOKIE_MAX_AGE = Integer.MAX_VALUE;
	// 保存路径,根路径
	private static final String COOKIE_PATH = "/";

	/**
	 * 设置Cookie
	 * @param response  Http Response
	 * @param key       Cookie的key
	 * @param value     Cookie的value
	 * @param ifRemember  是否记得我
	 */
	public static void set(HttpServletResponse response, String key, String value, boolean ifRemember) {
		int age = ifRemember ? COOKIE_MAX_AGE : -1;
		set(response, key, value, null, COOKIE_PATH, age, true);
	}

	/**
	 * 设置Cookie
	 * @param response  Http Response
	 * @param key       Cookie的key
	 * @param value     Cookie的value
	 * @param domain    Cookie的domain
	 * @param path      Cookie的path
	 * @param maxAge    Cookie的maxAge
	 * @param httpOnly  Cookie的httpOnly
	 */
	private static void set(HttpServletResponse response, String key, String value, String domain, String path, int maxAge, boolean httpOnly) {
		Cookie cookie = new Cookie(key, value);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge);
		cookie.setPath(Objects.nonNull(path) ? path : COOKIE_PATH);
		if (Objects.nonNull(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 获取Cookie中对应的key的值
	 * @param request  Http Request
	 * @param key      Cookie的key值
	 * @return         返回Cookie的value
	 */
	public static String getValue(HttpServletRequest request, String key) {
		Cookie cookie = get(request, key);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	/**
	 * 返回Key对应的Cookie
	 * @param request   Http Request
	 * @param key       Cookie对应的key
	 * @return          返回一个Cookie对象
	 */
	private static Cookie get(HttpServletRequest request, String key) {
		Cookie[] arr_cookie = request.getCookies();
		if (arr_cookie != null && arr_cookie.length > 0) {
			for (Cookie cookie : arr_cookie) {
				if (cookie.getName().equals(key)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * 删除一个Cookie
	 * @param request   Http Request
	 * @param response  Http Response
	 * @param key       需要删除的Cookie的key
	 */
	public static void remove(HttpServletRequest request, HttpServletResponse response, String key) {
		Cookie cookie = get(request, key);
		if (cookie != null) {
			set(response, key, "", null, COOKIE_PATH, 0, true);
		}
	}

}