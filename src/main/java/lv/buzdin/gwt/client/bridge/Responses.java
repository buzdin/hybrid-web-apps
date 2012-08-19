package lv.buzdin.gwt.client.bridge;


import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.jsni.JSOAttributes;
import lv.buzdin.gwt.client.bridge.jsni.JSOResponse;

/**
 * @author dmitry.buzdin
 */
public final class Responses {

    private Responses() {
    }

    public static ModelAttributes data() {
        return (ModelAttributes) JavaScriptObject.createObject().cast();
    }

    public static JSOResponse ok() {
        return createResponse(true);
    }

    public static JSOResponse fail() {
        return createResponse(false);
    }

    public static JSOResponse createResponse(boolean success) {
        JSOResponse response = JavaScriptObject.createObject().cast();
        response.setSuccess(success);
        JSOAttributes attributes = JavaScriptObject.createObject().cast();
        response.setAttributes(attributes);
        return response;
    }

}
