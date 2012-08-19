package lv.buzdin.gwt.client.bridge;

/**
 * @author dmitry.buzdin
 */
public interface ModelResponse {

    void setSuccess(boolean value);

    boolean getSuccess();

    ModelAttributes getAttributes();

    void setAttributes(ModelAttributes attributes);

}
