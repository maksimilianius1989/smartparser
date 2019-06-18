package app.vimax.smartparser;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvShowResult;
    EditText etInsertUrl;
    EditText etSelector;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShowResult = (TextView) findViewById(R.id.tvShowResult);
        tvShowResult.setMovementMethod(new ScrollingMovementMethod());
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etInsertUrl = (EditText) findViewById(R.id.etInsertUrl);
        etSelector = (EditText) findViewById(R.id.etSelector);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MyTask mt = new MyTask();
        mt.execute();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        String title;

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;
            String url = etInsertUrl.getText().toString();
            String selector = etSelector.getText().toString();
            try {

                Connection con;
                con = Jsoup.connect(url);
                con.ignoreContentType(true);
                con.maxBodySize(100000000);
                con.timeout(60000);
                doc = con.get();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (selector.isEmpty()) {
                title = doc.html();
            } else {
                Elements elements = doc.select(selector);
                title = elements.toString();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            tvShowResult.setText(title);
        }
    }
}
