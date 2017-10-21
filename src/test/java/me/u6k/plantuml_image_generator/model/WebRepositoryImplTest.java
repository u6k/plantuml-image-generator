
package me.u6k.plantuml_image_generator.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import me.u6k.plantuml_image_generator.exception.ReadWebResourceException;
import org.apache.commons.io.IOUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

@RunWith(Enclosed.class)
public class WebRepositoryImplTest {

    @RunWith(Parameterized.class)
    @SpringBootTest
    public static class get_ok {

        @ClassRule
        public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

        @Rule
        public final SpringMethodRule springMethodRule = new SpringMethodRule();

        @Autowired
        private WebRepository webRepo;

        @Parameters(name = "{0}")
        public static Collection<Object[]> parameters() {
            return Arrays.asList(new Object[][] {
                { "データを取得する",
                    "https://gist.githubusercontent.com/u6k/bd978c6af1d040d06aca8820fc178fe1/raw/42862b4b373e7a67c0a3ca11b9b8dd59fc83c000/criminal_jc.plantuml",
                    "/model/WebRepositoryImplTest/criminal_jc.plantuml" },
                { "リダイレクトでも取得できる",
                    "https://goo.gl/UoQLcf",
                    "/model/WebRepositoryImplTest/criminal_jc.plantuml" }
            });
        }

        @Parameter(0)
        public String testName;

        @Parameter(1)
        public String url;

        @Parameter(2)
        public String expectedDataPath;

        @Test
        public void test() throws Exception {
            // テストを実行
            String actualData = this.webRepo.getUrl(this.url);

            // テスト結果を確認
            String expectedData = IOUtils.toString(this.getClass().getResourceAsStream(this.expectedDataPath), "UTF-8");

            assertThat(actualData, is(expectedData));
        }

    }

    @RunWith(Parameterized.class)
    @SpringBootTest
    public static class get_error {

        @ClassRule
        public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

        @Rule
        public final SpringMethodRule springMethodRule = new SpringMethodRule();

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Autowired
        private WebRepository webRepo;

        @Parameters(name = "{0}")
        public static Collection<Object[]> parameters() {
            return Arrays.asList(new Object[][] {
                {
                    "404の場合、ReadWebResourceException例外をスロー",
                    "https://gist.github.com/u6k/aaa/bbb",
                    ReadWebResourceException.class,
                    "url=https://gist.github.com/u6k/aaa/bbb, statusCode=404"
                },
                {
                    "500の場合、ReadWebResourceException例外をスロー",
                    "http://ozuma.sakura.ne.jp/httpstatus/500",
                    ReadWebResourceException.class,
                    "url=http://ozuma.sakura.ne.jp/httpstatus/500, statusCode=500"
                },
                {
                    "url引数がnullの場合、IllegalArgumentException例外をスロー",
                    null,
                    IllegalArgumentException.class,
                    "url is blank."
                },
                {
                    "url引数が空文字列の場合、IllegalArgumentException例外をスロー",
                    "",
                    IllegalArgumentException.class,
                    "url is blank."
                },
                {
                    "url引数が\" \"の場合、IllegalArgumentException例外をスロー",
                    " ",
                    IllegalArgumentException.class,
                    "url is blank."
                },
                {
                    "不正なURLの場合はURLの解釈に失敗するため、ReadWebResourceException例外をスロー",
                    "aaa:bbb:ccc:ddd:eee",
                    ReadWebResourceException.class,
                    "" // FIXME
                },
                {
                    "タイムアウトの場合、ReadWebResourceException例外をスロー",
                    "http://httpbin.org/delay/20",
                    ReadWebResourceException.class,
                    "" // FIXME
                },
            });
        }

        @Parameter(0)
        public String testName;

        @Parameter(1)
        public String url;

        @Parameter(2)
        public Class<? extends Throwable> expectedExceptionClass;

        @Parameter(3)
        public String expectedExceptionMessage;

        @Test
        public void test() throws Exception {
            // テストを実行(テスト結果として例外を期待)
            this.thrown.expect(this.expectedExceptionClass);
            this.thrown.expectMessage(this.expectedExceptionMessage);

            this.webRepo.getUrl(this.url);
        }

    }

    @RunWith(SpringRunner.class)
    @SpringBootTest
    public static class get_check_user_agent {

        @Value("${info.app.version}")
        private String appVersion;

        @Autowired
        private WebRepository webRepo;

        @Test
        public void get_user_header_check() {
            // テストを実行
            String url = "http://httpbin.org/get";
            String data = this.webRepo.getUrl(url);

            // テスト結果を確認
            String expectedUserAgent = "\"User-Agent\": \"me.u6k.plantuml-image-generator:plantuml-image-generator/" + this.appVersion + "\"";
            assertThat(data.indexOf(expectedUserAgent), not(-1));
        }

    }

}
