package support;

import java.util.Map;

public class YamlMap {

    private Map<String, Object> map;

    public YamlMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getString(String key) {
        return get(key, String.class);
    }

    public Integer getInt(String key) {
        return get(key, Integer.class);
    }

    public Boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    public Double getDouble(String key) {
        return get(key, Double.class);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = map.get(key);
        Object convertedValue;
        if (value == null) {
            convertedValue = null;
        } else {
            try {
                convertedValue = type.cast(value);
            } catch (ClassCastException e) {
                throw new ClassCastException(value.getClass() + "cannot be converted to " + type);
            }
        }
        return (T) convertedValue;
    }
}