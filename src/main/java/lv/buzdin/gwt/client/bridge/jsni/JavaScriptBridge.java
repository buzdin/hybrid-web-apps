package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;
import lv.buzdin.gwt.client.bridge.*;
import lv.buzdin.gwt.client.bridge.impl.NullCallback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Dmitry Buzdin
 */
public class JavaScriptBridge implements EventBridge {

    static final Map<String, ModelCommand> commandMap = new HashMap<String, ModelCommand>();

    static JSOResponses responses;

    @Override
    public void addEventPreview(BridgeEventPreview preview) {
        // TODO
    }

    @Override
    public void removeEventPreview(BridgeEventPreview preview) {
        // TODO
    }

    @Override
    public void subscribe(String eventId, ModelCommand handler) {
        String moduleName = GWT.getModuleName();
        int id = commandMap.size();
        String handlerId = moduleName + id;
        commandMap.put(handlerId, handler);

        _subscribe(eventId, handlerId);
    }

    @Override
    public void unsubscribe(ModelCommand command) {
        for (Iterator<Map.Entry<String, ModelCommand>> iterator = commandMap.entrySet().iterator();
             iterator.hasNext(); ) {
            Map.Entry<String, ModelCommand> entry = iterator.next();
            if (entry.getValue() == command) {
                iterator.remove();
            }
        }
    }

    @Override
    public void publish(String eventId, ModelAttributes data) {
        publish(eventId, data, NullCallback.INSTANCE);
    }

    @Override
    public void publish(String eventId, ModelAttributes data, ModelEventCallback callback) {
        _publish(eventId, data);

        ModelAttributes[] results = responses.getResults();
        callback.resolve(results);
        responses = null;
    }

    public static void registerResponse(JSOResponses jsoResponses) {
        responses = jsoResponses;
    }

    public static void publish(String handler, ModelAttributes data, JavaScriptObject fn) {
        ModelCommand modelCommand = commandMap.get(handler);
        if (modelCommand != null) {
            modelCommand.execute(data, new JavaScriptCallback(fn));
        }
    }

    private native void _subscribe(String eventId, String handler) /*-{
        var handle = handler;
        $wnd.$bridge.subscribe(eventId, function (response, fn) {
            console.log(handle);
            console.log(response);
            console.log(fn);

            $entry(@lv.buzdin.gwt.client.bridge.jsni.JavaScriptBridge::publish(Ljava/lang/String;Llv/buzdin/gwt/client/bridge/ModelAttributes;Lcom/google/gwt/core/client/JavaScriptObject;)(handle, response, fn));
        });
    }-*/;

    private native void _publish(String eventId, ModelAttributes data) /*-{
        $wnd.$bridge.publish(eventId, data, function (response) {
            $entry(@lv.buzdin.gwt.client.bridge.jsni.JavaScriptBridge::registerResponse(Llv/buzdin/gwt/client/bridge/jsni/JSOResponses;)(response));
        });
    }-*/;

}
