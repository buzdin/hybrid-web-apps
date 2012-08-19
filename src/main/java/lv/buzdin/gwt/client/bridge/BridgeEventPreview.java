package lv.buzdin.gwt.client.bridge;

/**
 * Provides ability to preview JS <-> GWT events and react somehow.
 *
 * @author dmitry.buzdin
 */
public interface BridgeEventPreview {

    void onEvent(String eventId, ModelAttributes data);

}
