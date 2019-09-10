package cn.com.zhsz.eco.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 工具类
 */

public class Utils {

	private static Logger logger = LoggerFactory.getLogger(Utils.class);

	/**
	 * 返回UUID
	 *
	 * @return
	 * @author 郑建超
	 * @Time 2017年3月21日
	 * @version 1.0v
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取数字id
	 */
	public static String getAtomicCounter() {
		String returnValue = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
		Long random = Long.parseLong(returnValue, 16);
		return DateUtils.mmddhh.format(new Date()) + random.toString().substring(0, 4);
	}

	/**
	 * 把数据对象转换成json字符串 DTO对象形如：{"id" : idValue, "name" : nameValue, ...}
	 * 数组对象形如：[{}, {}, {}, ...] map对象形如：{key1 : {"id" : idValue, "name" :
	 * nameValue, ...}, key2 : {}, ...}
	 *
	 * @param object
	 * @return
	 */
	public static String getJSONString(Object object) {
		String jsonString = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat);
		return jsonString == null ? "{}" : jsonString;
	}

	/**
	 * xml字符串转json
	 *
	 * @param xmlStr
	 * @return
	 */
	public static JSONObject xmlToJson(String xmlStr) {
		JSONObject json = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			json.put(root.getName(), iterateElement(root));
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 一个迭代方法
	 *
	 * @param root
	 * @return
	 */
	private static JSONObject iterateElement(Element root) {
		List rootChilds = root.getChildren();
		Element rootChild = null;
		JSONObject json = new JSONObject();
		List list = null;
		for (int i = 0; i < rootChilds.size(); i++) {
			list = new LinkedList();
			rootChild = (Element) rootChilds.get(i);
			if ("".equals(rootChild.getTextTrim()) || "null".equals(rootChild.getTextTrim())) {
				if (rootChild.getChildren().size() == 0) {
					json.put(rootChild.getName(), "");
					continue;
				}
				if (!json.containsKey(rootChild.getName())) {
					json.put(rootChild.getName(), iterateElement(rootChild));
				} else {
					if (json.get(rootChild.getName()) instanceof List) {
						list = (List) json.get(rootChild.getName());
					} else {
						list = new LinkedList();
						list.add(json.get(rootChild.getName()));
					}
					list.add(iterateElement(rootChild));
					json.put(rootChild.getName(), list);
				}
			} else {
				if (!json.containsKey(rootChild.getName())) {
					json.put(rootChild.getName(), rootChild.getTextTrim());
				} else {
					List strList = null;
					if (json.get(rootChild.getName()) instanceof List) {
						strList = (List) json.get(rootChild.getName());
					} else {
						strList = new LinkedList();
						strList.add(json.get(rootChild.getName()));
					}
					strList.add(rootChild.getTextTrim());
					json.put(rootChild.getName(), strList);
				}
			}
		}
		return json;
	}

	/**
	 * 转为响应xml
	 *
	 * @param json
	 * @return
	 */
	public static String jsonToXml(JSONObject json, String head) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		mapToXMLTest(json, sb, head);
		try {
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void mapToXMLTest(Object param, StringBuffer sb, String key) {
		if (StringUtils.isBlank(param.toString())) {
			sb.append("<" + key + "/>");
			return;
		}
		if (key == null) {
			if (param instanceof Map) {
				Map map = (Map) param;
				Set set = map.keySet();
				for (Iterator it = set.iterator(); it.hasNext();) {
					String k = (String) it.next();
					Object value = map.get(k);
					if (null == value) {
						value = "";
					}
					mapToXMLTest(value, sb, k);
				}
			} else {
				sb = new StringBuffer("失败");
			}
		} else {
			if (param instanceof List) {
				List list = (List) param;
				for (Object object : list) {
					mapToXMLTest(object, sb, key);
				}
			} else {
				sb.append("<" + key + ">");
			}
			if (param instanceof Map) {
				Map map = (Map) param;
				Set set = map.keySet();
				for (Iterator it = set.iterator(); it.hasNext();) {
					String k = (String) it.next();
					Object value = map.get(k);
					if (null == value) {
						value = "";
					}
					mapToXMLTest(value, sb, k);
				}
			} else if (param instanceof Integer) {
				sb.append(param);
			} else if (param instanceof String) {
				sb.append(param);
			} else if (param instanceof Double) {
				sb.append(param);
			} else if (param instanceof Float) {
				sb.append(param);
			} else if (param instanceof Long) {
				sb.append(param);
			} else if (param instanceof Boolean) {
				sb.append(param);
			} else if (param instanceof Date) {
				sb.append(DateUtils.mm_dd_hh.format(param));
			}
			if (!(param instanceof List)) {
				sb.append("</" + key + ">");
			}
		}
	}

	/**
	 * 请求webService接口
	 *
	 * @param url
	 * @param method
	 * @param json
	 * @return
	 */
	public static JSONObject fandWebService(String url, String method, JSONObject json) {
		// 创建动态客户端
		try {
			String xml = jsonToXml(json, "params");
			logger.debug("接口入参=============" + xml);
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(url);
			Object[] objects = new Object[0];
			objects = client.invoke(method, xml);
			logger.debug("接口返回=============" + objects[0]);
			return xmlToJson(String.valueOf(objects[0]));
		} catch (Exception e) {
			logger.error("请求接口失败!", e);
		}
		return null;
	}

	/**
	 * @explain : 删除json中的空对象
	 * @Author : chao
	 * @CreationDate : 2019/3/25
	 */
	public static JSONObject rmJsonNull(String str) {
		logger.debug("清除json======" + str);
		if (StringUtils.isBlank(str) || "{}".equals(str)) {
			return new JSONObject();
		}
		JSONObject json1 = JSONObject.parseObject(str);
		JSONObject json = new JSONObject();
		for (Map.Entry<String, Object> entry : json1.entrySet()) {
			if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
				if (entry.getValue() instanceof JSONArray) {
					json.put(entry.getKey(), entry.getValue());
				} else {
					json.put(entry.getKey(), entry.getValue().toString().trim());
				}
			}
		}
		logger.debug("清除后======" + json);
		return json;
	}

	/**
	 * @explain : 删除json中的空对象
	 * @Author : chao
	 * @CreationDate : 2019/3/25
	 */
	public static JSONObject returnAllJson(String str) {
		logger.debug("所有参数======" + str);
		if (StringUtils.isBlank(str) || "{}".equals(str)) {
			return new JSONObject();
		}
		JSONObject json1 = JSONObject.parseObject(str);
		logger.debug("返回所有参数======" + json1);
		return json1;
	}

	/**
	 * 解析url
	 *
	 * @param url
	 * @return
	 */
	public static JSONObject parseUrl(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		String[] urlParts = url.split("\\?");
		// 没有参数
		if (urlParts.length == 1) {
			return null;
		}
		// 有参数
		String[] params = urlParts[1].split("&");
		JSONObject json = new JSONObject();
		for (String param : params) {
			String[] keyValue = param.split("=");
			json.put(keyValue[0], keyValue[1]);
		}
		return json;
	}

	/**
	 * @explain : 统一封装入参
	 * @Author : chao
	 * @CreationDate : 2019/3/7
	 */
	public static JSONObject getRequestPames(HttpServletRequest request) {
		JSONObject json = (JSONObject) request.getAttribute(Constant.PARAMETER);
		if (json == null) {
			String pojo = request.getParameter(Constant.PARAMETER);
			json = rmJsonNull(AESUtil.decrypt(pojo));
			request.setAttribute(Constant.PARAMETER, json);
		}
		return json;
	}

	/**
	 * @explain : 统一封装入参
	 * @Author : chao
	 * @CreationDate : 2019/3/7
	 */
	public static JSONObject getAllRequestPames(HttpServletRequest request) {
		JSONObject json = (JSONObject) request.getAttribute(Constant.PARAMETER);
		if (json == null) {
			String pojo = request.getParameter(Constant.PARAMETER);
			json = returnAllJson(AESUtil.decrypt(pojo));
			request.setAttribute(Constant.PARAMETER, json);
		}
		return json;
	}

	/**
	 * 调用短信接口
	 * 
	 * @param output
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
	 */
	public static JSONObject sendSms(String output) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		// 流处理
		InputStream input = null;
		InputStreamReader inputReader = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		try {
			// 建立连接
			URL url = new URL(Constant.SMSREQUESTURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml");
			if (output != null) {
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}
			// 流处理
			input = connection.getInputStream();
			inputReader = new InputStreamReader(input, "UTF-8");
			reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			logger.debug("短信调用结果=============" + buffer.toString());
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (Exception e) {
			logger.error("短信调用失败", e);
		} finally {
			try {
				// 关闭连接、释放资源
				if (reader != null) {
					reader.close();
				}
				if (inputReader != null) {
					inputReader.close();
				}
				if (input != null) {
					input.close();
					input = null;
				}
				if (connection != null) {
					connection.disconnect();
				}
			} catch (IOException e) {
				logger.error("短信调用失败", e);
			}
		}
		return jsonObject;
	}
}
