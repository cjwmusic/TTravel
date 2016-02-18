package com.wukong.ttravel.Base;


/**
 * Created by wukong on 2/17/16.
 */
public enum TabIndexEnum {
    HOME_TAB_INDEX(0),
    DISCOVER_TAB_INDEX(1),
    ORDER_TAB_INDEX(2);

    public int value;

    private TabIndexEnum(int value) {
        this.value = value;
    }

    public static TabIndexEnum to(int value) {
        for (TabIndexEnum element : values()) {
            if (element.value == value) {
                return element;
            }

        }
        return null;
    }

}
