package com.jerey.rxjavademo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Xiamin on 2017/1/14.
 */

public interface IRetrofitService {
    @GET("/")
    Observable<String> getTestHtmlString();


}
