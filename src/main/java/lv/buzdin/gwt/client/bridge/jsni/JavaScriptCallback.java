package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.ModelAttributes;
import lv.buzdin.gwt.client.bridge.ModelEventCallback;
import lv.buzdin.gwt.client.bridge.Responses;

/**
* @author Dmitry Buzdin
*/
public final class JavaScriptCallback implements ModelEventCallback {

    private final JavaScriptObject javaScriptFunction;

    public JavaScriptCallback(JavaScriptObject javaScriptFunction) {
        this.javaScriptFunction = javaScriptFunction;
    }

    @Override
    public void resolve(ModelAttributes... response) {
        JSOResponses responses = Responses.createResponses(response);
        _resolve(javaScriptFunction, responses);
    }

    private native void _resolve(JavaScriptObject javaScriptFunction, JSOResponses responses) /*-{
        if (typeof(javaScriptFunction) == "function") {
            javaScriptFunction(responses);
        }
    }-*/;

}
