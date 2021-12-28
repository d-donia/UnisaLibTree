package presenter.utentepresenter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import model.utentemanagement.Utente;

public class UtentePresenter {
    RequestParams params;
    AsyncHttpClient client;
    String MYURL="http://192.168.1.61:8080/UnisaLIBServer/UtentePresenter/login";

    public void login(String email, String password) {
        System.out.println("ok");
        params=new RequestParams();
        params.put("email",email);
        params.put("pass",password);
        client=new AsyncHttpClient();
        client.post(MYURL,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Toast.makeText(LoginActivity., "Submit Success "+response, Toast.LENGTH_SHORT).show();
                Utente u= null;
                try {
                    u = Utente.fromJson(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(u.getEmail());
                System.out.println(u.getPassword());
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                //Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
