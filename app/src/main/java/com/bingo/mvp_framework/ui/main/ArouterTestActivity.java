package com.bingo.mvp_framework.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bingo.base.data.prefernce.PreferencesManager;
import com.bingo.base.ui.BaseActivity;
import com.bingo.mvp_framework.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tuyx on 2017/4/19.
 * Description :
 */
@Route(path = "/mvp/test")
public class ArouterTestActivity extends BaseActivity {

    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492774114627&di=3f2b7fe871cda7162cb3212152654180&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3Db933601731fa828bd1239debcd1f41cd%2F0ef41bd5ad6eddc454d750e93bdbb6fd526633eb.jpg";

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_content)
    TextView tvContent;

    CompositeDisposable disposables = new CompositeDisposable();
    @BindView(R.id.tv_another)
    TextView tvAnother;
    @BindView(R.id.iv_glide)
    ImageView ivGlide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_test);
        ButterKnife.bind(this);
        Glide.with(this)
                .load(url)
                .into(ivGlide);
    }

    @OnClick({R.id.tv_save, R.id.tv_get, R.id.tv_another})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                PreferencesManager.getInstance().getRxString("input").set(etInput.getText().toString());
                break;
            case R.id.tv_get:
                disposables.add(PreferencesManager.getInstance().getRxString("input")
                        .asObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String s) throws Exception {
                                tvContent.setText(s);
                            }
                        }));
                break;
            case R.id.tv_another:
                PreferencesManager.getInstance().getRxString("bingo").set("tyx");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposables.dispose();
    }
}
