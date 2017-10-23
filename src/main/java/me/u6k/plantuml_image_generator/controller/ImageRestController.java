
package me.u6k.plantuml_image_generator.controller;

import java.io.IOException;
import java.net.URLDecoder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import me.u6k.plantuml_image_generator.service.PlantUmlService;
import net.sourceforge.plantuml.FileFormat;
import org.apache.commons.lang3.StringUtils;
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
@Api
public class ImageRestController {

    private static final Logger L = LoggerFactory.getLogger(ImageRestController.class);

    @Autowired
    private PlantUmlService plantUmlService;

    @Value("${info.app.version}")
    private String appVersion;

    @ApiOperation(value = "Generate PlantUML Image(png or svg)", protocols = "https", produces = "image/png,image/svg+xml", responseHeaders = { @ResponseHeader(name = "X-Api-Version", description = "APIのバージョン番号") })
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "パラメータが不正な場合"),
        @ApiResponse(code = 500, message = "PlantUML文書の取得に失敗した場合")
    })
    @RequestMapping(value = "/images", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateImage(
        @RequestParam(name = "url", required = true) //
        @ApiParam(name = "url", required = true, value = "PlantUML文書が存在するURL。URLエンコードしてください。", example = "http%3A%2F%2Fwww.plantuml.com%2Fplantuml%2Fuml%2FSyfFKj2rKt3CoKnELR1Io4ZDoSa70000") //
        String encodedUrl,
        @RequestParam(name = "format", required = false) //
        @ApiParam(name = "format", required = false, value = "画像形式。\"png\"または\"svg\"。", defaultValue = "png", example = "svg") //
        String format) throws IOException {
        L.debug("#generateImage: encodedUrl={}, format={}", encodedUrl, format);

        /*
         * 入力チェック
         */
        if (StringUtils.isBlank(encodedUrl)) {
            throw new IllegalArgumentException("encodedUrl is blank.");
        }

        FileFormat fileFormat;
        if (StringUtils.isBlank(format)) {
            fileFormat = FileFormat.PNG;
        } else if (StringUtils.equalsIgnoreCase(format, "png")) {
            fileFormat = FileFormat.PNG;
        } else if (StringUtils.equalsIgnoreCase(format, "svg")) {
            fileFormat = FileFormat.SVG;
        } else {
            throw new IllegalArgumentException("\"" + format + "\" is unknown format.");
        }

        /*
         * URLから画像データを作成
         */
        String url = URLDecoder.decode(encodedUrl, "UTF-8");
        L.debug("url decoded: {}", url);

        byte[] image = this.plantUmlService.generate(url, fileFormat);

        /*
         * レスポンスを構築
         */
        L.debug("build response data start:");
        HttpHeaders headers = new HttpHeaders();
        switch (fileFormat) {
            case PNG:
                headers.add("Content-Type", "image/png");
                break;
            case SVG:
                headers.add("Content-Type", "image/svg+xml");
                break;
            default:
                throw new IllegalArgumentException("\"" + format + "\" is unknown format.");
        }
        headers.add("X-Api-Version", this.appVersion);

        ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
        L.debug("build response data end: length={}", image.length);

        return resp;
    }

}
