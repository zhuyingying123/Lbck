package com.example.lbck.fragment;


import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.lbck.EBaseFragment;
import com.example.lbck.OtherUtil;
import com.example.lbck.R;
import com.example.lbck.adapter.QuickAreaAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 快捷区域
 */
public class QuickAreaFragment extends EBaseFragment {
    private List<String> mTitle;
    private ViewPager quick_viewpager;
    private LinearLayout quick_ll;
    private MagicIndicator quick_tabs;

    @Override
    protected void initData() {
        initViewpager();
    }

    @Override
    protected void initView(View view) {
        quick_tabs = view.findViewById(R.id.quick_tabs);
        quick_ll = view.findViewById(R.id.quick_ll);
        quick_viewpager = view.findViewById(R.id.quick_viewpager);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_quick;
    }
    private void initViewpager() {
        mTitle = new ArrayList<>();
        mTitle.add("我要买");
        mTitle.add("我要卖");
        //预加载
        quick_viewpager.setOffscreenPageLimit(1);
        quick_viewpager.setAdapter(new QuickAreaAdapter(getChildFragmentManager(), tabData(), mTitle));
        OtherUtil.initTabLayout(getContext(), quick_viewpager, quick_tabs, mTitle, R.color.color_80ffffff,
                R.color.color_ffffff, R.color.color_80ffffff);
    }

    private List<Fragment> tabData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new QuickBuyFragment());
        fragmentList.add(new QuickSellFragment());
        return fragmentList;
    }
}
