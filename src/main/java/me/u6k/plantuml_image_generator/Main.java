
package me.u6k.plantuml_image_generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import net.sourceforge.plantuml.SourceStringReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Main {

    private static final Logger L = LoggerFactory.getLogger(Main.class);

    @Value("${info.app.version}")
    private String appVersion;

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateImage(@RequestParam String url) throws IOException {
        L.debug("#generateImage: url={}", url);
        if (url == null) {
            throw new IllegalArgumentException("url is null.");
        }

        url = URLDecoder.decode(url, "UTF-8");
        L.debug("url decoded: url={}", url);

        String umlText;

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

                umlText = EntityUtils.toString(httpResp.getEntity());
                L.debug("response: text.length={}", umlText.length());
            }
        }

        byte[] image;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            L.debug("plantuml image generate start:");
            SourceStringReader reader = new SourceStringReader(umlText);
            reader.generateImage(out); // FIXME: 戻り値を処理
            L.debug("plantuml image generate end:");

            image = out.toByteArray();
            L.debug("image.length={}", image.length);
        }

        L.debug("build response data start:");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");
        headers.add("X-Api-Version", this.appVersion);

        ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
        L.debug("build response data end: length={}", resp.getBody().length);

        return resp;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
