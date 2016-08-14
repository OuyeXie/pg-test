package com.ouyexie.pg.data;

import com.ouyexie.pg.utils.StringUtil;

/**
 * @author Ouye Xie
 */
public class ShareholderBanner extends Banner {
    private int rank;
    public ShareholderBanner(double value, String name, int rank) {
        super(value, name);
        Pair<Number, String> pair = StringUtil.nice(value);
        setNiceValue((long) pair.first);
        setSuffix(pair.second);
        this.rank = rank;
    }

    /*
     * Convenience method for creating an appropriately typed banner
     */
    public static ShareholderBanner create(double value, String name, int rank) {
        return new ShareholderBanner(value, name, rank);
    }

    public int getRank() {
        return rank;
    }

    public String getContent() {
        return String.format("持有%s价值%s%s元，是该公司第%s大股东", name, niceValue, suffix, rank);
    }
}
