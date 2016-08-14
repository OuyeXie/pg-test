package com.ouyexie.pg.data;

import com.ouyexie.pg.utils.StringUtil;

/**
 * @author Ouye Xie
 */
public class ProfitBanner extends Banner {
    public ProfitBanner(double value, String name) {
        super(value, name);
        Pair<Number, String> pair = StringUtil.nice(value * 10000);
        setNiceValue((long) pair.first);
        setSuffix(pair.second);
    }

    /*
     * Convenience method for creating an appropriately typed banner
     */
    public static ProfitBanner create(double value, String name) {
        return new ProfitBanner(value, name);
    }

    public String getContent() {
        return String.format("持有%s，浮盈%s%s元", name, niceValue, suffix);
    }
}
