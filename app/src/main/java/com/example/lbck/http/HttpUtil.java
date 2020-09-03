package com.example.lbck.http;

import android.content.Context;

import org.json.JSONObject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 针对服务器返回的结果进行封装处理
 */
public class HttpUtil {
    private volatile static HttpUtil instance;

    private HttpUtil() {

    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }

    public <T> void toSubscribe(final Context mContext, Flowable<Entity<T>> flowable, RxSubscriber<Entity<T>> rxSubscriber) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Entity<T>>() {
                    @Override
                    public boolean test(@NonNull Entity<T> mData) throws Exception {
                        boolean isFilter = false;
                        if (mData == null) {

                        } else if (mData.getCode() == 200) {
                            isFilter = true;
                        } else {
                            isFilter = true;
                        }
                        return isFilter;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Entity<T>, Entity<T>>() {
                    @Override
                    public Entity<T> apply(@NonNull Entity<T> mData) throws Exception {
                        return mData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxSubscriber);
    }

    public <T> void toSubscribes(final Context mContext, Flowable<T> flowable, RxSubscriber<T> rxSubscriber) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<T>() {
                    @Override
                    public boolean test(@NonNull T mData) throws Exception {
                        boolean isFilter = false;
                        JSONObject jsonObject = new JSONObject(mData.toString());
                        int code = jsonObject.getInt("code");
                        if (mData == null) {

                        } else if (code == 200) {
                            isFilter = true;
                        } else {
                            isFilter = true;
                        }
                        return isFilter;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<T, T>() {
                    @Override
                    public T apply(@NonNull T mData) throws Exception {
                        return mData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxSubscriber);
    }

    public void toSubscribeNoGson(final Context mContext, Flowable<String> flowable, RxSubscriber<String> rxSubscriber) {

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String mData) throws Exception {
//                        BaseActivity.onLoadEnd();
                        boolean isFilter = false;
                        if (mData == null) {
                            return isFilter;
                        }
                        JSONObject jsonObject = new JSONObject(mData);
                        String status = jsonObject.optString("code");
                        String msg = jsonObject.optString("resultMsg");
                        if ("200".equals(status)) { //成功
                            isFilter = true;
                        } else {
                            isFilter = true;
                        }
                        return isFilter;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String mData) throws Exception {
                        return mData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rxSubscriber);
    }
}
