package com.bingo.mvp_framework.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bingo.base.ui.BaseActivity;
import com.bingo.mvp_framework.R;

/**
 * Created by tuyx on 2017/4/19.
 * Description :
 */
@Route(path = "/mvp/test")
public class ArouterTestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_test);
    }
}
