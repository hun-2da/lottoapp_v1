package yeong.wish.lotto.menuFragmentPage.FateFP;


import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InternetAccess {
    static RequestQueue requestQueue;
    Context context;
    public InternetAccess(Context context){
        /*if(requestQueue == null){

        }*/
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);


    }
    public void makeRequest(String url,Boolean lotto){
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //println(response);
                //textView.setText(response);
                if(lotto)
                    new Crawling645().parseLottoHtml(response);
                else
                    new Crawling720().parseLottoHtml(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("에러 발생");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);

    }
    public void println(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


}
