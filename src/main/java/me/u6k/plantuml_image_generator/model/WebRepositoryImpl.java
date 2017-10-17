
package me.u6k.plantuml_image_generator.model;

import java.io.IOException;

import me.u6k.plantuml_image_generator.exception.ReadWebResourceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class WebRepositoryImpl implements WebRepository {

    private static final int TIMEOUT_LENGTH = 10000;

    private static final Logger L = LoggerFactory.getLogger(WebRepositoryImpl.class);

    @Value("${info.app.groupId}")
    private String appGroupId;

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.version}")
    private String appVersion;

    @Override
    public String getUrl(String url) {
        L.debug("#getUrl: url={}", url);

        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url is blank.");
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);

            RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT_LENGTH)
                .setSocketTimeout(TIMEOUT_LENGTH)
                .build();
            httpGet.setConfig(requestConfig);
            L.debug("set config: timeout={}", TIMEOUT_LENGTH);

            String userAgent = this.appGroupId + ":" + this.appName + "/" + this.appVersion;
            httpGet.addHeader("User-Agent", userAgent);
            L.debug("set config: user-agent={}", userAgent);

            // TODO: キャッシュを活用

            L.debug("request: url={}", url);
            try (CloseableHttpResponse httpResp = httpClient.execute(httpGet)) {
                L.debug("response: status={}", httpResp.getStatusLine().getStatusCode());
                if (httpResp.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                    throw new ReadWebResourceException("url=" + url + ", statusCode=" + httpResp.getStatusLine().getStatusCode());
                }

                String responseText = EntityUtils.toString(httpResp.getEntity());
                L.debug("response: text.length={}", responseText.length());

                return responseText;
            }
        } catch (IOException e) {
            throw new ReadWebResourceException(e);
        }
    }

}
