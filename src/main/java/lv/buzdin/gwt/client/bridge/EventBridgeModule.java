package lv.buzdin.gwt.client.bridge;

import com.google.gwt.core.client.EntryPoint;
import lv.buzdin.gwt.client.bridge.jsni.JSNIBridge;

/**
 * @author Dmitry Buzdin
 */
public class EventBridgeModule implements EntryPoint {

    @Override
    public void onModuleLoad() {
        JSNIBridge bridge = new JSNIBridge();
        bridge.registerJsFunctions();
    }

}
