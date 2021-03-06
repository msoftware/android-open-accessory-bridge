/*
 * Copyright 2015 Christopher Blay <chris.b.blay@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.covertbagel.androidopenaccessorybridge.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.covertbagel.androidopenaccessorybridge.AndroidOpenAccessoryBridge;
import com.covertbagel.androidopenaccessorybridge.BufferHolder;

public class MainActivity extends Activity implements AndroidOpenAccessoryBridge.Listener {

    private AndroidOpenAccessoryBridge mAoab;
    private TextView mTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAoab = new AndroidOpenAccessoryBridge(this, this);
        mTextView = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAoab = null;
    }

    // AndroidOpenAccessoryBridge.Listener

    @Override
    public void onAoabRead(final BufferHolder bufferHolder) {
        try {
            final int value = Integer.parseInt(bufferHolder.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(String.valueOf(value));
                }
            });
        } catch (NumberFormatException exception) {
            return;
        }
        if (mAoab != null) {
            mAoab.write(bufferHolder);
        }
    }

    @Override
    public void onAoabShutdown() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

}
