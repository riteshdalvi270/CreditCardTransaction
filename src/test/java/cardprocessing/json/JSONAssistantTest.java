package cardprocessing.json;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test for {@link JSONAssistant}.
 * @author Ritesh Dalvi
 **/
public class JSONAssistantTest {

    /**
     * Test that Credit card number is found when {@link JSONAssistant#getCreditCardNumber(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetCreditCardNumber_Found() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\",\"creditCardNumber\":\"12346787667\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String creditCardNumber = JSONAssistant.getCreditCardNumber(jsonObject, "creditCardNumber");

        Assert.assertEquals("12346787667",creditCardNumber);
    }

    /**
     * Test that credit card number is not found when {@link JSONAssistant#getCreditCardNumber(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetCreditCardNumber_NotFound() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String creditCardNumber = JSONAssistant.getCreditCardNumber(jsonObject, "creditCardNumber");

        Assert.assertEquals(null,creditCardNumber);
    }

    /**
     * Test that credit card number is not found when {@link JSONAssistant#getCreditCardNumber(JSONObject, String)} is called with wrong key.
     */
    @Test
    public void test_GetCreditCardNumber_WrongKey_NotFound() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\",\"creditCardNumber\":\"12346787667\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String creditCardNumber = JSONAssistant.getCreditCardNumber(jsonObject, "lastname");

        Assert.assertEquals(null,creditCardNumber);
    }

    /**
     * Test that amount is found when {@link JSONAssistant#getAmount(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetAmount_Found() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String amount = JSONAssistant.getAmount(jsonObject, "amount");

        Assert.assertEquals("$200",amount);
    }

    /**
     * Test that amount is not found when {@link JSONAssistant#getAmount(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetAmount_NotFound() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String amount = JSONAssistant.getAmount(jsonObject, "amount");

        Assert.assertEquals(null,amount);
    }

    /**
     * Test that amount is not found when {@link JSONAssistant#getAmount(JSONObject, String)} is called with wrong key.
     */
    @Test
    public void test_GetAmount_WrongKey_NotFound() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"amount\":\"$200\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String amount = JSONAssistant.getAmount(jsonObject, "lastName");

        Assert.assertEquals(null,amount);
    }

    /**
     * Test that name is found when {@link JSONAssistant#getName(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetName_Found() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String name = JSONAssistant.getName(jsonObject, "firstName");

        Assert.assertEquals("Lisa",name);
    }

    /**
     * Test that name is not found when {@link JSONAssistant#getName(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetName_NotFound() throws JSONException {

        final String creditCardInformation = "{\"amount\":\"$200\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String name = JSONAssistant.getName(jsonObject, "firstName");

        Assert.assertEquals(null,name);
    }

    /**
     * Test that name is not found when {@link JSONAssistant#getName(JSONObject, String)} is called with wrong key.
     */
    @Test
    public void test_GetName_WrongKey_NotFound() throws JSONException {

        final String creditCardInformation = "{\"firstName\":\"Lisa\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String name = JSONAssistant.getName(jsonObject, "lastName");

        Assert.assertEquals(null,name);
    }

    /**
     * Test that limit is found when {@link JSONAssistant#getLimit(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetLimit_Found() throws JSONException {

        final String creditCardInformation = "{\"amount\":\"$200\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String limit = JSONAssistant.getLimit(jsonObject, "limit");

        Assert.assertEquals("$4000",limit);
    }

    /**
     * Test that limit is not found when {@link JSONAssistant#getLimit(JSONObject, String)} is called with right key.
     */
    @Test
    public void test_GetLimit_NotFound() throws JSONException {

        final String creditCardInformation = "{\"amount\":\"$200\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String limit = JSONAssistant.getLimit(jsonObject, "limit");

        Assert.assertEquals(null,limit);
    }

    /**
     * Test that limit is not found when {@link JSONAssistant#getLimit(JSONObject, String)} is called with wrong key.
     */
    @Test
    public void test_GetLimit_WrongKey_NotFound() throws JSONException {

        final String creditCardInformation = "{\"amount\":\"$200\",\"limit\":\"$4000\"}";

        final JSONObject jsonObject = new JSONObject(creditCardInformation);
        final String limit = JSONAssistant.getLimit(jsonObject, "lastname");

        Assert.assertEquals(null,limit);
    }
}
