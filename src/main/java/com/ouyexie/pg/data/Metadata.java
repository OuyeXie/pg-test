package com.ouyexie.pg.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Ouye Xie
 */
public class Metadata implements Serializable {
    private String pkey;
    private List<Column> columns;

    public Metadata(String name, List<Column> columns) {
        this.pkey = pkey;
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Metadata)) {
            return false;
        }
        Metadata p = (Metadata) o;
        return Objects.equals(p.pkey, pkey) && Objects.equals(p.columns, columns);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return 41 ^ 2 * (pkey == null ? 0 : pkey.hashCode()) + 41 * (columns == null ? 0 : columns.hashCode());
    }
}
