package com.bingo.base.ui.ptr;

import android.view.View;

/**
 * Created by tuyx on 2017/5/3.
 * Description :
 */

public interface IPtrView {

    /**
     * 获取布局id
     * @return
     */
    int getLayoutResId();

    /**
     * 初始化布局页面
     * @param view
     */
    void initLayoutView(View view);

    /*---------- ptr ------------*/

    /**
     * 是否支持下拉刷新
     * @return
     */
    boolean pullToRefresh();

    /**
     * 是否支持自动刷新
     * @return
     */
    boolean autoRefresh();

    /**
     * 获取自定义刷新头部
     * @return
     */
    View getHeaderView();

    /**
     * 下拉刷新动作
     */
    void doRefreshTask();
}
