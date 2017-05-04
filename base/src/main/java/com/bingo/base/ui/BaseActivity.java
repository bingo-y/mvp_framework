package com.bingo.base.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bingo.base.R;
import com.bingo.base.util.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuyx on 2017/4/17.
 * Description : activity基类
 */

public class BaseActivity extends AppCompatActivity implements IToolBar {

    // root view
    private ViewGroup rootView;
    // container
    private ViewGroup containerView;
    // tool bar
    private View toolbarView;

    private TextView backView;
    private TextView menuView;

    private Context mContext;

    private List<BasePresenter> presenters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mContext = this;
        rootView = (ViewGroup) findViewById(R.id.ll_root);
        containerView = (ViewGroup) findViewById(R.id.fl_container);
        rootView.setFitsSystemWindows(true);
        initToolBar();
    }

    /**
     * 设置内容布局
     * @param layoutId
     */
    public void setContentView(int layoutId) {
        LayoutInflater.from(this).inflate(layoutId, containerView);
    }

    public void registerPresenter(BasePresenter presenter) {
        if (presenter != null && !presenters.contains(presenter)) {
            presenters.add(presenter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenters != null && presenters.size() > 0) {
            for (BasePresenter p : presenters) {
                p.onDestroy();
            }
            presenters.clear();
        }
    }

    private void initToolBar() {
        if (showToolBar()) {
            Toolbar toolbar = new Toolbar(mContext);
            setSupportActionBar(toolbar);
            if (getToolBarBackgroundColor() != 0) {
                toolbar.setBackgroundColor(getToolBarBackgroundColor());
            }
            if (getToolBarBackgroundDrawable() != null) {
                toolbar.setBackgroundDrawable(getToolBarBackgroundDrawable());
            }
            int toolBarLayoutId = getToolBarLayoutId();
            if (toolBarLayoutId != 0) {
                toolbarView = View.inflate(mContext, toolBarLayoutId, toolbar);
                if (getToolBarBackgroundColor() != 0) {
                    toolbarView.setBackgroundColor(getToolBarBackgroundColor());
                }
                if (getToolBarBackgroundDrawable() != null) {
                    toolbarView.setBackgroundDrawable(getToolBarBackgroundDrawable());
                }
            }
            initBackView();
            initTitle();
            initMenu();
            rootView.addView(toolbar, 0);
        }
    }


    public View getToolBarView() {
        return toolbarView;
    }

    @Override
    public boolean showToolBar() {
        //默认使用toolbar
        return true;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.view_common_toolbar;
    }

    @Override
    public int getToolBarBackgroundColor() {
        return ContextCompat.getColor(this, R.color.app_white);
    }

    @Override
    public Drawable getToolBarBackgroundDrawable() {
        return null;
    }

    /**
     * init back key
     */
    private void initBackView() {
        backView = (TextView) toolbarView.findViewById(getBackViewId());
        if (getBackLabelResId() != 0) {
            backView.setText(getBackLabelResId());
        }
        if (getBackLabelColor() != 0) {
            backView.setTextColor(getBackLabelColor());
        }

        if (getBackLabelSize() != 0) {
            backView.setTextSize(getBackLabelSize());
        }

        if (getBackIconResId() != 0) {
            Drawable icon = ResourcesCompat.getDrawable(getResources(), getBackIconResId(), getTheme());
            icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());//必须设置图片大小，否则不显示
            backView.setCompoundDrawables(icon, null, null, null);
        }
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackListener();
            }
        });
    }

    @Override
    public int getBackViewId() {
        return R.id.tv_tool_bar_back;
    }

    @Override
    public int getBackLabelResId() {
        return 0;
    }

    @Override
    public int getBackLabelSize() {
        return 0;
    }

    @Override
    public int getBackLabelColor() {
        return 0;
    }

    @Override
    public void onBackListener() {
        finish();
    }

    @Override
    public int getBackIconResId() {
        return 0;
    }

    @Override
    public void setBackVisible() {
        if (backView != null) {
            backView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setBackInvisible() {
        if (backView != null) {
            backView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * init title
     */
    private void initTitle() {
        TextView title = ((TextView) toolbarView.findViewById(getTitleViewId()));
        if (getTitleLabelResId() != 0) {
            title.setText(getTitleLabelResId());
        }
        if (getTitleLabelColorResId() != 0) {
            title.setTextColor(getTitleLabelColorResId());
        }
        title.setSingleLine(true);
        title.setEllipsize(TextUtils.TruncateAt.END);
        title.setPadding(PixelUtil.dp2px(mContext, 14), 0, PixelUtil.dp2px(mContext, 14), 0);
    }

    @Override
    public int getTitleViewId() {
        return R.id.tv_tool_bar_title;
    }

    @Override
    public int getTitleLabelResId() {
        return R.string.null_string;
    }

    @Override
    public int getTitleLabelColorResId() {
        return 0;
    }

    @Override
    public int getTitleLabelSize() {
        return 0;
    }

    private void initMenu() {
        menuView = (TextView) toolbarView.findViewById(getMenuViewId());
        if (getMenuLabelResId() != 0) {
            menuView.setText(getMenuLabelResId());
        }
        if (getMenuLabelColorResId() != 0) {
            menuView.setTextColor(getMenuLabelColorResId());
        }
        if (getMenuLabelSize() != 0) {
            menuView.setTextSize(getMenuLabelSize());
        }

        if (getMenuIconResId() != 0) {
            menuView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(mContext, getMenuIconResId()), null);
        }

        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenuClick();
            }
        });

    }

    @Override
    public int getMenuViewId() {
        return R.id.tv_tool_bar_menu;
    }

    @Override
    public int getMenuLabelResId() {
        return 0;
    }

    @Override
    public int getMenuLabelColorResId() {
        return 0;
    }

    @Override
    public int getMenuLabelSize() {
        return 0;
    }

    @Override
    public int getMenuIconResId() {
        return 0;
    }

    @Override
    public void onMenuClick() {

    }

    /**
     * 设置菜单可见
     */
    @Override
    public void setMenuVisible() {
        if (menuView != null) {
            menuView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setMenuInVisible() {
        if (menuView != null) {
            menuView.setVisibility(View.INVISIBLE);
        }
    }
}
