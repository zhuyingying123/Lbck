package com.example.lbck.http;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil {
    private volatile static RetrofitUtil INSTANCE;
    private ApiService mApiServiceGson;
    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    private ApiService mApiServiceStr;
    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 30;

    public static RetrofitUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 使用Gson解析
     *
     * @return
     */
    public ApiService getServiceWithGson(Context mContext) {
        if (mApiServiceGson == null) {
            mApiServiceGson = new Retrofit.Builder()
                    .baseUrl(Common.URL)
                    .client(getClient(mContext))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(ApiService.class);
        }

        return mApiServiceGson;
    }

    /**
     * 不使用Gson解析
     *
     * @return
     */
    public ApiService getServiceWithStr(Context mContext) {
        if (mApiServiceStr == null) {
            mApiServiceStr = new Retrofit.Builder()
                    .baseUrl(Common.URL)
                    .client(getClient(mContext))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(ApiService.class);
        }
        return mApiServiceStr;
    }

    private OkHttpClient getClient(final Context mContext) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//错误重联
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder builder = request.newBuilder();
                        Request newRequest = builder.method(request.method(), request.body())
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();

        return client;
    }

//    /**
//     * 每个接口必传参数
//     *
//     * @return
//     */
//    public Map getValue() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("token", UserInfoPreferencesUtils.getToken());
//        map.put("device", 20);
//        return map;
//    }
//
//    /**
//     * 上传多张图片
//     *
//     * @param imageItems
//     * @param rxSubscriber pathType 图片类型 退货 9
//     */
//    public Flowable<String> upEvidenceImg(Context mContext, String picflag, String fileflag, String url, List<Photo> imageItems, RxSubscriber<String> rxSubscriber) {
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);//表单类型
//        //网络图片
//        List<String> list = new ArrayList<>();
//        if (imageItems != null && imageItems.size() != 0) {
//            for (int i = 0; i < imageItems.size(); i++) {
//                if (!TextUtils.isEmpty(imageItems.get(i).getUri().toString())) {
//                    if (imageItems.get(i).getUri().toString().contains("http") || imageItems.get(i).getUri().toString().contains("https")) {
//                        list.add(imageItems.get(i).getUri().toString());
//                    } else {
////                        File file = new File(imageItems.get(i).getPath());//filePath 图片地址
//                        File file = uriConvertFile(mContext, Uri.parse(imageItems.get(i).getUri().toString()));
//                        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                        builder.addFormDataPart(fileflag, file.getName()+".jpg", imageBody);//"imgfile"+i 后台接收图片流的参数名
//                    }
//                }
//            }
//            builder.addFormDataPart("urlList", JSON.toJSONString(list));
//            builder.addFormDataPart("pathType", picflag);
//        }
//        List<MultipartBody.Part> parts = builder.build().parts();
//        Flowable<String> flowable = getServiceWithStr(mContext).upLoadImages(url, parts);
//        flowable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(rxSubscriber);
//        return flowable;
//    }
//
//    /**
//     * 评价
//     * 上传多张图片加文字
//     */
//    public Flowable<String> upImg(int orderId, Context mContext, String url, List<OrderDetailModel.DataBean.OrderDetailsBean> list,
//                                  RxSubscriber<String> rxSubscriber) {
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);//表单类型
//        for (int i = 0; i < list.size(); i++) {
//            for (int j = 0; j < list.get(i).getEvaluathUrl().size(); j++) {
//                if (!TextUtils.isEmpty(list.get(i).getEvaluathUrl().get(j).getUri().toString())) {
////                    File file = new File(list.get(i).getEvaluathUrl().get(j).getUri().toString());//filePath 图片地址
//                    File file = uriConvertFile(mContext, Uri.parse(list.get(i).getEvaluathUrl().get(j).getUri().toString()));
//                    RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    builder.addFormDataPart(list.get(i).getOrderDetailId() + "", file.getName()+".jpg", imageBody);
//                }
//            }
//        }
//        Map map = new HashMap();
//        map.put("userId", UserInfoPreferencesUtils.getUserId());
//        map.put("evaluateList", list);
//        map.put("orderId", orderId);
//        builder.addFormDataPart("evaluateMap", JSON.toJSONString(map));
//
//        List<MultipartBody.Part> parts = builder.build().parts();
//        Flowable<String> flowable = getServiceWithStr(mContext).upLoadImages(url, parts);
//        flowable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(rxSubscriber);
//        return flowable;
//    }
}
