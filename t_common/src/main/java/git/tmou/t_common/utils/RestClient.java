package git.tmou.t_common.utils;


import git.tmou.t_common.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RestClient {

	private static volatile RestClient instance = new RestClient();

	 /** 从连接池获取连接的超时 */
//	 @Value(value = "${connection.request.timeout:3000}")
	 private static final int CONNECTION_REQUEST_TIMEOUT = 3000;

	/** 读超时 - 20s */
//	@Value(value = "${connection.read.timeout:10000}")
	 private static final int READ_TIMEOUT = 30 * 1000;

	/** 建立连接的超时 */
//	@Value(value = "${connection.timeout:3000}")
	private static final int CONNECTION_TIMEOUT = 3000;

	public static RestClient get() {
		return instance;
	}

	private RestTemplate restTemplate = null;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	private RestClient() {
		this.restTemplate = acceptsUntrustedCertsRestTemplate();
	}

	private Log log = LogFactory.getLog(getClass());

	private final String LogTemplete = "Method: %s, URL: %s, Params: %s";

	public String getReqResult(String url, String json) throws Exception {
		log.debug(String.format(LogTemplete, "getReqResult", url, json));
		return restTemplate.getForObject(url, String.class, json);
	}

//	public String postReqResult(String url, Map<String, Object> params)
//			throws Exception {
//		System.out.println("url=" + url);
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//		ObjectMapper mapper = new ObjectMapper();
//		String json =mapper.writeValueAsString(params);
//		return restTemplate.postForObject(url, json, String.class);
//	}

	/**
	 * POST请求
	 * @param url
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> postForEntity(String url, Map<String, Object> values) {
		// 响应
		return postForEntity(url, values, Map.class);
	}

	/**
	 * POST请求 使用body发送请求
	 * @param url
	 * @param data
	 * @param clazz 响应类型
	 * @return
	 */
	public <T> T postForEntity(String url, Map<String, Object> data, Class<T> clazz) {
		log.debug(String.format(LogTemplete, "postForEntity", url, data.toString()));
        HttpHeaders headers =  new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE));

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			map.add(entry.getKey(), entry.getValue());
		}
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		ResponseEntity<T> response = restTemplate.postForEntity(url, request, clazz);
		return response == null ? null : response.getBody();
	}

	/**
	 * POST请求 使用body发送请求
	 * @param url
	 * @param data
	 * @param clazz 响应类型
	 * @return
	 */
	public <T> T postForEntityTypeJSON(String url, Object data, Class<T> clazz) {
		log.debug(String.format(LogTemplete, "postForEntity", url, data.toString()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		HttpEntity<Object> request = new HttpEntity<Object>(data, headers);
		ResponseEntity<T> response = restTemplate.postForEntity(url, request, clazz);
		return response.getBody();
	}

//	public Map<String, Object> postForParams(String url, Map<String, Object> params) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE));
//
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//
//		for (Map.Entry<String, Object> entry : params.entrySet()) {
//			map.add(entry.getKey(), entry.getValue().toString());
//		}
//
//		String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();
//		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(headers);
//		@SuppressWarnings("rawtypes")
//		ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
//		return response.getBody();
//	}

	/**
	 * GET请求
	 * @param url
	 * @param values
	 * @return
	 */
	public <T> T getForEntity(String url, Map<String, Object> values, Class<T> clazz) {
		log.debug(String.format(LogTemplete, "getForEntity", url, values.toString()));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			map.add(entry.getKey(), entry.getValue().toString());
		}
		String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();

		ResponseEntity<T> response = restTemplate.getForEntity(newUrl, clazz);
		return response.getBody();
	}
	/**
	 * GET请求
	 * @param url
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getForEntity(String url, Map<String, Object> values) {
		return getForEntity(url, values, Map.class);
	}
	/**
	 * PUT请求
	 * @param url
	 * @param values
	 * @param clazz
	 * @return
	 */
	public <T> T putForEntity(String url, Map<String, Object> values, Class<T> clazz) {
		log.debug(String.format(LogTemplete, "putForEntity", url, values.toString()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			map.add(entry.getKey(), entry.getValue().toString());
		}
		String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();
//		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
		ResponseEntity<T> response = restTemplate.exchange(newUrl, HttpMethod.PUT, null, clazz);
//		restTemplate.p
		return response.getBody();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> putForEntity(String url, Map<String, Object> values) {
		return getForEntity(url, values, Map.class);
	}
	/**
	 * 根据map创建get参数，暂不使用
	 * @param map
	 * @return
	 */
//	private String buildGetParameters(Map<String, String> map) {
//		StringBuilder sb = new StringBuilder();
//		String value = "";
//		sb.append("?");
//		if (map.size() > 0) {
//			for (Map.Entry<String, String> entry : map.entrySet()) {
//				if (StringUtils.isEmpty(entry.getValue())) {
//					sb.append(entry.getKey() + "=&");
//				} else {
//					try {
//						value = URLEncoder.encode(entry.getValue(), "UTF-8");
//					} catch(UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
//					sb.append(entry.getKey() + "=" + value + "&");
//				}
//			}
//		}
//		return sb.toString();
//	}

	/**
	 * DELETE请求
	 * @param url
	 * @param values
	 * @return
	 */
	public <T> T deleteForEntity(String url, Map<String, Object> values, Class<T> clazz) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		log.debug(String.format(LogTemplete, "deleteForEntity", url, values.toString()));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			map.add(entry.getKey(), entry.getValue().toString());
		}
		String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();
		ResponseEntity<T> response = restTemplate.exchange(newUrl, HttpMethod.DELETE, null, clazz);
		return response.getBody();
	}


	/**
	 * 上传文件，返回结构：status=0为成功
	 * {"status":"","message":""}
	 * @param url
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public Response upload(String url, File file, String key, Map<String, Object> data) {
		log.debug(String.format(LogTemplete, "upload", url, data.toString()));
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			FileSystemResource fileSystemResource = new FileSystemResource(file);
			MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				form.add(entry.getKey(), entry.getValue());
			}
			form.add(key, fileSystemResource);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(form, headers);
			ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Response.class);
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 去除文件中的重复行并上传，返回结构：status=0为成功
	 * {"status":"","message":""}
	 * @param url
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public Response uploadDistinctLinesOfFile(String url, File file, String key, Map<String, Object> data) {
		log.debug(String.format(LogTemplete, "upload", url, data.toString()));
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			ByteArrayResource fileSystemResource = new ByteArrayResource(Files.lines(file.toPath()).distinct().map(s->(s+"\n").getBytes(Charset.forName("utf8"))).reduce(ArrayUtils::addAll).orElse(new byte[]{})){
				@Override
				public String getFilename() {
					return file.getName();
				}
			};
			MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				form.add(entry.getKey(), entry.getValue());
			}
		    form.add(key, fileSystemResource);
		    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(form, headers);
			ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Response.class);
		    return response.getBody();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 下载文件
	 * @param url
	 * @param values
	 * @param parent 下载的父目录
	 * @return File 下载后的文件，可能返回NULL
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> download(String url, Map<String, Object> values, String parent) {

//		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		log.debug(String.format(LogTemplete, "download", url, values.toString()));
		FileOutputStream outputStream = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			for (Map.Entry<String, Object> entry : values.entrySet()) {
				map.add(entry.getKey(), entry.getValue().toString());
			}
			String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();

			HttpEntity<String> entity = new HttpEntity<String>(headers);
			// 需要自定义ByteArrayHttpMessageConverter 以实现stream读取
			@SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.exchange(
					newUrl, HttpMethod.GET, entity, Map.class);

			if(response.getStatusCode() != HttpStatus.OK) {
				return null;
			}
			String headerValue = response.getHeaders().getFirst("Content-Disposition");
			if (headerValue == null) {
				return null;
			}
			String attachment = parseFileName(headerValue);// head 中带回来的文件名称
			File file = new File(parent, currentTimeMillis() + "." + attachment); // 避免重复
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

			outputStream = new FileOutputStream(file);
			Map<String, Object> tmpMap = response.getBody();
			if(response == null || tmpMap == null) {
				return null;
			}
			Object fileObj = tmpMap.get("file");
			if(fileObj == null || outputStream == null) {
				return null;
			}
			IOUtils.write((byte[])fileObj, outputStream);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

	public Resource download(String url, Map<String, Object> params) {
		log.debug(String.format(LogTemplete, "download & params", url, params.toString()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			map.add(entry.getKey(), entry.getValue().toString());
		}
		String newUrl = UriComponentsBuilder.fromHttpUrl(url).queryParams(map).build().toUriString();

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<Resource> response = restTemplate.exchange(
				newUrl, HttpMethod.GET, entity, Resource.class);
		if (response.getStatusCode() != HttpStatus.OK) {
			return null;
		}
		return response.getBody();
	}

	public RestTemplate acceptsUntrustedCertsRestTemplate() {
		HttpClientBuilder b = HttpClientBuilder.create();

		// setup a Trust Strategy that allows all certificates.
		//
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			}).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		if (sslContext == null) {
			return new RestTemplate();
		}
		b.setSSLContext(sslContext);

		// don't check Hostnames, either.
		// -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't
		// want to weaken
		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

		// here's the special part:
		// -- need to create an SSL Socket Factory, to use our weakened "trust
		// strategy";
		// -- and create a Registry, to register it.
		//
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
		        .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
		        .build();

		// now, we create connection-manager using our Registry.
		// -- allows multi-threaded use
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connMgr.setMaxTotal(200);
		connMgr.setDefaultMaxPerRoute(100);

		b.setConnectionManager(connMgr);
//		b.setConnectionTimeToLive()

		// finally, build the HttpClient;
		// -- done!
		CloseableHttpClient client = b.build();

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
		        client);
		// 从连接池获取连接的超时
		clientHttpRequestFactory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
		// 客户端从服务端读时的超时
		clientHttpRequestFactory.setReadTimeout(READ_TIMEOUT);
		// 客户端从服务端建立连接的超时
		clientHttpRequestFactory.setConnectTimeout(CONNECTION_TIMEOUT);

		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		return restTemplate;
	}

	public static synchronized long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * Parse a {@literal Content-Disposition} header value as defined in RFC 2183.
	 * @param contentDisposition the {@literal Content-Disposition} header value
	 * @return the parsed content disposition
	 * @see #toString()
	 */
	private String parseFileName(String contentDisposition) {
		List<String> parts = tokenize(contentDisposition);
		//String type = parts.get(0);
		//String name = null;
		String filename = null;
		//Charset charset = null;
		//Long size = null;
		for (int i = 1; i < parts.size(); i++) {
			String part = parts.get(i);
			int eqIndex = part.indexOf('=');
			if (eqIndex != -1) {
				String attribute = part.substring(0, eqIndex);
				String value = (part.startsWith("\"", eqIndex + 1) && part.endsWith("\"") ?
						part.substring(eqIndex + 2, part.length() - 1) :
						part.substring(eqIndex + 1, part.length()));
				if (attribute.equals("name") ) {
					//name = value;
				}
				else if (attribute.equals("filename*") ) {
					filename = decodeHeaderFieldParam(value);
					//charset = Charset.forName(value.substring(0, value.indexOf('\'')));
				}
				else if (attribute.equals("filename") && (filename == null)) {
					filename = value;
				}
				else if (attribute.equals("size") ) {
					//size = Long.parseLong(value);
				}
			}
			else {
				throw new IllegalArgumentException("Invalid content disposition format");
			}
		}
		return filename;
	}

	private List<String> tokenize(String headerValue) {
		int index = headerValue.indexOf(59);
		String type = (index >= 0 ? headerValue.substring(0, index) : headerValue).trim();
		if (type.isEmpty()) {
			throw new IllegalArgumentException("Content-Disposition header must not be empty");
		} else {
			List<String> parts = new ArrayList<String>();
			parts.add(type);
			int nextIndex;
			if (index >= 0) {
				do {
					nextIndex = index + 1;

					for (boolean quoted = false; nextIndex < headerValue.length(); ++nextIndex) {
						char part = headerValue.charAt(nextIndex);
						if (part == 59) {
							if (!quoted) {
								break;
							}
						} else if (part == 34) {
							quoted = !quoted;
						}
					}

					String arg6 = headerValue.substring(index + 1, nextIndex).trim();
					if (!arg6.isEmpty()) {
						parts.add(arg6);
					}

					index = nextIndex;
				} while (nextIndex < headerValue.length());
			}

			return parts;
		}
	}

	/**
	 * Decode the given header field param as describe in RFC 5987.
	 * <p>Only the US-ASCII, UTF-8 and ISO-8859-1 charsets are supported.
	 * @param input the header field param
	 * @return the encoded header field param
	 * @see <a href="https://tools.ietf.org/html/rfc5987">RFC 5987</a>
	 */
	private String decodeHeaderFieldParam(String input) {
		int firstQuoteIndex = input.indexOf('\'');
		int secondQuoteIndex = input.indexOf('\'', firstQuoteIndex + 1);
		// US_ASCII
		if (firstQuoteIndex == -1 || secondQuoteIndex == -1) {
			return input;
		}
		Charset charset = Charset.forName(input.substring(0, firstQuoteIndex));
		byte[] value = input.substring(secondQuoteIndex + 1, input.length()).getBytes(charset);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int index = 0;
		while (index < value.length) {
			byte b = value[index];
			if (isRFC5987AttrChar(b)) {
				bos.write((char) b);
				index++;
			}
			else if (b == '%') {
				char[] array = { (char)value[index + 1], (char)value[index + 2]};
				bos.write(Integer.parseInt(String.valueOf(array), 16));
				index+=3;
			}
			else {
				throw new IllegalArgumentException("Invalid header field parameter format (as defined in RFC 5987)");
			}
		}
		return new String(bos.toByteArray(), charset);
	}

	private boolean isRFC5987AttrChar(byte c) {
		return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ||
				c == '!' || c == '#' || c == '$' || c == '&' || c == '+' || c == '-' ||
				c == '.' || c == '^' || c == '_' || c == '`' || c == '|' || c == '~';
	}
}
