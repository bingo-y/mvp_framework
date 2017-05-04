package com.bingo.base.ui;

import android.graphics.drawable.Drawable;

/**
 * Created by tuyx on 2017/4/17.
 * Description : 通用工具栏定义
 */
public interface IToolBar {

    /**
     * 是否显示toolbar，默认显示
     *
     * @return
     */
    boolean showToolBar();


    /**
     * 是否要替换掉toolbar目前的布局，是的话，重写这个方法
     *
     * @return
     */
    int getToolBarLayoutId();

    /**
     * toolbar背景颜色
     *
     * @return
     */
    int getToolBarBackgroundColor();

    /**
     * toolbar背景
     *
     * @return
     */
    Drawable getToolBarBackgroundDrawable();


    /**
     * 无需重写这个方法，除非重置了整个toolbar layout
     *
     * @return
     */
    int getBackViewId();

    /**
     * 设置back文字内容（国际化，客制化back时需要使用）
     *
     * @return
     */
    int getBackLabelResId();

    /**
     * back key字体大小
     *
     * @return
     */
    int getBackLabelSize();

    /**
     * back key color
     *
     * @return
     */
    int getBackLabelColor();


    /**
     * set back key action, default behaviour is doing finish().
     */
    void onBackListener();


    /**
     * 设置back icon，获取back key的icon
     *
     * @return
     */
    int getBackIconResId();

    /**
     * 显示返回按钮
     */
    void setBackVisible();

    /**
     * 隐藏返回按钮
     */
    void setBackInvisible();


    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置标题，无需重写这个方法，除非重置了整个toolbar layout
     *
     * @return
     */
    int getTitleViewId();

    /**
     * 设置title，获取title的resource id
     *
     * @return
     */
    int getTitleLabelResId();

    /**
     * 设置title的文字颜色
     *
     * @return
     */
    int getTitleLabelColorResId();

    /**
     * 设置title的文字大小
     *
     * @return
     */
    int getTitleLabelSize();


    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 无需重写这个方法，除非重置了整个toolbar layout
     *
     * @return
     */
    int getMenuViewId();

    /**
     * menu 文本的resource id
     *
     * @return
     */
    int getMenuLabelResId();

    /**
     * 设置menu color resource id
     *
     * @return
     */
    int getMenuLabelColorResId();

    /**
     * 设置menu text size
     *
     * @return
     */
    int getMenuLabelSize();

    /**
     * 设置menu icon，获取menu的resource id
     *
     * @return
     */
    int getMenuIconResId();

    /**
     * menu点击事件
     */
    void onMenuClick();

    void setMenuVisible();

    void setMenuInVisible();

}
