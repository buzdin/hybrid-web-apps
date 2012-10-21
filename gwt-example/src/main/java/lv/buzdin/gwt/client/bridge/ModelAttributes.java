package lv.buzdin.gwt.client.bridge;

/**
 * @author dmitry.buzdin
 */
public interface ModelAttributes {

    String get(String key);

    void set(String key, String value);

    Double getDouble(String key);
    
    void setDouble(String key, Double value);
    
    Integer getInteger(String key);

    void setInteger(String key, Integer value);
    
    void setArray(String key, String value[]);

    String[] getArray(String key);
}
