package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.ModelEventCallback;
import lv.buzdin.gwt.client.bridge.ModelResponse;

/**
* @author Dmitry Buzdin
*/
public final class JavaScriptCallback implements ModelEventCallback {

    private final JavaScriptObject javaScriptFunction;

    public JavaScriptCallback(JavaScriptObject javaScriptFunction) {
        this.javaScriptFunction = javaScriptFunction;
    }

    @Override
    public void resolve() {
        _resolve(javaScriptFunction);
    }

    @Override
    public void resolve(ModelResponse response) {
        _resolve(javaScriptFunction, (JSOResponse) response);
    }

    private native void _resolve(JavaScriptObject javaScriptFunction) /*-{
        javaScriptFunction({});
    }-*/;

    private native void _resolve(JavaScriptObject javaScriptFunction, JSOResponse response) /*-{
        javaScriptFunction(response);
    }-*/;

}
