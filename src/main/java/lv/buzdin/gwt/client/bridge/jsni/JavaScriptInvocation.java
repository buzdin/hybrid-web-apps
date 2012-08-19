package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.ModelEventCallback;

/**
* @author Dmitry Buzdin
*/
public final class JavaScriptInvocation {

    private final JavaScriptObject functionReference;
    private final JSOAttributes attributes;
    private final ModelEventCallback callback;

    public JavaScriptInvocation(JavaScriptObject functionReference,
                                JSOAttributes attributes,
                                ModelEventCallback callback) {
        this.functionReference = functionReference;
        this.attributes = attributes;
        this.callback = callback;
    }

    public void apply() {
        _apply(functionReference, attributes);
    }

    // referenced from jsni
    private void resolve(JSOResponse response) {
        //logger.info("callback resolved");
        callback.resolve(response);
    }

    private native void _apply(JavaScriptObject functionReference,
                               JSOAttributes attributes) /*-{
        var self = this;
        var callback = function(result) {
            self.@lv.buzdin.gwt.client.bridge.jsni.JavaScriptInvocation::resolve(Llv/buzdin/gwt/client/bridge/jso/JSOResponse;)(result);
        };
        functionReference(attributes, callback);
    }-*/;

}
