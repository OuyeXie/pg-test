package com.ouyexie.pg.data;

import com.ouyexie.pg.utils.StringUtil;

/**
 * @author Ouye Xie
 */
public class InvestmentBanner extends Banner {
    public InvestmentBanner(double value, String name) {
        super(value, name);
        Pair<Number, String> pair = StringUtil.nice(value * 10000);
        setNiceValue((long) pair.first);
        setSuffix(pair.second);
    }

    /*
     * Convenience method for creating an appropriately typed banner
     */
    public static InvestmentBanner create(double value, String name) {
        return new InvestmentBanner(value, name);
    }

    public String getContent() {

        return String.format("投资%s%s%s元，是其在新三板的最大一笔投资", name, niceValue, suffix);
    }
}
