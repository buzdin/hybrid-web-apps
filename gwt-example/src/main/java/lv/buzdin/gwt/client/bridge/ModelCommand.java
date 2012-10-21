package lv.buzdin.gwt.client.bridge;

/**
 * @author dmitry.buzdin
 */
public interface ModelCommand {

    void execute(ModelAttributes attributes, ModelEventCallback callback);

}
