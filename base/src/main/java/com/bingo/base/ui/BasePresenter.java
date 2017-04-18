package com.bingo.base.ui;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by tuyx on 2017/4/17.
 * Description :
 */

public class BasePresenter<V extends IView> {
    private V view;

    public CompositeDisposable disposables;

    public BasePresenter(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    /**
     * RxJava注册
     * @param disposable
     */
    public void subscribe(@NonNull Disposable disposable) {
        if (disposables == null) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    /**
     * RxJava取消注册，避免内存泄漏
     */
    public void unsubscribe() {
        if (disposables != null) {
            disposables.dispose();
        }
    }

    public void onStart() {

    }

    public void onDestroy() {
        //cancel request
        unsubscribe();
        view = null;
        disposables = null;
    }

}