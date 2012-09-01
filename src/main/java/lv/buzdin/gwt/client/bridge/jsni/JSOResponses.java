package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import lv.buzdin.gwt.client.bridge.EventResponse;
import lv.buzdin.gwt.client.bridge.ModelAttributes;

/**
 * @author Dmitry Buzdin
 */
public final class JSOResponses extends JavaScriptObject implements EventResponse {

    protected JSOResponses() {
    }

    @Override
    public void setResults(ModelAttributes[] value) {
        JsArray<JSOAttributes> array = createArray().cast();
        for (ModelAttributes el : value) {
            array.push((JSOAttributes) el);
        }
        _setResults(array);
    }

    @Override
    public ModelAttributes[] getResults() {
        JsArray<JSOAttributes> array = _getResults();
        JSOAttributes[] result  = new JSOAttributes[array.length()];
        for (int i = 0; i < array.length(); i++) {
            result[i] = array.get(i);
        }
        return result;
    }

    private native void _setResults(JsArray<JSOAttributes> value) /*-{
        this.results=value;
    }-*/;

    private native JsArray<JSOAttributes> _getResults() /*-{
        return this.results;
    }-*/;

}
