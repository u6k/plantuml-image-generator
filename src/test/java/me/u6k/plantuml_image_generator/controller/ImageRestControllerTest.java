
package me.u6k.plantuml_image_generator.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import me.u6k.plantuml_image_generator.exception.ReadWebResourceException;
import me.u6k.plantuml_image_generator.service.PlantUmlService;
import net.sourceforge.plantuml.FileFormat;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ImageRestControllerTest {

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlantUmlService service;

    @Test
    public void 画像を生成() throws Exception {
        // モックを設定
        String url = "http://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa70000";
        byte[] expectedContent = IOUtils.toByteArray(this.getClass().getResourceAsStream("/service/PlantUmlServiceTest/simple_uml.png"));
        given(this.service.generate(url, FileFormat.PNG)).willReturn(expectedContent);

        // テストを実行
        ResultActions result = this.mvc.perform(get("/images?url=http%3a%2f%2fwww%2eplantuml%2ecom%2fplantuml%2fuml%2fSyfFKj2rKt3CoKnELR1Io4ZDoSa70000"));

        // テスト結果を確認
        result.andExpect(status().isOk())
            .andExpect(header().string("X-Api-Version", this.appVersion))
            .andExpect(header().string("Content-Type", "image/png"))
            .andExpect(content().bytes(expectedContent));
    }

    @Test
    public void 不正な引数_urlが空文字列() throws Exception {
        // モックを設定
        given(this.service.generate("", FileFormat.PNG)).willThrow(new IllegalArgumentException("encodedUrl is blank."));

        // テストを実行
        ResultActions result = this.mvc.perform(get("/images?url="));

        // テスト結果を確認
        result.andExpect(status().isBadRequest())
            .andExpect(header().string("X-Api-Version", this.appVersion))
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(content().string("{\"code\":\"java.lang.IllegalArgumentException\",\"message\":\"encodedUrl is blank.\"}"));
    }

    @Test
    public void タイムアウト() throws Exception {
        // モックを設定
        given(this.service.generate("http://httpbin.org/delay/20", FileFormat.PNG)).willThrow(new ReadWebResourceException("java.net.SocketTimeoutException: Read timed out"));

        // テストを実行
        ResultActions result = this.mvc.perform(get("/images?url=http%3a%2f%2fhttpbin%2eorg%2fdelay%2f20"));

        // テスト結果を確認
        result.andExpect(status().isInternalServerError())
            .andExpect(header().string("X-Api-Version", this.appVersion))
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(content().string("{\"code\":\"me.u6k.plantuml_image_generator.exception.ReadWebResourceException\",\"message\":\"java.net.SocketTimeoutException: Read timed out\"}"));
    }

}
