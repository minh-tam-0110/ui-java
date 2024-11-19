package testcase.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MenuTest extends MasterTest {
    @ParameterizedTest
    @CsvSource({
            "'Option 1', '1'",
            "'Option 2', '2'",
            "'Option 3', '3'"

    })
    void verifyMenu(String options, String expected) {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/menu");
        String menuXpath = "//div[normalize-space() = 'My Menu' and @role = 'menuitem']//parent::li";
        page.locator(menuXpath).hover();
        String itemXpath = String.format("//li[normalize-space() = '%s' and @role = 'menuitem']", options);
        page.locator(itemXpath).click();
        String expectedXpath = "//div[contains(text(), 'Current value:') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedXpath)).hasText(String.format("Current value: setting:%s",expected));
    }
}
