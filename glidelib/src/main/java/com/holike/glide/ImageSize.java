package com.holike.glide;

import androidx.annotation.NonNull;

public class ImageSize {
    private static final String SEPARATOR = "x";
    private int width;
    private int height;

    public ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @NonNull
    @Override
    public String toString() {
        return width + SEPARATOR + height;
    }
}
