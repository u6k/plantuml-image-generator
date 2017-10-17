
package me.u6k.plantuml_image_generator.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;

import me.u6k.plantuml_image_generator.exception.ReadWebResourceException;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebRepositoryImplTest {

    @Value("${info.app.version}")
    private String appVersion;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private WebRepository webRepo;

    @Test
    public void get_ok() throws IOException {
        String url = "https://gist.githubusercontent.com/u6k/bd978c6af1d040d06aca8820fc178fe1/raw/42862b4b373e7a67c0a3ca11b9b8dd59fc83c000/criminal_jc.plantuml";
        String actualData = this.webRepo.getUrl(url);

        String path = "/model/WebRepositoryImplTest/criminal_jc.plantuml";
        String expectedData = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");

        assertThat(expectedData, is(actualData));
    }

    @Test
    public void get_redirect() throws IOException {
        String url = "https://goo.gl/UoQLcf";
        String actualData = this.webRepo.getUrl(url);

        String path = "/model/WebRepositoryImplTest/criminal_jc.plantuml";
        String expectedData = IOUtils.toString(this.getClass().getResourceAsStream(path), "UTF-8");

        assertThat(expectedData, is(actualData));
    }

    @Test
    public void get_not_found() {
        String url = "https://gist.github.com/u6k/aaa/bbb";

        this.thrown.expect(ReadWebResourceException.class);
        this.thrown.expectMessage("url=https://gist.github.com/u6k/aaa/bbb, statusCode=404");
        this.webRepo.getUrl(url);
    }

    @Test
    public void get_internal_server_error() {
        String url = "http://ozuma.sakura.ne.jp/httpstatus/500";

        this.thrown.expect(ReadWebResourceException.class);
        this.thrown.expectMessage("url=http://ozuma.sakura.ne.jp/httpstatus/500, statusCode=500");
        this.webRepo.getUrl(url);
    }

    @Test
    public void get_url_blank_1() {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("url is blank.");
        this.webRepo.getUrl(null);
    }

    @Test
    public void get_url_blank_2() {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("url is blank.");
        this.webRepo.getUrl("");
    }

    @Test
    public void get_url_blank_3() {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("url is blank.");
        this.webRepo.getUrl(" ");
    }

    @Test
    public void get_invalid_url() {
        String url = "aaa:bbb:ccc:ddd:eee";

        this.thrown.expect(ReadWebResourceException.class);
        this.thrown.expectMessage(""); // FIXME
        this.webRepo.getUrl(url);
    }

    @Test
    public void get_timeout() {
        String url = "http://httpbin.org/delay/20";

        this.thrown.expect(ReadWebResourceException.class);
        this.thrown.expectMessage(""); // FIXME
        this.webRepo.getUrl(url);
    }

    @Test
    public void get_user_header_check() {
        String url = "http://httpbin.org/get";
        String data = this.webRepo.getUrl(url);

        String expectedUserAgent = "\"User-Agent\": \"me.u6k.plantuml-image-generator:plantuml-image-generator/" + this.appVersion + "\"";
        assertThat(data.indexOf(expectedUserAgent), not(-1));
    }

}
