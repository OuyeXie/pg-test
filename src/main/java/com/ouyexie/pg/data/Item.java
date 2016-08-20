package com.ouyexie.pg.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Item implements Serializable {
    private String name;
    private String type;
    private String value;

    public Item(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item)) {
            return false;
        }
        Item p = (Item) o;
        return Objects.equals(p.name, name) && Objects.equals(p.type, type) && Objects.equals(p.value, value);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return 41 ^ 3 * (name == null ? 0 : name.hashCode()) + 41 ^ 2 * (type == null ? 0 : type.hashCode()) + 41 * (value == null ? 0 : value.hashCode());
    }
}
