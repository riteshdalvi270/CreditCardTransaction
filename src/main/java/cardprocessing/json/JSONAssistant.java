package cardprocessing.json;

import cardprocessing.exception.Verifier;
import org.codehaus.jettison.json.JSONObject;

/**
 * Assistant to assist with reading JSON data.
 * @author Ritesh Dalvi.
 */
public class JSONAssistant {

    /**
     * Retrieves the credit card number from the Json Object.
     * @param jsonObject The {@link JSONObject} (cannot be null).
     * @param creditCardNumber The key to be used to retrieve from the {@link JSONObject} (cannot be null,empty or blank).
     * @return non-null {@link String} representing the credit card number.
     */
    public static String getCreditCardNumber(final JSONObject jsonObject, final String creditCardNumber) {
        return getSafeJSONObject(jsonObject,creditCardNumber);
    }

    /**
     * Retrieves the amount of the credit card from the Json Object.
     * @param jsonObject The {@link JSONObject} (cannot be null).
     * @param amount The key to be used to retrieve from the {@link JSONObject} (cannot be null,empty or blank).
     * @return non-null {@link String} representing the amount on the credit card.
     */
    public static String getAmount(final JSONObject jsonObject, final String amount) {
        return getSafeJSONObject(jsonObject,amount);
    }

    /**
     * Retrieves the name information from the Json Object.
     * @param jsonObject The {@link JSONObject} (cannot be null).
     * @param name The key to be used to retrieve from the {@link JSONObject} (cannot be null,empty or blank).
     * @return non-null {@link String} representing the name.
     */
    public static String getName(final JSONObject jsonObject, final String name) {
        return getSafeJSONObject(jsonObject,name);
    }

    /**
     * Retrieves the limit on the credit card from the Json Object.
     * @param jsonObject The {@link JSONObject} (cannot be null).
     * @param limit The key to be used to retrieve from the {@link JSONObject} (cannot be null,empty or blank).
     * @return non-null {@link String} representing the limit on the credit card.
     */
    public static String getLimit(final JSONObject jsonObject, final String limit) {
        return getSafeJSONObject(jsonObject,limit);
    }

    /**
     * Retrieves the error for invalid credit card from the Json Object.
     * @param jsonObject The {@link JSONObject} (cannot be null).
     * @param error The key to be used to retrieve from the {@link JSONObject} (cannot be null,empty or blank).
     * @return non-null {@link String} representing the error for invalid credit card.
     */
    public static String getError(final JSONObject jsonObject, final String error) {
        return getSafeJSONObject(jsonObject,error);
    }

    /**
     * Retrieves the Json String safely from {@link JSONObject} by performing appropriate checks.
     * @param jsonObject The {@link JSONObject} (cannot be null).
     * @param key The key to be used to retrieve from the {@link JSONObject} (cannot be null,empty or blank).
     * @return non-null {@link String} representing the Json string.
     * @throw {@link Verifier} if {@link JSONObject} or key is null or empty. {@link RuntimeException} if fails to retrieve json string using the key provided
     */
    private static String getSafeJSONObject(final JSONObject jsonObject, final String key) {
        Verifier.verifyNotNull(jsonObject, "jsonObject:null");
        Verifier.verifyBlank(key, "key:null,empty or blank");

        if(jsonObject.has(key)) {
            return jsonObject.optString(key);
        }

        return null;
    }
}
