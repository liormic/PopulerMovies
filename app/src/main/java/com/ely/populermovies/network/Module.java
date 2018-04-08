package com.ely.populermovies.network;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class  Module {
    private static  Retrofit retrofit;
    private static final int TIMEOUTCONNECTINSEC = 100;

    private static final String BASE_URL_TMDB = "https://api.themoviedb.org";

    Interceptor requestInterceptor(CallInterceptor interceptor) {
        return interceptor;
    }


  public static Retrofit createRetrofitInstance(OkHttpClient okHttpClient) {
      if (retrofit == null) {

          return new Retrofit
                  .Builder()
                  .baseUrl(BASE_URL_TMDB)
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
