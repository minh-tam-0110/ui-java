package testcase.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.w3c.dom.css.CSSValue;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ModelTest extends MasterTest {
    @ParameterizedTest
    @CsvSource({
            "Yes,       OK",
            "No,        CANCEL"
    })
    void verifyModel(String input, String expected) {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/modal");
        String showButtonXpath = "//div[normalize-space() = 'Modal / Popup']//following::button[normalize-space() = 'Show Confirm']";
        page.locator(showButtonXpath).click();
        String inputModel = String.format("//button[@type = 'button' and normalize-space() = '%s']",input);
        page.locator(inputModel).click();
        String expectedLabelXpath = "//div[contains(., 'Status: ') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedLabelXpath)).hasText(String.format("Status: %s",expected));
    }
}
