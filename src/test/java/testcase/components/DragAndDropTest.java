package testcase.components;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;

import testcase.MasterTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DragAndDropTest extends MasterTest {
    @Test
    public void verifyDragAndDrop() {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/drag-n-drop");

        List<String> itemMoveFromLeftToRight = List.of("Apple", "Banane");
        String leftPanelXpath = "(//div[.//text()[normalize-space()='Drag n Drop']]//following::div[contains(concat(' ',normalize-space(@class),' '),' ant-space ')])[1]//div[contains(concat(' ',normalize-space(@class),' '),' ant-space-item ')][1]";
        Locator leftPanelLocator = page.locator(leftPanelXpath);
        List<String> currentLeftItems = getCurrentPanelItems(leftPanelLocator);
        String rightPanelXpath = "(//div[.//text()[normalize-space()='Drag n Drop']]//following::div[contains(concat(' ',normalize-space(@class),' '),' ant-space ')])[1]//div[contains(concat(' ',normalize-space(@class),' '),' ant-space-item ')][2]";
        Locator rightPanelLocator = page.locator(rightPanelXpath);
        List<String> currentRightItems = getCurrentPanelItems(rightPanelLocator);

        moveItems(itemMoveFromLeftToRight, leftPanelLocator, rightPanelLocator);

        //Verify left
        currentLeftItems.removeAll(itemMoveFromLeftToRight);
        verifyPanelCurrentItems(leftPanelLocator, currentLeftItems);

        currentRightItems.addAll(itemMoveFromLeftToRight);
        verifyPanelCurrentItems(rightPanelLocator, currentRightItems);

        //---

        List<String> itemsMoveFromRightToLeft = List.of("Mango", "Grapes");
        moveItems(itemsMoveFromRightToLeft, rightPanelLocator, leftPanelLocator);
        //Verify right
        currentLeftItems.addAll(itemsMoveFromRightToLeft);
        verifyPanelCurrentItems(leftPanelLocator, currentLeftItems);

        currentRightItems.removeAll(itemsMoveFromRightToLeft);
        verifyPanelCurrentItems(rightPanelLocator, currentRightItems);

    }

    private void moveItems(List<String> items, Locator form, Locator to){
        for (String item : items) {
            String itemXpath = String.format("//button[.//text()[normalize-space()='%s']]", item);
            form.locator(itemXpath).dragTo(to);
        }
    }

    private void verifyPanelCurrentItems(Locator panelLocator, List<String> expectedCurrentItems) {
        List<String> actualCurrentItems = getCurrentPanelItems(panelLocator);
        assertTrue(expectedCurrentItems.containsAll(actualCurrentItems));
        assertTrue(actualCurrentItems.containsAll(expectedCurrentItems));
    }

    private List<String> getCurrentPanelItems(Locator panelLocator) {
        List<String> actualLeftCurrentItems = new ArrayList<>();
        List<Locator> leftCurrentItemLocator = panelLocator.locator("//button").all();
        for (Locator leftItemLocator : leftCurrentItemLocator) {
            String value = leftItemLocator.textContent();
            actualLeftCurrentItems.add(value);
        }
        return actualLeftCurrentItems;
    }
}
