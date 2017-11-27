package com.example.ab.popularmovies.util;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {

  public static String getResponseFromHttpUrl(String url) throws IOException {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();

    Response response = client.newCall(request).execute();
    //noinspection ConstantConditions
    return response.body().string();
  }
}
