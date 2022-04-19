package com.zuture.downloaderfbnew.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.zuture.downloaderfbnew.utils.iUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.zuture.downloaderfbnew.utils.Constants.DOWNLOADING_MSG;
import static com.zuture.downloaderfbnew.utils.Constants.WEB_DISABLE;
import static com.zuture.downloaderfbnew.utils.Constants.WENT_WRONG;


public class downloadVideo {

    private static Context Mcontext;
    private  static ProgressDialog pd;
    private static  String Title;

    private static Boolean fromService;


    public  static void Start(final Context context , String url ,  Boolean service  ){

        Mcontext=context;
        fromService = service;

        //SessionID=title;
        if(!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        if(!fromService) {
            pd = new ProgressDialog(context);
            pd.setMessage(DOWNLOADING_MSG);
            pd.setCancelable(false);
            pd.show();
        }
        if(url.contains("tiktok.com")){

            new GetTikTokVideo().execute(url);
        } else if (url.contains("facebook.com")){

            //String[] Furl = url.split("/");
            // url = Furl[Furl.length-1];
            //iUtils.ShowToast(Mcontext,Furl[Furl.length-1]);
            new GetFacebookVideo().execute(url);
        }else if (url.contains("instagram.com")){

            new GetInstagramVideo().execute(url);
        }else{
            if(!fromService) {
                pd.dismiss();

                iUtils.ShowToast(Mcontext,WEB_DISABLE);
            }
        }

        //iUtils.ShowToast(Mcontext,url);
        //iUtils.ShowToast(Mcontext,SessionID);


        SharedPreferences prefs = Mcontext.getSharedPreferences("AppConfig", MODE_PRIVATE);
    }


    //     private static class GetTikTokVideo extends AsyncTask<String, Void, Document> {
//         Document doc;
//
//         @Override
//         protected Document doInBackground(String... urls) {
//             try {
//                 doc = Jsoup.connect(urls[0]).get();
//             } catch (IOException e) {
//                 e.printStackTrace();
//                 Log.d(TAG, "doInBackground: Error");
//             }
//             return doc;
//          }
//
//         protected void onPostExecute(Document result) {
//            // pd.dismiss();
//        //     Log.d("GetResult", );
//               try {
//                 String URL = result.select("link[rel=\"canonical\"]").last().attr("href");
//
//                 if(!URL.equals("") && URL.contains("video/")){
//                     URL  =URL.split("video/")[1];
//                     Title = result.title();
//                     //  iUtils.ShowToast(Mcontext,URL);
//                      new DownloadTikTokVideo().execute(URL);
//                 }else{
//                     if(!fromService) {
//
//                         pd.dismiss();
//                     }
//                     iUtils.ShowToast(Mcontext,WENT_WRONG);
//                 }
//
//             } catch (NullPointerException e) {
//                             e.printStackTrace();
//                   if(!fromService) {
//
//                       pd.dismiss();
//                   }
//                             iUtils.ShowToast(Mcontext,WENT_WRONG);
//                   }
//
//
//         }
//}
    private static class GetTikTokVideo extends AsyncTask<String, Void, Document> {
        Document doc;

        @Override
        protected Document doInBackground(String... urls) {
            try {
                doc = Jsoup.connect(urls[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Error");
            }
            return doc;
        }

        protected void onPostExecute(Document result) {
            pd.dismiss();
            //     Log.d("GetResult", );

            try {
                String URL = result.select("script[id=\"videoObject\"]").last().html();
                String URL1 = result.select("script[id=\"__NEXT_DATA__\"]").last().html();
                if(!URL.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(URL);
                        JSONObject jsonObject1 = new JSONObject(URL1);
                        //Log.d("GetResult_Content",jsonObject1.getJSONObject("props").getJSONObject("pageProps").getJSONObject("videoData").getJSONObject("itemInfos").getJSONObject("video").getJSONArray("urls").getString(0).toString());
                        // Log.d("GetResult_URL",jsonObject.getString("contentUrl"));
                        Title = result.title();
                        //  iUtils.ShowToast(Mcontext,URL);
                        new downloadFile().Downloading(Mcontext,jsonObject.getString("contentUrl"),Title,".mp4");

                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                    }
                    // iUtils.ShowToast(Mcontext,URL);
                }else{
                    if(!fromService) {

                        pd.dismiss();
                    }
                    iUtils.ShowToast(Mcontext,WENT_WRONG);
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
                if(!fromService) {

                    pd.dismiss();
                }
                iUtils.ShowToast(Mcontext,WENT_WRONG);
            }


        }
    }
    private static class GetFacebookVideo extends AsyncTask<String, Void, Document> {
        Document doc;

        @Override
        protected Document doInBackground(String... urls) {
            try {

                //doc = Jsoup.connect(FacebookApi).data("v",urls[0]).get();
                doc = Jsoup.connect(urls[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Error");
                iUtils.ShowToast(Mcontext,WENT_WRONG);

            }
            return doc;

        }

        protected void onPostExecute(Document result) {
            if(!fromService) {

                pd.dismiss();
            }
            //     Log.d("GetResult", );
            try {
                String URL = result.select("meta[property=\"og:video\"]").last().attr("content");
                Title = result.title();
                //  iUtils.ShowToast(Mcontext,URL);
                new downloadFile().Downloading(Mcontext,URL,Title,".mp4");
            } catch (NullPointerException e)
            {
                e.printStackTrace();
                iUtils.ShowToast(Mcontext,WENT_WRONG);
            }
            //  new DownloadTikTokVideo().execute(URL);

        }
    }

    private static class GetInstagramVideo extends AsyncTask<String, Void, Document> {
        Document doc;

        @Override
        protected Document doInBackground(String... urls) {
            try {
                doc = Jsoup.connect(urls[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: Error");
            }
            return doc;

        }

        protected void onPostExecute(Document result) {
            if(!fromService) {

                pd.dismiss();}
            //     Log.d("GetResult", );
            try {
                String URL = result.select("meta[property=\"og:video\"]").last().attr("content");
                Title = result.title();
                //iUtils.ShowToast(Mcontext, URL);

                new downloadFile().Downloading(Mcontext, URL, Title, ".mp4");
            }catch (NullPointerException e)
            {
                e.printStackTrace();
                iUtils.ShowToast(Mcontext,WENT_WRONG);
            }
        }
    }
}
