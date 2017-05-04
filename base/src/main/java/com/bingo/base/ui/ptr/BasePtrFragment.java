package com.bingo.base.ui.ptr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bingo.base.R;
import com.bingo.base.ui.BaseFragment;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by tuyx on 2017/5/3.
 * Description : 带下拉刷新功能的fragment基类
 */

public abstract class BasePtrFragment extends BaseFragment implements IPtrView {

    //带下拉刷新布局
    PtrClassicFrameLayout pflRefreshFrame;
    //内容展示布局
    FrameLayout flContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_ptr, container, false);
        pflRefreshFrame = (PtrClassicFrameLayout) view.findViewById(R.id.pfl_refresh_frame);
        flContainer = (FrameLayout) view.findViewById(R.id.fl_container);

        if (getLayoutResId() != 0) {
            View contentView = inflater.inflate(getLayoutResId(), flContainer);

            initLayoutView(contentView);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPtrFrame();
    }

    /**
     * 初始化刷新控件参数
     */
    private void initPtrFrame() {
        /**
         * 自定义头部
         */
        if (getHeaderView() != null) {
            pflRefreshFrame.setHeaderView(getHeaderView());
        }

        pflRefreshFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doRefreshTask();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, flContainer, header);
            }
        });
        // default is false
        pflRefreshFrame.setPullToRefresh(pullToRefresh());
        pflRefreshFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                pflRefreshFrame.autoRefresh(autoRefresh());
            }
        }, 100);
    }

    @Override
    public boolean pullToRefresh() {
        return true;
    }

    @Override
    public boolean autoRefresh() {
        return false;
    }

    @Override
    public View getHeaderView() {
        return null;
    }
}
