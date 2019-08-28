package com.holike.glide.okhttp;


import android.os.Handler;
import android.os.Looper;

import com.holike.glide.GlideLoader;
import com.holike.glide.listener.ProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private BufferedSource bufferedSource;
    private ResponseBody responseBody;
    private String url;
    private ProgressListener listener;

    public ProgressResponseBody(String url, ResponseBody responseBody) {
        this.url = url;
        this.responseBody = responseBody;
        listener = GlideLoader.getInstance().getListener(url);
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private class ProgressSource extends ForwardingSource {

//        long totalBytesRead = 0;
//
//        int currentProgress;
        long totalBytesRead;
        long lastTotalBytesRead;
        ProgressSource(Source source) {
            super(source);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            final long bytesRead = super.read(sink, byteCount);
            totalBytesRead += (bytesRead == -1) ? 0 : bytesRead;
            final long fullLength = responseBody.contentLength();
            final int progress = (int) (100f * totalBytesRead / fullLength);
            if (listener != null && lastTotalBytesRead != totalBytesRead) {
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        lastTotalBytesRead = totalBytesRead;
                        listener.onProgress(url, totalBytesRead, fullLength, progress, totalBytesRead == fullLength);
                    }
                });
            }
            return bytesRead;
        }
    }

}
