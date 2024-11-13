package testcase.elements;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DropDownTest extends MasterTest {

    String buttonDropDown = "//div[@role = 'separator' and normalize-space() = 'Dropdown']//following::button[normalize-space() = 'Select an item']//following-sibling::button";
    // Option 2: "//div[@role = 'separator' and normalize-space() = 'Dropdown']//following::button[contains(@class, 'btn-compact-last-item')]"
    String expectDropDown = "//div[@role = 'separator' and normalize-space() = 'Dropdown']//following::div[contains(., 'Value:') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]]";
    String itemDropDownXpath = "//ul[@role = 'menu']//li[@role = 'menuitem' and normalize-space(.//text()) = '%s']";


    void navigatePage() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/dropdown");
    }


    void actionClickDropDown(String listItem) {
        Locator buttonDropDownLocator = page.locator(buttonDropDown);
        Locator itemDropDownLocator = page.locator(String.format(itemDropDownXpath, listItem));
        buttonDropDownLocator.click();
        itemDropDownLocator.click();
    }

//    void resultDropDown(String listItem) {
//        Locator resultDropDownLocator = page.locator(expectDropDown);
//        assertThat(resultDropDownLocator).hasText("Value: " + listItem);
//    }

    @ParameterizedTest
    @ValueSource(strings = {"1st menu item","2nd menu item","3rd menu item","4rd menu item"})
    void verifyDropDown(String listItem) {
        navigatePage();
        actionClickDropDown(listItem);
        String buttonResultDropDownXpath = "//button[contains(@class, 'btn-compact-first-item')]//span";
        Locator resultDropDownLocator = page.locator(expectDropDown);
        Locator buttonResultLocator = page.locator(buttonResultDropDownXpath);
        assertThat(resultDropDownLocator).hasText("Value: " + buttonResultLocator.textContent());
    }
}
