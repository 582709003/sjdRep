package cn.com.zhsz.eco.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * http请求及ssl证书请求
 * @see:
 * @Company:江苏鸿信系统集成有限公司微信开发组
 * @author 杨坚
 * @Time 2017年1月17日
 * @version 1.0v
 */
public abstract class HttpTool {

	private static final int connectionTimeOut = 3000;

	private static final int soTimeout = 40000;

	private static Logger logger = LoggerFactory.getLogger(HttpTool.class);

	/**
	 * 证书请求
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public static String certSSL(String data, String mchId, String certPath, String redpackURL) {
		StringBuffer message = new StringBuffer();
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(certPath));
			keyStore.load(instream, mchId.toCharArray());
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpost = new HttpPost(redpackURL);
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			System.out.println("executing request" + httpost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: " + entity.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						message.append(text);
					}
				}
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				response.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return message.toString();
	}

	/**
	 * 将数据流写入文件
	 *
	 * @param formFile
	 *            从页面传过来的文件对象
	 * @param file
	 *            文件对象
	 */
	public static void streamToFile(MultipartFile filedate, File file) {

		OutputStream out = null;
		InputStream in = null;

		try {
			out = new FileOutputStream(file);
			in = filedate.getInputStream();
			int bytesRead;

			byte[] buffer = new byte[8192];

			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException fnfe) {

		} catch (IOException ioe) {
		} finally {
			close(out);
			close(in);
		}
	}

	/**
	 * 文件流
	 *
	 * @param out
	 *            写文件流
	 * @param in
	 *            读文件流
	 * @see [类、类#方法、类#成员]
	 */
	public static void close(OutputStream out) {
		try {
			if (null != out) {
				out.flush();

				out.close();
			}
		} catch (IOException io) {
		}
	}

