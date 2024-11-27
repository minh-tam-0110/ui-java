package testcase.components;

import org.junit.jupiter.api.Test;
import testcase.MasterTest;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UploadFileTest extends MasterTest {
    @Test
    void verifyUploadFile() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/upload");
        String uploadInputXpath = "(//button[span[normalize-space(text())='Click to Upload']]//preceding-sibling::input[@type='file'])[1]";
        Path path = new File(getClass()
                .getResource("/upload/my-upload-file.txt")
                .getFile()).toPath();
        page.locator(uploadInputXpath).setInputFiles(path);
        String expectedXpath = "(//div[contains(concat(' ',normalize-space(@class),' '),' ant-upload-list ')]//span[contains(concat(' ',normalize-space(@class),' '),' ant-upload-list-item-name ')])[1]";
        assertThat(page.locator(expectedXpath)).hasText("my-upload-file.txt");
    }
}
