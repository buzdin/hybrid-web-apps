package lv.buzdin.gwt.client.bridge.jsni;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import lv.buzdin.gwt.client.bridge.ModelAttributes;

/**
 * @author dmitry.buzdin
 */
public final class JSOAttributes extends JavaScriptObject implements ModelAttributes {

    protected JSOAttributes() {
    }

    @Override
    public native void set(String key, String value) /*-{
        this[key]=value;
    }-*/;

    @Override
    public native String get(String key) /*-{
        return this[key];
    }-*/;

    @Override
    public Double getDouble(String key) {
        String value = get(key);
        return Double.parseDouble(value);
    }

    @Override
    public void setDouble(String key, Double value) {
        set(key, value.toString());
    }

    @Override
    public Integer getInteger(String key) {
        Double value = getDouble(key);
        return value.intValue();
    }

    @Override
    public void setInteger(String key, Integer value) {
        set(key, value.toString());
    }

    @Override
    public void setArray(String key, String[] value) {
        JsArrayString array = createArray().cast();
        for (String el : value) {
            array.push(el);
        }
        _setArray(key, array);
    }

    @Override
    public String[] getArray(String key) {
        JsArrayString array = _getArray(key);
        String[] result  = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            result[i] = array.get(i);
        }
        return result;
    }

    private native void _setArray(String key, JsArrayString value) /*-{
        this[key]=value;
    }-*/;

    private native JsArrayString _getArray(String key) /*-{
        return this[key];
    }-*/;

}
