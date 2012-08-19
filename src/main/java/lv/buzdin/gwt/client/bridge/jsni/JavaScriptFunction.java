package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.ModelAttributes;
import lv.buzdin.gwt.client.bridge.ModelCommand;
import lv.buzdin.gwt.client.bridge.ModelEventCallback;

/**
* @author Dmitry Buzdin
*/
public final class JavaScriptFunction implements ModelCommand {

    private final JavaScriptObject functionReference;

    public JavaScriptFunction(JavaScriptObject functionReference) {
        this.functionReference = functionReference;
    }

    public void execute(ModelAttributes attributes, ModelEventCallback callback) {
        JavaScriptInvocation invocation = new JavaScriptInvocation(functionReference, (JSOAttributes) attributes, callback);
        invocation.apply();
    }

}
