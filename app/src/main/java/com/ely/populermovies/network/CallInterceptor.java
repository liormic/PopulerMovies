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

class CallInterceptor implements Interceptor {


    public CallInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request sourceRequest = chain.request();
        HttpUrl sourceHttpUrl = sourceRequest.url();
        HttpUrl httpUrl = sourceHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY_TMDB)
                .build();

        Request request = sourceRequest.newBuilder().url(httpUrl).build();
        return  chain.proceed(request);
    }
}
