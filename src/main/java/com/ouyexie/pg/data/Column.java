package com.ouyexie.pg.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Column implements Serializable {
    private String name;
    private String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Column)) {
            return false;
        }
        Column p = (Column) o;
        return Objects.equals(p.name, name) && Objects.equals(p.type, type);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return 41 ^ 2 * (name == null ? 0 : name.hashCode()) + 41 * (type == null ? 0 : type.hashCode());
    }
}
