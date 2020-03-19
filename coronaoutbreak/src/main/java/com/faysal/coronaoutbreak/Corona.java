package com.faysal.coronaoutbreak;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.faysal.coronaoutbreak.models.Response;
import com.faysal.coronaoutbreak.models.TotalOutbreak;
import com.faysal.coronaoutbreak.utils.Constants;
import com.faysal.coronaoutbreak.utils.TotalOutbreakListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Corona {

    public static final String TAG=Corona.class.getSimpleName();
    private static Context ct;


   public static void setTotalOutbreakListener(Context context, TotalOutbreakListener listener){
       ct=context;
       TotalOutBreakTask breakTask = new TotalOutBreakTask();
       breakTask.execute();
       breakTask.setTotalOutbreakListener(listener);
   }

    public static class TotalOutBreakTask extends AsyncTask<Void,Void, Response> {
        Response totalResponse;
        TotalOutbreakListener listener;



        public void setTotalOutbreakListener(TotalOutbreakListener listener) {
            this.listener = listener;
            totalResponse=new Response();
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Connection.Response repose = null;
            Document doc;
            try {
                repose = Jsoup.connect(Constants.SERVER_URL)
                        .userAgent(Constants.USER_AGENT)
                        .followRedirects(false)
                        .execute();;

                StringBuilder builder = new StringBuilder();
                builder.append("div.container ");
                builder.append("div.row  ");
                builder.append("div.col-md-8 ");
                builder.append("div.content-inner ");
                builder.append("div.maincounter-number ");

                doc=repose.parse();
                Elements formElement = doc.select(builder.toString());

                TotalOutbreak outbreak=new TotalOutbreak();
                outbreak.setTotalCases(formElement.get(0).text());
                outbreak.setTotalDeaths(formElement.get(1).text());
                outbreak.setTotalRecovered(formElement.get(2).text());

                if (outbreak !=null){
                    totalResponse.setSuccess(true);
                    totalResponse.setMessage(ct.getString(R.string.success));
                    totalResponse.setOutbreak(outbreak);
                }else {
                    totalResponse.setSuccess(false);
                    totalResponse.setMessage(ct.getString(R.string.failed));
                    totalResponse.setOutbreak(null);

                }

                return totalResponse;


            } catch (IOException e) {
                e.printStackTrace();
                totalResponse.setSuccess(false);
                totalResponse.setMessage(e.getMessage().toString());
                totalResponse.setOutbreak(null);
                return totalResponse;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            if (response !=null && response.isSuccess()){
                listener.success(response);
            }else {
                listener.failed(response.getMessage());
            }
        }
    }




    public static boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)ct.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            return false;
        }
    }

}
