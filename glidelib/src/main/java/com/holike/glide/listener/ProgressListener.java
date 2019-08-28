package com.holike.glide.listener;


public interface ProgressListener {
    void onProgress(String imageUrl, long bytesRead, long totalBytes, int percent, boolean isDone);
}
