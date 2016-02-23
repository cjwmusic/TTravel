package com.wukong.ttravel.service.order.adapter;/*
 ********************************************************************************
 TREND MICRO HIGHLY CONFIDENTIAL INFORMATION:
 THIS SOFTWARE CONTAINS CONFIDENTIAL INFORMATION AND TRADE SECRETS OF TREND MICRO
 INCORPORATED AND MAY BE PROTECTED BY ONE OR MORE PATENTS. USE, DISCLOSURE, OR
 REPRODUCTION OF ANY PORTION OF THIS SOFTWARE IS PROHIBITED WITHOUT THE PRIOR
 EXPRESS WRITTEN PERMISSION OF TREND MICRO INCORPORATED.
 Copyright 2011 Trend Micro Incorporated. All rights reserved as an unpublished
 work.
 ********************************************************************************
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author edge_fu
 */

/**
 * This is a helper class that implements the management of tabs and all details
 * of connecting a ViewPager with associated TabHost. It relies on a trick.
 * Normally a tab host has a simple API for supplying a View or Intent that each
 * tab will show. This is not sufficient for switching between pages. So instead
 * we make the content part of the tab host 0dp high (it is not shown) and the
 * TabsAdapter supplies its own dummy view to show as the tab content. It
 * listens to changes in tabs, and takes care of switch to the correct paged in
 * the ViewPager whenever the selected tab changes.
 */
public class FragmentTabAdapter extends FragmentPagerAdapter {
    private static final String ARG_TITLE = "title";
    private List<Fragment> fragmentsList = new ArrayList<Fragment>();
    FragmentManager mFragmentManager;

    public FragmentTabAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentsList.add(fragment);
    }

    public void replaceFragment(int position, Fragment fragment) {
        fragmentsList.set(position, fragment);
    }

    public CharSequence getPageTitle(int position) {
        Bundle args = fragmentsList.get(position).getArguments();
        if (null != args) {
            return args.getString(ARG_TITLE);
        } else {
            return String.valueOf(position);
        }
    }

    public void clear() {
        try {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : fragmentsList) {
                transaction.detach(fragment);
            }
            fragmentsList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}