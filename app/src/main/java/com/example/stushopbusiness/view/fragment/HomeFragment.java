package com.example.stushopbusiness.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.stushopbusiness.R;
import com.example.stushopbusiness.adapter.FragmentAdapter;
import com.example.stushopbusiness.base.BaseFragment;
import com.example.stushopbusiness.presenter.EmptyPresenter;
import com.example.stushopbusiness.util.RouterUtil;
import com.example.stushopbusiness.weight.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterUtil.HomePage)
public class HomeFragment extends BaseFragment<EmptyPresenter> {
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    private String[] title = {"已支付订单", "已发货订单", "待评价订单"};
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        fragmentList.add(new NeedPaymentFragment());
        fragmentList.add(new NeedDeliverFragment());
        fragmentList.add(new FinishDeliverFragment());
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragmentList);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title == null ? 0 : title.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(title[index]);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setMaxCircleRadius(10);
                indicator.setColors(Color.parseColor("#ffd321"), Color.parseColor("#ffd321"), Color.parseColor("#ffd321"));
                return indicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        viewPager.setAdapter(fragmentAdapter);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public EmptyPresenter initPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
