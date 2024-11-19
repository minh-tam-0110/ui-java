package testcase.components;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SwitchTest extends MasterTest {
    /*
    kiểm tra value ban đầu của button / checkbox là true hay false
    nếu false thì click còn false thì không click
    */
    @Test
    void verifySwitch(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/switch");
        String switchButtonXpath = "//div[normalize-space() = 'Switch']//following::button[@role = 'switch']";
        Locator switchButtonLocator = page.locator(switchButtonXpath);
        boolean input = true;
        if (Boolean.valueOf(switchButtonLocator.getAttribute("aria-checked")) != input){
            switchButtonLocator.click();
        }
        String expectedLabel = "//div[contains(text(), 'Current Value:') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedLabel)).hasText(String.format("Current Value: %s", input));

    }
}
