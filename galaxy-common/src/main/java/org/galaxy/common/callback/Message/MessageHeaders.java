package org.galaxy.common.callback.Message;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.*;

public class MessageHeaders implements Map<String, Object>, Serializable {

    public static final UUID ID_VALUE_NONE = new UUID(0L, 0L);

    private final Map<String, Object> headers;

    public MessageHeaders(@Nullable Map<String, Object> headers) {
        this(headers, null, null);
    }

    protected MessageHeaders(@Nullable Map<String, Object> headers, @Nullable UUID id, @Nullable Long timestamp) {
        this.headers = headers != null ? new HashMap<>(headers) : new HashMap<>();
        if (id == null) {
            this.headers.put("id", UUID.randomUUID().toString());
        } else if (id == ID_VALUE_NONE) {
            this.headers.remove("id");
        } else {
            this.headers.put("id", id);
        }

        if (timestamp == null) {
            this.headers.put("timestamp", System.currentTimeMillis());
        } else if (timestamp < 0L) {
            this.headers.remove("timestamp");
        } else {
            this.headers.put("timestamp", timestamp);
        }

    }

    @Override
    public int size() {
        return this.headers.size();
    }

    @Override
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.headers.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return this.headers.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("MessageHeaders is immutable");
    }

    @Override
    public Set<String> keySet() {
        return this.headers.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.headers.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.headers.entrySet();
    }
}
