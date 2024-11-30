package testcase.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PopConfirmTest extends MasterTest {

    @ParameterizedTest
    @ValueSource(strings = {"Yes", "No"})
    void verifyPopConfirm(String input) {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/pop-confirm");

        String buttonXpath = "(//div[normalize-space() = 'Pop confirm']//following::button[normalize-space() = 'Delete'])[1]";
        page.locator(buttonXpath).click();
        String itemButtonXpath = String.format("//div[@role = 'tooltip' and .//div[normalize-space(text())='Delete the task']]//button[.//text()[normalize-space()='%s']]", input);
        page.locator(itemButtonXpath).click();
        String expectedPopupXpath = String.format("//div[normalize-space() = 'Click on %s' and contains(concat(' ',normalize-space(@class),' '),' ant-message-notice-content ')]", input);
//        page.waitForSelector(expectedPopupXpath , new Page.WaitForSelectorOptions()
//                .setTimeout(5000)
//                .setState(WaitForSelectorState.VISIBLE)
//        );
        assertThat(page.locator(expectedPopupXpath)).hasText(String.format("Click on %s", input));

    }
}
