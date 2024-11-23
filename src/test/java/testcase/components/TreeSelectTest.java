package testcase.components;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TreeSelectTest extends MasterTest {
    @ParameterizedTest
    @ValueSource(strings = {"Light > Bamboo", "Heavy > Walnut"})
    void verifyTreeSelect(String inputs) {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/tree-select");
        String treeSelectInputXpath = "//div[normalize-space() = 'TreeSelect']//following::span[contains(concat(' ',normalize-space(@class),' '),' ant-select-selection-search ')]";
        page.locator(treeSelectInputXpath).click();
        String[] inputString = inputs.split(">");
        for (int i = 0; i < inputString.length; i++) {
            if (i < inputString.length - 1) {
                //click oon arrow
                String arrowXpath = String.format("(//span[normalize-space(text())='%s']//preceding::span[contains(concat(' ',normalize-space(@class),' '),' ant-select-tree-switcher ')])[last()]", inputString[i].trim());
                Locator arrowLocator = page.locator(arrowXpath);
                if (!arrowLocator.getAttribute("class").contains("ant-select-tree-switcher_open")){
                    arrowLocator.click();
                }
            } else {
                //click on options
                String itemXpath = String.format("//span[contains(concat(' ',normalize-space(@class),' '),' ant-select-tree-title ')][normalize-space(text())='%s']", inputString[i].trim());
                page.locator(itemXpath).click();
            }
        }
        String expectedLabelXpath = "//div[contains(., 'Current value: ') and ./span[contains(concat(' ', normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedLabelXpath)).hasText(String.format("Current value: %s", inputString[inputString.length - 1].trim().toLowerCase()));
    }
}