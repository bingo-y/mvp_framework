package com.bingo.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuyx on 2017/4/17.
 * Description :
 */

public class BaseActivity extends AppCompatActivity {
    private List<BasePresenter> presenters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}
