package lv.buzdin.gwt.client.bridge.jsni;


import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.ModelAttributes;
import lv.buzdin.gwt.client.bridge.ModelResponse;

/**
 * @author dmitry.buzdin
 */
public final class JSOResponse extends JavaScriptObject implements ModelResponse {

    protected JSOResponse() {
    }

    public native void setSuccess(boolean value) /*-{
        this.success = value;
    }-*/;    
    
    public native boolean getSuccess() /*-{
        return this.success;
    }-*/;

    @Override
    public void setAttributes(ModelAttributes attributes) {
        _setAttributes((JSOAttributes) attributes);
    }

    @Override
    public ModelAttributes getAttributes() {
        return _getAttributes();
    }

    public native JSOAttributes _getAttributes() /*-{
        return this.result;
    }-*/;

    public native void _setAttributes(JSOAttributes attributes) /*-{
        this.result = attributes;
    }-*/;

}
