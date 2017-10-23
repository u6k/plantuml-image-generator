
package me.u6k.plantuml_image_generator.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import me.u6k.plantuml_image_generator.model.WebRepository;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantUmlService {

    private static final Logger L = LoggerFactory.getLogger(PlantUmlService.class);

    @Autowired
    private WebRepository webRepo;

    public byte[] generate(String url, FileFormat format) throws IOException {
        /*
         * 入力チェック
         */
        L.debug("#generate: url={}, format={}", url, format);
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url is blank.");
        }
        if (format == null) {
            throw new IllegalArgumentException("format is null.");
        }

        /*
         * URLからデータを取得
         */
        String umlText = this.webRepo.getUrl(url);

        /*
         * データからPlantUML画像を生成
         */
        byte[] image;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            L.debug("plantuml image generate start:");
            SourceStringReader reader = new SourceStringReader(umlText);
            reader.generateImage(out, new FileFormatOption(format)); // FIXME: 戻り値を処理
            L.debug("plantuml image generate end:");

            image = out.toByteArray();
            L.debug("image.length={}", image.length);
        }

        return image;
    }

}
