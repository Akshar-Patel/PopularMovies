package com.example.ab.popularmovies.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

  public static void openYoutubeVideo(Context context, String id) {
    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
    Intent webIntent = new Intent(Intent.ACTION_VIEW,
        Uri.parse("http://www.youtube.com/watch?v=" + id));
    try {
      context.startActivity(appIntent);
    } catch (ActivityNotFoundException ex) {
      context.startActivity(webIntent);
    }
  }
}
