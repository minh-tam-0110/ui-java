package testcase.elements;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ButtonTest extends MasterTest{

    void clickButton(String buttonLabel) {
        String normalButton = String.format("//button[normalize-space() ='%s']", buttonLabel);
        String divButton = String.format("//*[@role = 'button' and normalize-space(.//text()) = '%s']", buttonLabel);
        String inputButton = String.format("//input[@type = 'button' and @value = '%s']", buttonLabel);
        String buttonXpath = String.format("%s | %s | %s", normalButton, divButton, inputButton);
        Locator buttonLocator = page.locator(buttonXpath);
        buttonLocator.click();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Div button", "Primary", "Input button"})
    void shouldClickButtonTest(String buttonLabel) {
    page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/button");
    clickButton(buttonLabel);
    String expectedElementXpath = "//div[contains(., 'Button') " +
            "and contains(., 'was clicked!') " +
            "and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]] ";
    Locator expectedElement = page.locator(expectedElementXpath);
    assertThat(expectedElement).hasText(String.format("Button %s was clicked!", buttonLabel));

}

}
