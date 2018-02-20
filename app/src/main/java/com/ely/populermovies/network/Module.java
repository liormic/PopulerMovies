package com.ely.populermovies.network;

import com.ely.populermovies.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lior on 2/20/18.
 */

public class Module {
  private static  Retrofit retrofit;
    private static final int TIMEOUTCONNECTINSEC = 30;

  public static Retrofit createRetrofitInstance(OkHttpClient okHttpClient) {
      if (retrofit == null) {

          return new Retrofit
                  .Builder()
                  .baseUrl(BuildConfig.BASE_URL_TMDB)
                  .addConverterFactory(GsonConverterFactory.create())
                  .client(okHttpClient)
                  .build();
      }
       return  retrofit;
   }

   public  static OkHttpClient generateOkHttpClient (CallInterceptor callInterceptor){
       HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
       httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       return new OkHttpClient.Builder().connectTimeout(TIMEOUTCONNECTINSEC, TimeUnit.SECONDS)
             .addInterceptor(httpLoggingInterceptor)
               .addInterceptor(callInterceptor)
               .build();
   }


}
