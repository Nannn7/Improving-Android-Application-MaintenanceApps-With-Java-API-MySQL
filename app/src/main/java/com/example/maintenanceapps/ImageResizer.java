package com.example.maintenanceapps;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageResizer {
    private static final String TAG = "IMAGE_RESIZER";

	//For Image Size 640*480, use MAX_SIZE =  307200 as 640*480 307200
    //private static long MAX_SIZE = 360000;
    //private static long THUMB_SIZE = 6553;
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static Bitmap reduceBitmapSize(Bitmap bitmap, int MAX_SIZE) {
        double ratioSquare;
        int bitmapHeight, bitmapWidth;

        bitmapHeight = bitmap.getHeight();
        bitmapWidth = bitmap.getWidth();
        Log.d(TAG, "bitmapHeight: " + bitmapHeight);
        Log.d(TAG, "bitmapWidth: " + bitmapWidth);

        ratioSquare = (bitmapHeight * bitmapWidth) / MAX_SIZE;
        Log.d(TAG, "ratioSquare: " + ratioSquare);

        if (ratioSquare <= 1) return bitmap;

        double ratio = Math.sqrt(ratioSquare);
        Log.d(TAG, "ratio: " + ratio);

        int requiredHeight = (int) Math.round(bitmapHeight / ratio);
        int requiredWidth = (int) Math.round(bitmapWidth / ratio);
        Log.d(TAG, "requiredHeight: " + requiredHeight);
        Log.d(TAG, "requiredWidth: " + requiredWidth);

        return Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
    }

    public static Bitmap generateThumb(Bitmap bitmap, int THUMB_SIZE) {
        double ratioSquare;
        int bitmapHeight, bitmapWidth;
        bitmapHeight = bitmap.getHeight();
        bitmapWidth = bitmap.getWidth();
        ratioSquare = (bitmapHeight * bitmapWidth) / THUMB_SIZE;
        if (ratioSquare <= 1)
            return bitmap;
        double ratio = Math.sqrt(ratioSquare);
        Log.d(TAG, "Ratio: " + ratio);
        int requiredHeight = (int) Math.round(bitmapHeight / ratio);
        int requiredWidth = (int) Math.round(bitmapWidth / ratio);
        return Bitmap.createScaledBitmap(bitmap, requiredWidth, requiredHeight, false);
    }

}
