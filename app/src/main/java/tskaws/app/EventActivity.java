package tskaws.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jason on 6/5/17.
 */

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
        Log.d(TAG, "About to start Activity");

        // Get the Intent that started this activity and extract the event
        Intent intent = getIntent();
        EventItem event = (EventItem) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        Log.d(TAG, "Received Intent and made an event " + event.getTitle());

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if (event.getImageUrl() != null) {
            new DownloadImage(imageView).execute(event.getImageUrl());
        }

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(event.getTitle());

        Format formatter = new SimpleDateFormat("MMMM dd, yyyy");
        String theDate = formatter.format(event.getDate());
        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setText(theDate);

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(event.getDescription());

        Log.d(TAG, "Got to the end Activity");

    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public DownloadImage(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;

            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }

}