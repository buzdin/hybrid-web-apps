package lv.buzdin.gwt.client.bridge;


import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.jsni.JSOResponses;

/**
 * @author dmitry.buzdin
 */
public final class Responses {

    private Responses() {
    }

    public static ModelAttributes attributes() {
        return (ModelAttributes) JavaScriptObject.createObject().cast();
    }

    public static JSOResponses createResponses(ModelAttributes ... modelAttributes) {
        JSOResponses result = JavaScriptObject.createObject().cast();
        result.setResults(modelAttributes);
        return result;
    }

}
