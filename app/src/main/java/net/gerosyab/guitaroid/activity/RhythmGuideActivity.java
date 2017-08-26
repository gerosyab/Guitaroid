package net.gerosyab.guitaroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import net.gerosyab.guitaroid.R;

/**
 * Created by donghe on 2017-04-03.
 */

public class RhythmGuideActivity extends AppCompatActivity {
    static {
        System.loadLibrary("test-lib");
    }

    private native String calculateArea(double radius);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhythmguide);

        TextView textView  = (TextView) findViewById(R.id.activity_rhythmguide_textview);

        textView.setText("" + calculateArea(5.5f));

    }
}
