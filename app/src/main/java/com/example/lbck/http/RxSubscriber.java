package com.example.lbck.http;

import io.reactivex.subscribers.DefaultSubscriber;

/**
 * Created by gk-swj on 2017/9/22.
 * 返回成功和失败的回调
 */

public abstract class RxSubscriber<T> extends DefaultSubscriber<T> {

    @Override
    public void onComplete() {
        cancel();
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        _onError(t.toString());
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }


    public abstract void _onNext(T t);

    public abstract void _onError(String msg);

    public void _onCancel() {
        cancel();
    }


}
