package com.faysal.coronaoutbreak;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.faysal.coronaoutbreak.models.ReportByCountry;
import com.faysal.coronaoutbreak.models.Response;
import com.faysal.coronaoutbreak.models.TotalOutbreak;
import com.faysal.coronaoutbreak.utils.Constants;
import com.faysal.coronaoutbreak.utils.TotalOutbreakListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<ReportByCountry> coutryData;



        public void setTotalOutbreakListener(TotalOutbreakListener listener) {
            this.listener = listener;
            totalResponse=new Response();
            coutryData=new ArrayList<>();
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

                StringBuilder listBuilder=new StringBuilder();
                listBuilder.append("div.row ");
                listBuilder.append("div.col-md-8 ");
                listBuilder.append("div.tab-content ");

                doc=repose.parse();
                Elements formElement = doc.select(builder.toString());
                Elements listElement = doc.select(listBuilder.toString());
                Element table = listElement.select("table").get(0);
                Elements rows = table.select("tr");

                TotalOutbreak outbreak=new TotalOutbreak();
                outbreak.setTotalCases(formElement.get(0).text());
                outbreak.setTotalDeaths(formElement.get(1).text());
                outbreak.setTotalRecovered(formElement.get(2).text());

                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    Elements cols = row.select("td");

                    ReportByCountry country=new ReportByCountry();
                    country.setCountryName(cols.get(0).text());
                    country.setTotalCases(cols.get(1).text());
                    country.setNewCases(cols.get(2).text());
                    country.setTotalDeaths(cols.get(3).text());
                    country.setNewDeaths(cols.get(4).text());
                    country.setTotalRecovered(cols.get(5).text());
                    country.setActiveCases(cols.get(6).text());
                    country.setSeriousCritical(cols.get(7).text());
                    country.setTopCases(cols.get(8).text());

                    coutryData.add(country);
                }

                if (outbreak !=null && coutryData !=null && coutryData.size() !=0){
                    totalResponse.setSuccess(true);
                    totalResponse.setMessage(ct.getString(R.string.success));
                    totalResponse.setOutbreak(outbreak);
                    totalResponse.setReportByCountry(coutryData);
                }else {
                    totalResponse.setSuccess(false);
                    totalResponse.setMessage(ct.getString(R.string.failed));
                    totalResponse.setOutbreak(null);
                    totalResponse.setReportByCountry(null);

                }


                return totalResponse;


            } catch (IOException e) {
                e.printStackTrace();
                totalResponse.setSuccess(false);
                totalResponse.setMessage(e.getMessage().toString());
                totalResponse.setOutbreak(null);
                totalResponse.setReportByCountry(null);
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
