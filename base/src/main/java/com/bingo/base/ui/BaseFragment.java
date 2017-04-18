package com.bingo.base.ui;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuyx on 2017/4/17.
 * Description :
 */

public class BaseFragment extends Fragment {
    private List<BasePresenter> presenters = new ArrayList<>();

    public void registerPresenter(BasePresenter presenter) {
        if (presenter != null && !presenters.contains(presenter)) {
            presenters.add(presenter);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenters != null && presenters.size() > 0) {
            for (BasePresenter p : presenters) {
                p.onDestroy();
            }
            presenters.clear();
        }
    }
}
