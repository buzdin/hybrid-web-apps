package lv.buzdin.gwt.client.bridge;

/**
 * @author Dmitry Buzdin
 */
public interface EventResponse {

    void setResults(ModelAttributes[] value);

    ModelAttributes[] getResults();

}
