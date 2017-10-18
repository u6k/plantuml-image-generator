
package me.u6k.plantuml_image_generator.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import me.u6k.plantuml_image_generator.model.WebRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlantUmlServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockBean
    private WebRepository webRepo;

    @Autowired
    private PlantUmlService service;

    @Test
    public void simple_uml() throws IOException {
        String url = "http://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa70000";

        String expectedUmlPath = "/service/PlantUmlServiceTest/simple_uml.pu";
        String expectedUmlData = IOUtils.toString(this.getClass().getResourceAsStream(expectedUmlPath), "UTF-8");
        given(this.webRepo.getUrl(url)).willReturn(expectedUmlData);

        byte[] actualData = this.service.generate(url);

        String path = "/service/PlantUmlServiceTest/simple_uml.png";
        byte[] expectedData = IOUtils.toByteArray(this.getClass().getResourceAsStream(path));

        assertThat(expectedData, is(actualData));
    }

    @Test
    public void complex_uml() throws IOException {
        String url = "http://www.plantuml.com/plantuml/uml/hLXRJnj757xVNx7DKolLKa0aJQfGHUfR8lMXwNlb9cvZ1Mp4fSyxQmp6DhNJ10SIm5oC0JkO678njh5_pEpivIb_eMTcrjwPtSMYKNW0Nyxvvfjp-SvPxi_DHsVd5wQd5EMR9x4_uucO-YYMc4ucVebEnrIxLh5hguxMC5VNC5h3UXwZCdcdEoMDd5JDdLthPNdez_DiV3gUY4unJojxQbtMZDO9s8b6lqQVBSn5v-SME7xnqjmyiRNqyFi73yVPD-JWd9mKdVewE6DqP5LwHlUrqMvZr1Y7FqFxt-AFdyNc7prDpZ0td49OR-9K2gSoTcUFx9zX_GFMA_1MKMRWfl77yPbeObwzuTtoXXgTSoyjcuWNOaQ33sGpbpApUF3mzt7vMvaWC_8-aasbs33BuIUoeNrI9QTbPl4m5WynC2kRda4Y364VaKp5ND7uvQFnFwA9kQT9HPcSLCTlZjqc5-_lc9iPTN9IuP5HwK_afs48uDsFKhJLEwhHMYPBInWTih1dCUfXj079mwb5be8kpO80ToyYmzbddp1Qv6w2NIHmh9RtieUrLQW9KjWdxKEi_skkmOaPhEUKsKHoFgOc_uxDUdaBUjOngZ6k6-pSLr3VMD_3UXthLPmw1yO0wfHozj694aiyePYAG0QeqPIh_CSNaOdhHMGgcPnHnycRRH4OOYC1Ix7nx2810jUAZU02049A001jiJRVXjyJd3z97pZBpzLlIJ5Z7tc9_eu6vBhbuMZ8wAoJTCRZWQeO5J3QeSA25drHzNe4WCpjZzPnMuG35FzDIN7LwXr3h6y3lLjaDUL6VhbDNY15m8FG3llAJnDmkIRvSY7HuHOGMKTxRPKFQ3wrlDIyGCTCfSbEazwsKBGgRI5EzoAHG3txlG7Sk2n0VqffasCzwXBccvP5tGvvtAzxVQEp3yuytwCYuFU18cC-F22ZKXdYoShJp9_QlQxHst9ske8_ZPNa3urajB9m5Ay0KQAbQaTrZYZAWAln8JC2rPtI7crDFIUs9et5IjRPg0nRFrHKV0eIa0yVXjny0m4FT0n6UT84s1p5fwqFmeImXgmtBRk-J0kabOMtzbwTDCwf9IirA7UHv70y-Bj54KALm_gALstPh5dO7P8Lw56b60mAIfQl1KQdOcK_qhwfvyochdWc4V_jV0xMHjlUpRiJvmUOEBTWuepTCbyTqB7prsnoOIPKpd8zA6rpi-oKNc1qEdON7E2bdwceQVPnqNnBLmVIkB1AprbRi9KcrI654YJFhlKnklZSpKpSCJTWktbXT0x8KlfpTyK7EvYcxdqG35f5-Cn_i0zzVCAFBayv2Ttb2kLJhC8IvZz6p0GFII01GJhSxhn6rWg1H8b7yn0tswJn-dzSR-mcJyNNlXZfV7JMFbrHNm7QtFgBoN-5t4X5JvQh9BDazBU0sgWQTPOpvijUcC1yBowCmOOulQQ6iUK6HcwMYfM6c5YLFtUVG-FPVUY90bcXxQNOnqre48nqZFQfw0nqP_IKMlGD86XkXnv25oedzS7ezKbJensuyvxh3EclipsZwcgRfYjXUfuNo0SsCxgzLPX_5UiwvUXUF8NrFbmVdXgukYd3QKnw8D0hxEGQqrR_7TrjtZsSJJL-kD7EaKQPlSxn_PgTVyGWgegv_Wy5ff273Bl0yQHiqUAgb8P210y-S0X6e6CDK0PBls3Ef-Q71WDw9v6420rKcIvUemVR8FvruxBF_RqiWCXhMn0sc1LqcmoPRI60JXgU0v5zjExap-XAxEMK1XYQYKQMLTyGKAZluAGBgwOMn674cUvu4t02Qn2OGy96hPc2UII4bFounclSlyhv25ovt_369EcKX17AIi08OtNbcaCL21f2yrEyOWNmuNp14X1OyeEpLHHMQHKCIED0HK52QV_96ovTEcNpWtDJevkT_NRBkgm9YyDTkZYCkOk3Zwoasx0MExlRxjo-3zUZ__Zu3m00";

        String expectedUmlPath = "/model/WebRepositoryImplTest/criminal_jc.plantuml";
        String expectedUmlData = IOUtils.toString(this.getClass().getResourceAsStream(expectedUmlPath), "UTF-8");
        given(this.webRepo.getUrl(url)).willReturn(expectedUmlData);

        byte[] actualData = this.service.generate(url);

        String path = "/service/PlantUmlServiceTest/complex_uml.png";
        byte[] expectedData = IOUtils.toByteArray(this.getClass().getResourceAsStream(path));

        assertThat(expectedData, is(actualData));
    }

    @Test
    public void invalid_uml() throws IOException {
        String url = "http://www.plantuml.com/plantuml/uml/SoWkIImgAStDuNBAJrAmqLNGjLDmpCbCJbMmKiX8pSd9vt98pKi1oWC0";

        String expectedUmlPath = "/service/PlantUmlServiceTest/invalid_uml.pu";
        String expectedUmlData = IOUtils.toString(this.getClass().getResourceAsStream(expectedUmlPath), "UTF-8");
        given(this.webRepo.getUrl(url)).willReturn(expectedUmlData);

        byte[] actualData = this.service.generate(url);

        String path = "/service/PlantUmlServiceTest/invalid_uml.png";
        byte[] expectedData = IOUtils.toByteArray(this.getClass().getResourceAsStream(path));

        assertThat(expectedData, is(actualData));
    }

    public void invalid_argument_url_blank_1() throws IOException {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("url is blank.");
        this.service.generate(null);
    }

    public void invalid_argument_url_blank_2() throws IOException {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("url is blank.");
        this.service.generate("");
    }

    public void invalid_argument_url_blank_3() throws IOException {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("url is blank.");
        this.service.generate(" ");
    }

}