	/**
	 * 关闭流
	 *
	 * @param in
	 *            in
	 */
	public static void close(InputStream in) {
		try {
			if (null != in) {
				in.close();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * post消息到服务器
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String postUrl(String serverUrl, String content) {

		// 如果服务url为空，直接返回空
		if (null == serverUrl || 0 == serverUrl.length()) {
			return null;
		}

		BufferedReader br = null;
		HttpURLConnection urlCon = null;
		BufferedWriter bw = null;
		String result;
		try {
			URL url = new URL(serverUrl);
			urlCon = (HttpURLConnection) url.openConnection();
			urlCon.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			urlCon.setRequestMethod("POST");
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			bw = new BufferedWriter(new OutputStreamWriter(urlCon.getOutputStream(), "utf-8"));
			bw.write(content);
			bw.flush();
			br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "utf-8"));
			result = read(br);
		} catch (Exception e) {
			throw new RuntimeException("post message to server error!url " + serverUrl, e);
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			if (null != bw) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
			if (null != urlCon) {
				try {
					urlCon.disconnect();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	/**
	 * 读响应码
	 * 
	 * @param br
	 *            输入流
	 * @return 响应码
	 * @see [类、类#方法、类#成员]
	 */
	public static String read(BufferedReader br) {
		StringBuilder buffer = new StringBuilder();
		try {
			String line = null;
			while (null != (line = br.readLine())) {
				buffer.append(line);
			}
		} catch (Exception e) {
			throw new RuntimeException("send short message error", e);
		}

		return buffer.toString();
	}

	/**
	 * 发送消息到服务器
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String sendUrl(String serverUrl) {

		String result = sendUrl(serverUrl, "utf-8");

		return result;
	}

	/**
	 * 发送消息到服务器
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String sendUrlCode(String serverUrl) {

		String result = sendUrl(serverUrl, "GBK");

		return result;
	}

	/**
	 * 发送消息到服务器
	 * 
	 * @param serverUrl
	 *            服务器地址
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String sendUrl(String serverUrl, String charsetName) {

		BufferedReader br = null;
		HttpURLConnection urlCon = null;
		String result;
		try {
			URL url = new URL(serverUrl);
			urlCon = (HttpURLConnection) url.openConnection();
			urlCon.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charsetName));
			result = read(br);
		} catch (Exception e) {
			throw new RuntimeException("send request message error", e);
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			if (null != urlCon) {
				try {
					urlCon.disconnect();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	public static String post2(String actionUrl, Map<String, String> params) {
		BasicHttpParams bp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(bp, connectionTimeOut); // 超时时间设置
		HttpConnectionParams.setSoTimeout(bp, soTimeout);
		HttpClient httpclient = new DefaultHttpClient(bp);
		HttpPost httpPost = new HttpPost(actionUrl);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
			String key = entry.getKey();
			String value = entry.getValue();
			list.add(new BasicNameValuePair(key, value));
		}
		HttpResponse httpResponse;
		String responseString = "";
		logger.warn("传入后台的参数：" + list);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			httpResponse = httpclient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				responseString = EntityUtils.toString(httpResponse.getEntity());
				return responseString;
			} else if (httpResponse.getStatusLine().getStatusCode() == 404) {
				logger.warn("actionUrl:{} not found 404!" + actionUrl);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	public static String post(String actionUrl, Map<String, Object> params) {
		BasicHttpParams bp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(bp, connectionTimeOut); // 超时时间设置
		HttpConnectionParams.setSoTimeout(bp, soTimeout);
		HttpClient httpclient = new DefaultHttpClient(bp);
		HttpPost httpPost = new HttpPost(actionUrl);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {// 构建表单字段内容
			String key = entry.getKey();
			String value = entry.getValue().toString();
			list.add(new BasicNameValuePair(key, value));
		}
		HttpResponse httpResponse;
		String responseString = "";
		logger.warn("-----HttpClientHelper4TelephoneFee-----" + list);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			httpResponse = httpclient.execute(httpPost);
			logger.warn("-----httpResponse-----" + httpResponse);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				responseString = EntityUtils.toString(httpResponse.getEntity());
				return responseString;
			} else if (httpResponse.getStatusLine().getStatusCode() == 404) {
				logger.warn("actionUrl:{} not found 404!" + actionUrl);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 生成excel文件
	 * 
	 * @param headList
	 *            excel文件头
	 * @param contentList
	 *            excel文件的内容
	 * @param fileName
	 *            目标文件名(如文件名为null，则自动生成一个文件名)
	 * @return 保存的文件名
	 */
	public static Workbook exportWithExcelFile(List<String> headList, List<List<String>> contentList) {
		if (headList == null || contentList == null) {
			return new SXSSFWorkbook(100);
		}

		Workbook workbook = new SXSSFWorkbook(100);// 最重要的就是使用SXSSFWorkbook，表示流的方式进行操作
		Sheet sheet = workbook.createSheet();

		CellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		// 设置表头
		Row writeRow = sheet.createRow(0);
		for (Integer headIndex = 0; headIndex < headList.size(); headIndex++) {
			Cell cell = writeRow.createCell(headIndex);
			cell.setCellValue(headList.get(headIndex));
			cell.setCellStyle(style);
		}

		// 写入实体数据
		for (Integer rowIndex = 0; rowIndex < contentList.size(); rowIndex++) {
			Row contentRow = sheet.createRow(rowIndex + 1);
			List<String> cList = contentList.get(rowIndex);
			for (Integer cellIndex = 0; cellIndex < cList.size(); cellIndex++) {
				Cell cell = contentRow.createCell(cellIndex);
				cell.setCellValue(cList.get(cellIndex));
				cell.setCellStyle(style);
			}
		}
		return workbook;
	}

	/**
	 * 将数据流写入文件
	 * 
	 * @param conn
	 *            从页面传过来的文件对象
	 * @param file
	 *            文件对象
	 */
	public static void streamToFile(HttpURLConnection conn, File file) throws Exception {

		OutputStream out = null;
		InputStream in = null;

		try {
			out = new FileOutputStream(file);
			in = conn.getInputStream();
			int bytesRead;

			byte[] buffer = new byte[8192];

			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} finally {
			close(out);
			close(in);
		}
	}

	/**
	 * 获取真实的IP4地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
