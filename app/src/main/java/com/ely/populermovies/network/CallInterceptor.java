package com.ely.populermovies.network;

import com.ely.populermovies.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lior on 2/21/18.
 */

public class CallInterceptor implements Interceptor {
    private static final String API_KEI = "4531ce4ec6bedafe51c581bfe425632c";

    public CallInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request sourceRequest = chain.request();
        HttpUrl sourceHttpUrl = sourceRequest.url();
        HttpUrl httpUrl = sourceHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.MY_MOVIE_DB_API_KEY)
                .build();

        Request request = sourceRequest.newBuilder().url(httpUrl).build();
        return  chain.proceed(request);
    }
}
