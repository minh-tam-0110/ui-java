package testcase.elements;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


public class CheckboxTest extends MasterTest{

    void selectCheckboxByLable(String checkboxLabel, boolean checked) {
        String normalCheckbox = "//label[normalize-space(.//text()) = '%s'] //input";
        String checkboxXpath = String.format(normalCheckbox, checkboxLabel);
        Locator checkboxLocator = page.locator(checkboxXpath);
        if (checked){
            if (!(checkboxLocator.isChecked())){
                checkboxLocator.click();
            }
        } else {
            if (checkboxLocator.isChecked()){
                checkboxLocator.click();
            }
        }
    }

    void selectCheckboxByLableVersion2(String checkboxLabel, boolean setChecked){
        String normalCheckbox = "//label[normalize-space(.//text()) = '%s'] //input";
        String checkboxXpath = String.format(normalCheckbox, checkboxLabel);
        Locator checkboxLocator = page.locator(checkboxXpath);
        checkboxLocator.setChecked(setChecked);
    }

    @Test
    void verifyThatUserCanSelectAllCheckbox() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/checkbox");
        selectCheckboxByLable("Apple", true);
        selectCheckboxByLable("Pear", true);
        selectCheckboxByLable("Orange", true);
        String expectedXpath = "//div[contains(text(), 'Selected values:')]";
        Locator expectedElementLocator = page.locator(expectedXpath);
        assertThat(expectedElementLocator).hasText("Selected values: Apple Pear Orange");

    }

    @Test
    void verifyThatUserCanSelectAllCheckboxVersion2() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/checkbox");

        selectCheckboxByLableVersion2("Apple", true);
        selectCheckboxByLableVersion2("Pear", true);
        selectCheckboxByLableVersion2("Orange", true);

        String expectedXpath = "//div[contains(text(), 'Selected values:')]";
        Locator expectedElementLocator = page.locator(expectedXpath);
        assertThat(expectedElementLocator).hasText("Selected values: Apple Pear Orange");
    }

    @Test
    void verifyThatUserCanUnselectAllCheckboxVersion2() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/checkbox");

        selectCheckboxByLableVersion2("Apple", false);
        selectCheckboxByLableVersion2("Pear", false);
        selectCheckboxByLableVersion2("Orange", false);

        String expectedXpath = "//div[contains(text(), 'Selected values:')]";
        Locator expectedElementLocator = page.locator(expectedXpath);
        assertThat(expectedElementLocator).hasText("Selected values: ");
    }

}
