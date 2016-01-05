package verkstad.com.in.browser;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import android.app.SearchManager;
import java.io.IOException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {
    WebView webView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("ping -c 1 www.google.com");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace();
        }
        catch (InterruptedException e) { e.printStackTrace();

        }

        return false;
    }

    public void search(View v){
        if(isOnline()){
            editText= (EditText) findViewById(R.id.editText);
            String et = editText.getText().toString();
            webView= (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            final Activity activity = this;

           webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                }
            });



           if(Patterns.WEB_URL.matcher(et).matches()){
               webView.loadUrl(et);
           }
            else{
           webView.loadUrl("https://www.google.com/search?q="+et);
           }
        }
        else{
            Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
