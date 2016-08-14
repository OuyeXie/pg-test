package com.ouyexie.pg.data;

import com.ouyexie.pg.utils.StringUtil;

/**
 * @author Ouye Xie
 */
public class PositionBanner extends Banner {
    public PositionBanner(double value, String name) {
        super(value, name);
        Pair<Number, String> pair = StringUtil.nice(value * 10000);
        setNiceValue((long) pair.first);
        setSuffix(pair.second);
    }

    /*
     * Convenience method for creating an appropriately typed banner
     */
    public static PositionBanner create(double value, String name) {
        return new PositionBanner(value, name);
    }

    public String getContent() {
        return String.format("持有%s现值%s%s元，是其在新三板的最大仓位", name, niceValue, suffix);
    }
}
