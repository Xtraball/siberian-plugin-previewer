/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.previewer.previewer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.appsmobilecompany.base.R;

import org.apache.cordova.*;

import java.net.MalformedURLException;
import java.net.URL;


public class ApplicationActivity extends CordovaActivity
{
    public String host;
    public ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        host = launchUrl = (String) getIntent().getSerializableExtra("url");

        /** @TODO fin a ay to hide WHEN all the loading is done .... */
        dialog = ProgressDialog.show(ApplicationActivity.this, "", this.getApplicationContext().getString(R.string.load_message_app), true);

        loadUrl(launchUrl);
        appView.clearCache();
    }

    public void confirm() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE) {
                    finish();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(appView.getContext());
        builder.setMessage(R.string.close_message).setPositiveButton(R.string.yes, dialogClickListener)
                .setNegativeButton(R.string.no, dialogClickListener).show();
        return;
    }

    @Override
    public void onBackPressed() {
        String url = (String) appView.getUrl();

        if(url.matches(".*index\\.html/#\\w+$") || !appView.canGoBack()) {
            confirm();
        } else {
            super.onBackPressed();
        }
    }

    public String getHost() {
        URL home;
        try {
            home = new URL(host);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + host);
        }

        return home.getHost();
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

}
