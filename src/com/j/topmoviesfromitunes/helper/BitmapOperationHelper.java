package com.j.topmoviesfromitunes.helper;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Bitmap Operation Helper class for bitmap operations
 * @author J
 *
 */
public class BitmapOperationHelper {

	//Tag for logging
	private static final String TAG = BitmapOperationHelper.class.getSimpleName();

	/**
	 * Loads and creates a scaled bitmap by path / widhDp / heightDp
	 * @param context The context
	 * @param bitmapFilePath The file path of the bitmap data file
	 * @param widthDp Width of the bitmap in Dp
	 * @param heightDp Height of the bitmap in Dp
	 * @return scaled bitmap
	 * @throws IOException If error occurs during bitmap decoding
	 */
	public static Bitmap loadScaledBitmap(Context context, String bitmapFilePath, int widthDp, int heightDp) throws IOException {
		
		//create movie icon
		Bitmap bitmap;
		//calculate dimensions in px   
		int movieIconWidthDp = ImageUnitHelper.dpToPx(context, widthDp);
		int movieIconHeightDp = ImageUnitHelper.dpToPx(context, heightDp);
		//decode bitmap file
		bitmap=BitmapFactory.decodeStream(context.openFileInput(bitmapFilePath));
		//scale bitmap
		bitmap=Bitmap.createScaledBitmap(bitmap, movieIconWidthDp, movieIconHeightDp, true);
		Log.d(TAG, "Scaled bitmap (" + bitmapFilePath + ", " + widthDp + "dp x" + heightDp + " dp) created");
		//return scaled bitmap
		return bitmap;
	}

}
