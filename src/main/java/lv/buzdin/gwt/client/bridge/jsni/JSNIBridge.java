package lv.buzdin.gwt.client.bridge.jsni;

import com.google.gwt.core.client.JavaScriptObject;
import lv.buzdin.gwt.client.bridge.*;
import lv.buzdin.gwt.client.bridge.impl.AggregateEventCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author dmitry.buzdin
 */
public final class JSNIBridge implements EventBridge {

    private static final Logger logger = Logger.getLogger("jsni-bridge");

    private final Map<String, List<ModelCommand>> eventHandlerMap = new HashMap<String, List<ModelCommand>>();
    private final List<BridgeEventPreview> previews = new ArrayList<BridgeEventPreview>();

    private static JSNIBridge instance;

    public JSNIBridge() {
        instance = this;
    }

    @Override
    public void addEventPreview(BridgeEventPreview preview) {
        previews.add(preview);
    }

    @Override
    public void removeEventPreview(BridgeEventPreview preview) {
        previews.remove(preview);
    }

    @Override
    public void unsubscribe(ModelCommand command) {
        for (List<ModelCommand> commandMap : instance.eventHandlerMap.values()) {
            commandMap.remove(command);
        }
    }

    // Intended to be called from JavaScript
    public static void subscribe(String eventId, JavaScriptObject function) {
        instance.subscribe(eventId, new JavaScriptFunction(function));
    }

    @Override
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

    @Override
    public void publish(String eventId, ModelAttributes data) {
        publish(eventId, data, NullCallback.INSTANCE);
    }

    @Override
    public void publish(String eventId, ModelAttributes data, ModelEventCallback callback) {
        logger.info("PUB >>> '" + eventId);

        List<ModelCommand> commandList = eventHandlerMap.get(eventId);
        if (commandList == null || commandList.isEmpty()) {
            logger.info("No subscribers found for " + eventId);
            return;
        }
        logger.info("Subscribers count " + commandList.size());

        for (BridgeEventPreview preview : previews) {
            preview.onEvent(eventId, data);
        }

        AggregateEventCallback aggregateEventCallback = new AggregateEventCallback();
        for (ModelCommand command : commandList) {
            command.execute(data, aggregateEventCallback);
        }

        List<ModelAttributes> responses = aggregateEventCallback.getResponses();
        ModelAttributes[] sample = new ModelAttributes[responses.size()];
        callback.resolve(responses.toArray(sample));
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
        gwtEventbus.subscribe = $entry(@lv.buzdin.gwt.client.bridge.jsni.JSNIBridge::subscribe(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;));
        gwtEventbus.unsubscribe = function() {}; // TODO
        gwtEventbus.publish = $entry(@lv.buzdin.gwt.client.bridge.jsni.JSNIBridge::publish(Ljava/lang/String;Llv/buzdin/gwt/client/bridge/ModelAttributes;Lcom/google/gwt/core/client/JavaScriptObject;));

        $wnd.$bridge.registerEventBus(gwtEventbus);
    }-*/;

    private static final class NullCallback implements ModelEventCallback {

        public static final NullCallback INSTANCE = new NullCallback();

        @Override
        public void resolve(ModelAttributes... attributes) { }

    }

}
