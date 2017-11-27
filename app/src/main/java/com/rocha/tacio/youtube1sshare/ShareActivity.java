package com.rocha.tacio.youtube1sshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the intent that started this activity
        Intent intent = getIntent();
        String sharedLink = intent.getStringExtra(Intent.EXTRA_TEXT);

        YoutubeUrlConverter urlConverter = new YoutubeUrlConverter();
        String youtube1sUrl = urlConverter.convert(sharedLink);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube1sUrl));
        startActivity(browserIntent);
    }
}
