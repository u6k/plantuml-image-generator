
package me.u6k.plantuml_image_generator.controller;

import java.io.IOException;

import me.u6k.plantuml_image_generator.service.PlantUmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageRestController {

    private static final Logger L = LoggerFactory.getLogger(ImageRestController.class);

    @Autowired
    private PlantUmlService plantUmlService;

    @Value("${info.app.version}")
    private String appVersion;

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateImage(@RequestParam String url) throws IOException {
        L.debug("#generateImage: url={}", url);

        byte[] image = this.plantUmlService.generate(url);

        L.debug("build response data start:");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");
        headers.add("X-Api-Version", this.appVersion);

        ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
        L.debug("build response data end: length={}", resp.getBody().length);

        return resp;
    }

}
