package com.wukong.ttravel.service.order.fragment;

import android.os.Bundle;

import com.wukong.ttravel.Base.BaseFragment;

/**
 * Created by wukong on 2/23/16.
 */
public class OrderListFragment extends BaseFragment{


    private String strTailorID = "";

    public static OrderListFragment newInstance(String strTailorID) {
        OrderListFragment orderListFragment = new OrderListFragment();
        orderListFragment.setStrTailorID(strTailorID);
        return orderListFragment;
    }




    public void setStrTailorID(String strTailorID) {
        this.strTailorID = strTailorID;
    }
}
