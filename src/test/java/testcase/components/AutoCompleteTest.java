package testcase.components;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AutoCompleteTest extends MasterTest {
    @Test
    void verfifyAutoComplete() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/auto-complete");
        assertThat(page).hasTitle("Test With Me aka Tho Test");
        String inputValue = "Downing Street";
        selectItemAutoComplete(inputValue); // extract method generate
        String expectedElementXpath = "//div[contains(., 'Value: ') and contains(., ' was selected!') and ./span[contains(concat(' ', normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedElementXpath)).hasText(String.format("Value: %s was selected!", inputValue));
    }

    private void selectItemAutoComplete(String inputValue) {
        String autoCompleteInputXpath = "//div[normalize-space() = 'Auto complete']//following::input[@type = 'search']";
        Locator autocompleteInput = page.locator(autoCompleteInputXpath);
        autocompleteInput.click();
        autocompleteInput.fill(inputValue);
        String selectItemXpath = String.format("//div[normalize-space() = '%s' and contains(concat(' ',normalize-space(@class),' '),' ant-select-item-option-content ')]",inputValue);
        page.locator(selectItemXpath).click();
    }
}
