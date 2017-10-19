
package me.u6k.plantuml_image_generator.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import me.u6k.plantuml_image_generator.exception.ReadWebResourceException;
import me.u6k.plantuml_image_generator.service.PlantUmlService;
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

    @MockBean
    private PlantUmlService service;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    private MockMvc mvc;

    @Test
    public void generate_image() throws Exception {
        String url = "http://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa70000";

        String expectedUmlPath = "/service/PlantUmlServiceTest/simple_uml.png";
        byte[] expectedUmlData = IOUtils.toByteArray(this.getClass().getResourceAsStream(expectedUmlPath));
        given(this.service.generate(url)).willReturn(expectedUmlData);

        ResultActions result = this.mvc.perform(get("/image.png").param("url", url));

        result.andExpect(status().isOk())
            .andExpect(header().string("X-Api-Version", this.appVersion))
            .andExpect(header().string("Content-Type", "image/png"))
            .andExpect(content().bytes(expectedUmlData));
    }

    @Test
    public void service_throw_illegalArgumentException() throws Exception {
        given(this.service.generate(""))
            .willThrow(new IllegalArgumentException("url is blank."));

        ResultActions result = this.mvc.perform(get("/image.png").param("url", ""));

        result.andExpect(status().isBadRequest())
            .andExpect(header().string("X-Api-Version", this.appVersion))
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(content().string("{\"code\":\"java.lang.IllegalArgumentException\",\"message\":\"url is blank.\"}"));
    }

    @Test
    public void service_throw_readWebResourceException() throws Exception {
        String url = "http://httpbin.org/delay/20";

        given(this.service.generate(url))
            .willThrow(new ReadWebResourceException("timeout")); // FIXME

        ResultActions result = this.mvc.perform(get("/image.png").param("url", url));

        result.andExpect(status().isInternalServerError())
            .andExpect(header().string("X-Api-Version", this.appVersion))
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(content().string("{\"code\":\"me.u6k.plantuml_image_generator.exception.ReadWebResourceException\",\"message\":\"timeout\"}"));
    }

}
