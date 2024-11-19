package testcase.components;

import org.junit.jupiter.api.Test;
import testcase.MasterTest;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TransferTest extends MasterTest {

    void moveItem(String[] inputItems, String target) {
        String itemXpath = "//li[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-content-item ') and normalize-space() = '%s']";
        for (String input : inputItems) {
            page.locator(String.format(itemXpath, input)).click();
        }
        String targetXpath = String.format("//button[.//span[contains(concat(' ',normalize-space(@class),' '),' anticon-%s ')]]", target);
        page.locator(targetXpath).click();
    }

    void verifyPanel(String[] expected, String target){
        String panelXpath = String.format("//span[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-header-title ') and normalize-space(text())='%s']/..//following-sibling::div//li[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-content-item ')]",target);
        List<String> allInnerTextRightPanel = page.locator(panelXpath).allInnerTexts();
        assertArrayEquals(expected, allInnerTextRightPanel.toArray());
    }


    @Test
    void verfiyTransfer(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/transfer");
        String[] moveToRightInputs = {"Apple", "Banana"};
//        String itemXpath = "//li[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-content-item ') and normalize-space() = '%s']";
//        for (String input : moveToRightInputs) {
//            String rightItemXpath = String.format(itemXpath, input);
//            page.locator(rightItemXpath).click();
//        }
//        String motoRightXpath = "//button[.//span[contains(concat(' ',normalize-space(@class),' '),' anticon-right ')]]";
//        page.locator(motoRightXpath).click();
        moveItem(moveToRightInputs, "right");

        //verify item have been move to the right panel
        //used inner-text of
//        String rightPanelXpath = "//span[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-header-title ') and normalize-space(text())='Target']/..//following-sibling::div//li[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-content-item ')]";
//        List<String> allInnerTextRightPanel = page.locator(rightPanelXpath).allInnerTexts();
        String[] rightPanelExpected = {"Apple", "Banana", "Orange", "Pineapple" , "Strawberry"};
//        assertArrayEquals(rightPanelExpected, allInnerTextRightPanel.toArray());
        verifyPanel(rightPanelExpected, "Target");

        //continute
        String[] moveToLeftInputs = {"Orange", "Pineapple"};
//        for (String input : moveToLeftInputs) {
//            String leftItemXpath = String.format(itemXpath, input);
//            page.locator(leftItemXpath).click();
//        }
//        String motoLeftXpath = "//button[.//span[contains(concat(' ',normalize-space(@class),' '),' anticon-left ')]]";
//        page.locator(motoLeftXpath).click();
        moveItem(moveToLeftInputs, "left");

//        String leftPanelXpath = "//span[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-header-title ') and normalize-space(text())='Source']/..//following-sibling::div//li[contains(concat(' ',normalize-space(@class),' '),' ant-transfer-list-content-item ')]";
//        List<String> allInnerTextleftPanel = page.locator(leftPanelXpath).allInnerTexts();
        String[] leftPanelExpected = {"Kiwi", "Orange", "Pineapple"};
//        assertArrayEquals(leftPanelExpected, allInnerTextleftPanel.toArray());
        verifyPanel(leftPanelExpected, "Source");

    }

}
