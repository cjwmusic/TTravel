package com.wukong.ttravel.service.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wukong.ttravel.Base.BaseFragment;
import com.wukong.ttravel.Base.views.SlidingTabLayout;
import com.wukong.ttravel.R;
import com.wukong.ttravel.service.order.adapter.FragmentTabAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/23/16.
 */
public class OrderFragment extends BaseFragment implements  ViewPager.OnPageChangeListener {

    private View rootView;

    @Bind(R.id.categorySlidingTab)
    SlidingTabLayout categorySlidingTab;
    @Bind(R.id.categoryTabViewPager)
    ViewPager categoryTabViewPager;

    private FragmentTabAdapter categoryPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_order,container,false);

            ButterKnife.bind(this, rootView);

            //未完成、已完成的Tab
            categoryPagerAdapter = new FragmentTabAdapter(getActivity().getSupportFragmentManager());
            categoryPagerAdapter.clear();
            categoryPagerAdapter.addFragment(OrderListFragment.newInstance("未完成订单","123232"));
            categoryPagerAdapter.addFragment(OrderListFragment.newInstance("已完成订单","123232"));

            categoryTabViewPager.setAdapter(categoryPagerAdapter);
            categoryPagerAdapter.notifyDataSetChanged();

            categorySlidingTab.setCustomTabView(R.layout.item_category, R.id.categoryName);
            categorySlidingTab.setSelectedIndicatorColors(getResources().getColor(R.color.yellowLevel1));
            categorySlidingTab.setViewPager(categoryTabViewPager);

            categorySlidingTab.setDividerColors(android.R.color.transparent);
            categorySlidingTab.setOnPageChangeListener(this);

        }

        return rootView;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        System.out.println("onPageSelected::" + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        System.out.println("onPageScrollStateChanged::" + state);
    }
}
