package com.javagpt.back.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

/**
 * ES单节点配置文件
 */
@Configuration
public class ESConfig /**extends ElasticsearchConfiguration**/ {

    @Value("${spring.es.host}")
    private String host;

    @Value("${spring.es.port}")
    private Integer port;

    @Value("${spring.elasticsearch.uris}")
    private List<String> uris;

    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

//    @Bean
//    public ElasticsearchClient elasticsearchClient() {
//        // 创建低级客户端
//        RestClient restClient = RestClient.builder(new HttpHost(host, port)).build();
//        // 使用Jackson映射器创建传输层
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        // 创建API客户端
//        return new ElasticsearchClient(transport);
//    }
//
//    @Bean
//    public ElasticsearchClient client() throws Exception {
//
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        //设置账号密码
//        credentialsProvider.setCredentials(
//                AuthScope.ANY, new UsernamePasswordCredentials("elastic", "HYnyGbJ7LOXVUN*8XByl"));
//
//        final SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
//        final MyHostnameVerifier hostnameVerifier = new MyHostnameVerifier();
//        RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200, "https"))
//                .setHttpClientConfigCallback(httpClientBuilder->httpClientBuilder
//                        .setDefaultCredentialsProvider(credentialsProvider)
//                        .setSSLContext(sslContext)
//                        .setSSLHostnameVerifier(hostnameVerifier)
//                ).build();
//        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
//        return new ElasticsearchClient(transport);
//    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")
        ).build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );

        return new ElasticsearchClient(transport);
    }


    /**
     * 重写HostnameVerifier接口
     */
    private static class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        // 去除http开头
//        uris = uris.stream().map(uri -> {
//            if (uri.startsWith("http")) {
//                uri = uri.split("://")[1];
//            }
//            return uri;
//        }).toList();
//        return ClientConfiguration.builder()
//                .connectedTo(uris.toArray(new String[uris.size()]))
//                .build();
//    }
//
//    @Bean
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        // 注入自定义转换器处理LocalDateTime数据
//        List<Converter<?, ?>> converters = new ArrayList<>(16);
//        converters.add(StringToLocalDateTimeConverter.INSTANCE);
//        converters.add(LocalDateTimeToStringConverter.INSTANCE);
//        return new ElasticsearchCustomConversions(converters);
//    }
//
//    /**
//     * LocalDateTime转String
//     */
//    @WritingConverter
//    private enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
//        /**
//         * 实例化
//         */
//        INSTANCE;
//
//        @Override
//        public String convert(LocalDateTime source) {
//            return source.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
//        }
//    }
//
//    /**
//     * String转LocalDateTime
//     */
//    @ReadingConverter
//    private enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
//        /**
//         * 实例化
//         */
//        INSTANCE;
//
//        @Override
//        public LocalDateTime convert(String source) {
//            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
//        }
//    }
}