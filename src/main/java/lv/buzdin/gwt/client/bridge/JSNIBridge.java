package lv.buzdin.gwt.client.bridge;

import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.jsni.JavaScriptCallback;
import lv.buzdin.gwt.client.bridge.jsni.JavaScriptFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author dmitry.buzdin
 */
public final class JSNIBridge {

    private static final Logger logger = Logger.getLogger("jsni-bridge");

    private final Map<String, List<ModelCommand>> eventHandlerMap = new HashMap<String, List<ModelCommand>>();
    private final List<BridgeEventPreview> previews = new ArrayList<BridgeEventPreview>();

    private static JSNIBridge instance;

    public JSNIBridge() {
        instance = this;
    }

    public void addEventPreview(BridgeEventPreview preview) {
        previews.add(preview);
    }

    public void removeEventPreview(BridgeEventPreview preview) {
        previews.remove(preview);
    }

    public void unregister(ModelCommand command) {
        for (List<ModelCommand> commandMap : instance.eventHandlerMap.values()) {
            commandMap.remove(command);
        }
    }

    // Intended to be called from JavaScript
    public static void subscribe(String eventId, JavaScriptObject function) {
        instance.subscribe(eventId, new JavaScriptFunction(function));
    }

    // Intended to be called from Java
    public void subscribe(String eventId, ModelCommand handler) {
        logger.info("SUB >>> '" + eventId + "' id : " + handler.hashCode());

        List<ModelCommand> handlers = eventHandlerMap.get(eventId);
        if (handlers == null) {
            handlers = new ArrayList<ModelCommand>();
            eventHandlerMap.put(eventId, handlers);
        }
        handlers.add(handler);
    }

    // Intended to be called from JavaScript
    public static void publish(String eventId, ModelAttributes data, JavaScriptObject resultCallback) {
        instance.publish(eventId, data, new JavaScriptCallback(resultCallback));
    }

    // Intended to be called from Java
    public void publish(String eventId, ModelAttributes data) {
        publish(eventId, data, NullCallback.INSTANCE);
    }

    // Intended to be called from Java
    public void publish(String eventId, ModelAttributes data, ModelEventCallback callback) {
        logger.info("PUB >>> '" + eventId);

        List<ModelCommand> commandList = eventHandlerMap.get(eventId);
        if (commandList == null || commandList.isEmpty()) {
            logger.info("No subscribers found for " + eventId);
            return;
        }

        // Event Previews are guaranteed to be first
        for (BridgeEventPreview preview : previews) {
            preview.onEvent(eventId, data);
        }

        for (ModelCommand command : commandList) {
            command.execute(data, callback);
        }
    }

    public void registerJsFunctions() {
        logger.info("Registering JavaScript bridge");
        _registerJsFunctions();
    }

    // Wiring together JavaScript <-> Java bridge functions
    private native void _registerJsFunctions() /*-{
        // Set up view bridge.
        if ($wnd.$bridge == null) {
            $wnd.alert('No bridge loaded');
        }

        var gwtEventbus = {};
        gwtEventbus.subscribe = $entry(@lv.buzdin.gwt.client.bridge.JSNIBridge::subscribe(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;));
        gwtEventbus.unsubscribe = function() {};
        gwtEventbus.publish = $entry(@lv.buzdin.gwt.client.bridge.JSNIBridge::publish(Ljava/lang/String;Llv/buzdin/gwt/client/bridge/ModelAttributes;Lcom/google/gwt/core/client/JavaScriptObject;));

        $wnd.$bridge.addEventBus(gwtEventbus);
    }-*/;

    private static final class NullCallback implements ModelEventCallback {

        public static final NullCallback INSTANCE = new NullCallback();

        @Override
        public void resolve() { }

        @Override
        public void resolve(ModelResponse response) { }

    }

}
