package testcase.alerts;

import com.microsoft.playwright.Dialog;
import org.junit.jupiter.api.Test;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class alertTest extends MasterTest {

    void clickButtonByLabel(String label) {
        String xpath = String.format("//button[normalize-space() = '%s']", label);
        page.locator(xpath).click();
    }

    @Test
    void verifyAlertTypeOne(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/alerts");
        clickButtonByLabel("Show Alert");
        page.onDialog(Dialog::accept);
    }

    @Test
    void verifyAlertTypeTwo(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/alerts");
        page.onDialog(Dialog::accept);
        clickButtonByLabel("Show Confirm");
        String expectedLabelXpath = "//div[./text()[normalize-space() = 'Selected value:']]";
        assertThat(page.locator(expectedLabelXpath)).hasText("Selected value: OK");
    }

    @Test
    void verifyAlertTypeThree(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/alerts");
        page.onDialog(dialog -> dialog.accept("ABC"));
        clickButtonByLabel("Show Prompt");
        String expectedLabelXpath = "//div[./text()[normalize-space() = 'Entered value:']]";
        assertThat(page.locator(expectedLabelXpath)).hasText("Entered value: ABC");
    }

    @Test
    void verifyAlertTypeFour(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/alerts");
        page.onDialog(dialog -> {
            if("Please enter your name:".equals(dialog.message()))
            dialog.accept("");
            else {
                assertEquals("Name is required!", dialog.message());
                dialog.accept();
            }
        });
        clickButtonByLabel("Show Prompt");
        String expectedLabelXpath = "//div[./text()[normalize-space() = 'Entered value:']]";
    }
}
