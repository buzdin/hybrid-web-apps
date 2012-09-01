package lv.buzdin.gwt.client.bridge;

/**
 * @author Dmitry Buzdin
 */
public interface EventBridge {

    void addEventPreview(BridgeEventPreview preview);

    void removeEventPreview(BridgeEventPreview preview);

    void subscribe(String eventId, ModelCommand handler);

    void unsubscribe(ModelCommand command);

    void publish(String eventId, ModelAttributes data);

    void publish(String eventId, ModelAttributes data, ModelEventCallback callback);

}
