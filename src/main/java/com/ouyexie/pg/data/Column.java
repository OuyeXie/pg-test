package com.ouyexie.pg.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Column implements Serializable {
    private Map<String, String> references;
    private String _default;
    private int precision;
    private boolean updatable;
    private String schema;
    private String name;
    private String type;
    private String maxLen;
    private List<String> _enum;
    private int nullable;
    private int position;

    public Column(Map<String, String> references, String _default, int precision, boolean updatable, String schema, String name, String type, String maxLen, List<String> _enum, int nullable, int position) {
        this.references = references;
        this._default = _default;
        this.precision = precision;
        this.updatable = updatable;
        this.schema = schema;
        this.name = name;
        this.type = type;
        this.maxLen = maxLen;
        this._enum = _enum;
        this.nullable = nullable;
        this.position = position;
    }

    public Map<String, String> getReferences() {
        return references;
    }

    public void setReferences(Map<String, String> references) {
        this.references = references;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
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

    public String getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(String maxLen) {
        this.maxLen = maxLen;
    }

    public List<String> getEnum() {
        return _enum;
    }

    public void setEnum(List<String> _enum) {
        this._enum = _enum;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //TODO: think through how two columns can be different
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
