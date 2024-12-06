package testcase.form;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FormTestData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import testcase.MasterTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormTest extends MasterTest {

    static Stream<?> formValidationData() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List< Map<String, Map<String,String>>> dataList = mapper.readValue(FormTestData.formValidationData, new
                TypeReference<List< Map<String, Map<String,String>>>>(){});
        return dataList.stream();
    }

    @ParameterizedTest
    @MethodSource("formValidationData")
    void verifyFormValidation(Map<String, Map<String,String>> data) {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/form");

        for (Map.Entry<String, Map<String,String>> fieldData : data.entrySet()){
            String inputXpath = String.format("(//div[normalize-space() = '%s']//following-sibling::div//input)[1]", fieldData.getKey());
            page.locator(inputXpath).fill(fieldData.getValue().get("input"));
        }

        String submitButtonXpath = "//button[.//text()[normalize-space() = 'Submit']]";
        page.locator(submitButtonXpath).click();

        Map<String, String> expectedError = new HashMap<>();
        Map<String, String> actualError = new HashMap<>();

        for (Map.Entry<String, Map<String,String>> fieldData : data.entrySet()){
            String inputXpath = String.format("//div[contains(concat(' ',normalize-space(@class),' '),' ant-form-item ') and .//label[.//text()[normalize-space() = '%s']]]//div[@role = 'alert']", fieldData.getKey());
            expectedError.put(fieldData.getKey(), fieldData.getValue().get("expected"));
            actualError.put(fieldData.getKey(), page.locator(inputXpath).textContent());
        }
        assertEquals(expectedError, actualError);
    }
}
