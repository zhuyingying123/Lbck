package com.example.lbck.http;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    @FormUrlEncoded
    @POST
    Flowable<String> getSelectInfo(@Url String url, @FieldMap Map<String, Object> map);

    @GET
    Flowable<String> getTaskInfo(@Url String url, @QueryMap Map<String, Object> map);

    @GET
    Flowable<String> getMyTaskInfo(@Url String url);

}
