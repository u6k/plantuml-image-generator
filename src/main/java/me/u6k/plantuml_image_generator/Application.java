
package me.u6k.plantuml_image_generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.sourceforge.plantuml.SourceStringReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping(value = "/image.png", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateImage() throws IOException {
        byte[] image;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            String src = "@startuml\n";
            src += "Bob -> Alice : hello\n";
            src += "@enduml\n";

            SourceStringReader reader = new SourceStringReader(src);
            reader.generateImage(out); // FIXME: 戻り値を処理

            image = out.toByteArray();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");
        headers.add("X-Api-Version", "0.2.0"); // FIXME: アプリケーション設定からバージョン番号を取得

        ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);

        return resp;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
