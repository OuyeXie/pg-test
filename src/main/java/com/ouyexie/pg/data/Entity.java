package com.ouyexie.pg.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Entity implements Serializable {
    private String code;
    private String name;
    private String[] labels;

    public Entity(String name) {
        this.name = name;
        this.labels = null;
    }

    public Entity(String name, String[] labels) {
        this.name = name;
        this.labels = labels;
    }

    public Entity(String code, String name) {
        this.code = code;
        this.name = name;
        this.labels = null;
    }

    public Entity(String code, String name, String[] labels) {
        this.code = code;
        this.name = name;
        this.labels = labels;
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     *
     * @param code the first object in the Pair
     * @param name the second object in the pair
     * @return a Entity
     */
    public static Entity create(String code, String name) {
        return new Entity(code, name);
    }

    public static Entity create(String code, String name, String[] labels) {
        return new Entity(code, name, labels);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Entity)) {
            return false;
        }
        Entity p = (Entity) o;
        return Objects.equals(p.code, code) && Objects.equals(p.name, name);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return 41 ^ 2 * (code == null ? 0 : code.hashCode()) + 41 * (name == null ? 0 : name.hashCode());
    }
}
