package testcase.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NotificationTest extends MasterTest {

    @ParameterizedTest
    @CsvSource({
            "Success     ,     SUCCESS",
            "Info     ,        INFO",
            "Warning     ,     WARNING",
            "Error     ,       ERROR"
    })
    void verifyNotification(String options, String expected) {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/notification");
        String buttonXpath = String.format("(//div[normalize-space() = 'Notification']//following::button[normalize-space() = '%s' and contains(concat(' ',normalize-space(@class),' '),' ant-btn ')])[1]", options);
        page.locator(buttonXpath).click();
        String notificationXpath = String.format("//div[contains(concat(' ',normalize-space(@class),' '),' ant-notification-notice-wrapper ') and .//div[normalize-space() = 'Notification %s']]", expected);
        page.waitForSelector(notificationXpath, new Page.WaitForSelectorOptions()
                .setTimeout(3000)
                .setState(WaitForSelectorState.VISIBLE)
        );
        assertThat(page.locator(notificationXpath)).containsText(String.format("Notification %sYou have clicked the %s button.",expected, expected));
    }

    @Test
    void verifyNotificatoinFirstSolution(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/notification");
        String buttonXpath = "(//div[normalize-space() = 'Notification']//following::button[normalize-space() = 'Success'])[1]";
        page.locator(buttonXpath).click();
        String messageXpath = "//div[contains(concat(' ',normalize-space(@class),' '),' ant-notification-notice-message') and normalize-space() = 'Notification SUCCESS']";
        assertThat(page.locator(messageXpath)).containsText("Notification SUCCESS");
        String descriptionXpath = "//div[contains(concat(' ',normalize-space(@class),' '),' ant-notification-notice-description') and normalize-space() = 'You have clicked the SUCCESS button.']";
        assertThat(page.locator(descriptionXpath)).containsText("You have clicked the SUCCESS button.");
    }


    @Test
    void verifyNotificatoinSecondSolution(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/notification");
        String buttonXpath = "(//div[normalize-space() = 'Notification']//following::button[normalize-space() = 'Success'])[1]";
        page.locator(buttonXpath).click();
        String messageXpath = "//div[contains(concat(' ',normalize-space(@class),' '),' ant-notification-notice-with-icon')]";
        assertThat(page.locator(messageXpath)).containsText("Notification SUCCESSYou have clicked the SUCCESS button.");
    }
}
