package com.faysal.coronalibsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faysal.coronaoutbreak.Corona;
import com.faysal.coronaoutbreak.models.Response;
import com.faysal.coronaoutbreak.utils.TotalOutbreakListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="MainActivity";

    Button getResultBtn;
    TextView resultView;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getResultBtn=findViewById(R.id.getResultBtn);
        resultView=findViewById(R.id.resultView);



        getResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog=new ProgressDialog(MainActivity.this);
                dialog.setMessage("Please wait....");
                dialog.show();
               if (checkInternet()){
                       Corona.setTotalOutbreakListener(MainActivity.this,new TotalOutbreakListener() {
                           @Override
                           public void success(Response response) {
                               Log.d(TAG, "success: "+response.getMessage());
                               if (response.isSuccess()){
                                   resultView.setText(
                                           "Coronavirus Cases : "+response.getOutbreak().getTotalCases()+"\n"+
                                           "Deaths : "+response.getOutbreak().getTotalDeaths()+"\n"+
                                           "Recovered : "+response.getOutbreak().getTotalRecovered()


                                   );

                                   Log.d(TAG, "success: ");

                                   Log.d(TAG, "Coronavirus Cases : "+response.getOutbreak().getTotalCases());
                                   Log.d(TAG,    "Deaths : "+response.getOutbreak().getTotalDeaths());
                                   Log.d(TAG, "Recovered : "+response.getOutbreak().getTotalRecovered());
                               }

                               dialog.dismiss();
                           }

                           @Override
                           public void failed(String errorMessage) {
                               Log.d(TAG, "failed: "+errorMessage);
                               dialog.dismiss();
                           }



                       });
               }else {
                   Toast.makeText(MainActivity.this, "Internet Connection not available", Toast.LENGTH_SHORT).show();
               }
            }
        });





    }

    private boolean checkInternet(){
        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }


}
