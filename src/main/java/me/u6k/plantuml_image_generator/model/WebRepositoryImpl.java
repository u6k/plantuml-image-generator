
package me.u6k.plantuml_image_generator.model;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class WebRepositoryImpl implements WebRepository {

    private static final Logger L = LoggerFactory.getLogger(WebRepositoryImpl.class);

    @Override
    public String getUrl(String url) {
        L.debug("#getUrl: url={}", url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            // TODO: タイムアウトを設定
            // TODO: User-Agentを設定
            // TODO: キャッシュを活用
            L.debug("request: url={}", url);
            try (CloseableHttpResponse httpResp = httpClient.execute(httpGet)) {
                L.debug("response: status={}", httpResp.getStatusLine().getStatusCode());
                if (httpResp.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                    throw new RuntimeException("url=" + url + ", statusCode=" + httpResp.getStatusLine().getStatusCode());
                }
                // TODO: リダイレクトなど300系は？

                String responseText = EntityUtils.toString(httpResp.getEntity());
                L.debug("response: text.length={}", responseText.length());

                return responseText;
            }
        } catch (IOException e) {
            // TODO: 例外を整理する。
            throw new RuntimeException(e);
        }
    }

}
