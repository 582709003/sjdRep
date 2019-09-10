package cn.com.zhsz.eco.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽取公共response与request字段
 */
public class BaseRquestAndResponse {

	private static final Logger logger = LoggerFactory.getLogger(BaseRquestAndResponse.class);

	//获取request请求HttpServletRequest
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	// 获取response请求
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	// 获取session请求
	public static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	/**
	* 把数据对象转换成json字符串
	* DTO对象形如：{"id" : idValue, "name" : nameValue, ...}
	* 数组对象形如：[{}, {}, {}, ...]
	* map对象形如：{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...}
	* @param object
	* @return
	*/
	public static String getJSONString(Object object){
		String jsonString = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero);
		return jsonString == null ? "{}" : jsonString;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 *
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 *
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 *
	 * 用户真实IP为： 192.168.1.110
	 *
	 * @return
	 */
	public static String getIpAddress() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * @explain : 统一封装分页数据
	 * @Author : chao
	 * @CreationDate : 2019/3/7
	 */
	public static JSONObject getPageInfo() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constant.PAGENUM, 1);
		jsonObject.put(Constant.PAGESIZE, 10);
		try {
			JSONObject json = Utils.getRequestPames(getRequest());
			if(json != null){
				if(json.containsKey(Constant.PAGENUM)){
					jsonObject.put(Constant.PAGENUM, json.getIntValue(Constant.PAGENUM));
				}
				if(json.containsKey(Constant.PAGESIZE)){
					jsonObject.put(Constant.PAGESIZE, json.getIntValue(Constant.PAGESIZE));
				}
			}
		}catch (Exception e){
			logger.error("分页信息获取错误===============",e);
		}
		logger.debug("==========分页数据:="+jsonObject.get(Constant.PAGENUM)+"/"+jsonObject.get(Constant.PAGESIZE)+"===========");
		return jsonObject;
	}

	/**
	 * @explain : 统一封装实体类入参
	 * @Author : chao
	 * @CreationDate : 2019/3/7
	 */
	public static <T> T getObject(Class obj) {
		try {
			JSONObject json = Utils.getRequestPames(getRequest());
			if(json != null){
				logger.debug("==========解析实体========"+obj.getName()+"===========");
				logger.debug("==========解析实体========"+json.toString()+"===========");
				return (T) JSON.toJavaObject(json,obj);
			}
		}catch (Exception e){
			logger.error("解析实体类入参错误===============",e);
		}
		return null;
	}

	/**
	 * @explain : 统一封装实体类入参
	 * @Author : chao
	 * @CreationDate : 2019/3/7
	 */
	public static <T> T getAllObject(Class obj) {
		try {
			JSONObject json = Utils.getAllRequestPames(getRequest());
			if(json != null){
				logger.debug("==========解析实体========"+obj.getName()+"===========");
				logger.debug("==========解析实体========"+json.toString()+"===========");
				return (T) JSON.toJavaObject(json,obj);
			}
		}catch (Exception e){
			logger.error("解析实体类入参错误===============",e);
		}
		return null;
	}

	/**
	 * @explain : 统一获取入参
	 * @Author : chao
	 * @CreationDate : 2019/3/7
	 */
	public static JSONObject getObject() {
		try {
			return Utils.getRequestPames(getRequest());
		}catch (Exception e){
			logger.error("统一获取入参错误===============",e);
		}
		return null;
	}

	/**
	 * 返回json到前台
	 * @param result
	 */
	public static void returnString(Map<String, Object> result) {
		String str = getJSONString(result);
		try {
			//System.out.println("================="+str);
			getResponse().setContentType("text/html;charset=UTF-8");
			getResponse().getWriter().println("{\"result\":\""+AESUtil.encrypt(str)+"\"}");
		} catch (IOException e) {
			logger.error("IOException"+e);
		}
	}

	/**
	 * 返回json到前台
	 */
	public static void returnString() {
		Map<String, Object> result = new HashMap<>();
		result.put("code",0);
		result.put("msg",StepExecutor.getInstance().get("0"));
		returnString(result);
	}

	/**
	 * 返回json到前台
	 */
	public static void returnString(String str) {
		Map<String, Object> result = new HashMap<>();
		result.put("code",str);
		result.put("msg",StepExecutor.getInstance().get(str));
		returnString(result);
	}

	/**
	 * 返回json到前台
	 */
	public static void returnString(Object... strs) {
		Map<String, Object> result = new HashMap<>();
		result.put("code",strs[0]);
		result.put("msg",StepExecutor.getInstance().get(String.valueOf(strs[0])));
		for (int i = 1; i < strs.length ; i+=2) {
			result.put(String.valueOf(strs[i]),strs[i+1]);
		}
		returnString(result);
	}

	/**
	 * 返回json到前台
	 */
	public static void returnString(String str,Map<String, Object> result) {
		result.put("code",str);
		result.put("msg",StepExecutor.getInstance().get(str));
		returnString(result);
	}

}
