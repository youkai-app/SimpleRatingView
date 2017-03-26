package app.youkai.simpleratingview.sample;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import app.youkai.simpleratingview.SimpleRatingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView currentRating = (TextView) findViewById(R.id.currentRating);

        SimpleRatingView simpleRatingView = (SimpleRatingView) findViewById(R.id.simpleRatingView);
        simpleRatingView.setListener(new SimpleRatingView.OnRatingSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onRatingSelected(SimpleRatingView.Rating rating) {
                currentRating.setText("Current rating: " + getString(rating.getStringRes()));
            }
        });
    }
}
