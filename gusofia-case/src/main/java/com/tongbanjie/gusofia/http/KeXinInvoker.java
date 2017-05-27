package com.tongbanjie.gusofia.http;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class KeXinInvoker {

    private static final Log log = LogFactory.getLog(KeXinInvoker.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;

    public static void init() {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(501);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(5000);
        // 设置读取超时
        configBuilder.setSocketTimeout(5000);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(2000);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    public static String invoke(Map params, String apiUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet(apiUrl);
        CloseableHttpResponse response = null;
        HttpEntity entity;
        try {
            httpGet.setConfig(requestConfig);
            StringEntity se = new StringEntity(JSON.toJSONString(params), "UTF-8");
            httpGet.addHeader(HTTP.CONTENT_TYPE, "application/json");
            se.setContentType("text/json");
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                log.warn("[BqsApiInvoker] invoke failed, response status: " + statusCode);
                return null;
            }
            entity = response.getEntity();
            if (entity == null) {
                log.warn("[BqsApiInvoker] invoke failed, response output is null!");
                return null;
            }
            String result = EntityUtils.toString(entity, "utf-8");
            return result;
        } catch (Exception e) {
            log.error("[BqsApiInvoker] invoke throw exception, details: ", e);
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return null;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            log.error(e.getMessage(), e);
        }
        return sslsf;
    }

    public static void main(String[] args) {
        KeXinInvoker.init();
        StringBuilder apiUrl = new StringBuilder();
        String uri = "https://api.creditx.com/perRisk_creditloan_xScore";
        apiUrl.append(uri).append("?");
        apiUrl.append("user_name").append("=").append("顾利东&");
        apiUrl.append("mobile_number").append("=").append("18258112331&");
        apiUrl.append("identity_number").append("=").append("330184198912131333");
        System.out.println(apiUrl);
        try {
            String result = KeXinInvoker.invoke(null, apiUrl.toString());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}