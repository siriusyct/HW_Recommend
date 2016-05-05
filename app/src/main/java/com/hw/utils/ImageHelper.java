/*******************************************************************************
 * Copyright 2012 momock.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.hw.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

public class ImageHelper {

	public static boolean toFile(Bitmap bitmap, File file) {
		if (file == null) return false;
		try {
			FileOutputStream out = new FileOutputStream(file);
			Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
			String fn = file.getName().toLowerCase();
			if (fn.endsWith(".jpg") || fn.endsWith(".jpeg"))
				format = Bitmap.CompressFormat.JPEG;
			bitmap.compress(format, 85, out);
			out.close();
		} catch (Exception e) {
			//Logger.error(e);
			return false;
		}
		return true;
	}

	public static boolean toFile(Bitmap bitmap, String filename) {
		if (filename == null) return false;		
		return toFile(bitmap, new File(filename));
	}
	
	public static boolean toFile(Bitmap bitmap, File file, int compress) {
		if (file == null) return false;
		try {
			FileOutputStream out = new FileOutputStream(file);
			Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
			String fn = file.getName().toLowerCase();
			if (fn.endsWith(".jpg") || fn.endsWith(".jpeg"))
				format = Bitmap.CompressFormat.JPEG;
			bitmap.compress(format, compress, out);
			out.close();
		} catch (Exception e) {
			//Logger.error(e);
			return false;
		}
		return true;
	}
	
	public static boolean toFile(Bitmap bitmap, String filename, int compress) {
		if (filename == null) return false;		
		return toFile(bitmap, new File(filename), compress);
	}

	public static Bitmap fromFile(final String fn) {
		return fromFile(fn, -1, -1);
	}

	public static Bitmap fromFile(final String fn, final int expectedWidth,
			final int expectedHeight) {
		if (fn == null) return null;
		File f = new File(fn);
		return fromFile(f, expectedWidth, expectedHeight);
	}

	public static Bitmap fromFile(final File f) {
		return fromFile(f, -1, -1);
	}

	public static Bitmap fromFile(final File f, final int expectedWidth,
			final int expectedHeight) {
		if (f == null)
			return null;
		try {
			if (expectedWidth > 0 && expectedHeight > 0) {
				int inWidth;
				int inHeight;
				InputStream in = new FileInputStream(f);
				BitmapFactory.Options options = new BitmapFactory.Options();
				try {
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeStream(in, null, options);
				} finally {
					in.close();
				}
				inWidth = options.outWidth;
				inHeight = options.outHeight;
				final Bitmap roughBitmap;
				in = new FileInputStream(f);
				try {
					options = new BitmapFactory.Options();
					options.inSampleSize = Math.max(inWidth / expectedWidth,
							inHeight / expectedHeight);
					options.inPreferredConfig = Bitmap.Config.RGB_565;
					roughBitmap = BitmapFactory.decodeStream(in, null, options);
				} finally {
					in.close();
				}
				if (roughBitmap == null) return null;
				float[] values = new float[9];
				Matrix m = new Matrix();
				RectF inRect = new RectF(0, 0, roughBitmap.getWidth(),
						roughBitmap.getHeight());
				RectF outRect = new RectF(0, 0, expectedWidth, expectedHeight);
				m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
				m.getValues(values);
				final Bitmap resizedBitmap = Bitmap.createScaledBitmap(
						roughBitmap,
						(int) (roughBitmap.getWidth() * values[0]),
						(int) (roughBitmap.getHeight() * values[4]), true);
				return resizedBitmap;
			} else {
				InputStream in = new FileInputStream(f);
				try {
					return BitmapFactory.decodeStream(in);
				} finally {
					in.close();
				}
			}
		} catch (IOException e) {
			//Logger.error(e);
		}
		return null;
	}

	public static Bitmap fromStream(final InputStream in) {
		return fromStream(in, -1, -1);
	}

	public static Bitmap fromStream(final InputStream in,
			final int expectedWidth, final int expectedHeight) {
		if (in == null)
			return null;
		if (expectedWidth > 0 && expectedHeight > 0) {
			File tempFile;
			try {
				tempFile = File.createTempFile("image", ".tmp");
				final FileOutputStream tempOut = new FileOutputStream(tempFile);
				int len;
				byte[] bs = new byte[1024 * 10];
				while ((len = in.read(bs)) > 0) {
					tempOut.write(bs, 0, len);
				}
				tempOut.close();
				Bitmap bitmap = fromFile(tempFile, expectedWidth,
						expectedHeight);
				tempFile.delete();
				return bitmap;
			} catch (IOException e) {
				//Logger.error(e);
			}
		} else {
			return BitmapFactory.decodeStream(in);
		}
		return null;
	}
	
	public static Bitmap compressImage(Bitmap image, int expectedSize) {
		try {
			int lastSize = 300; // 300kb
			if (expectedSize <= 0)
				lastSize = expectedSize;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			int options = 100;
			while (baos.toByteArray().length / 1024 > lastSize) {		
				baos.reset();
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);
				options -= 10;
				if (options == 0)
					break;
			}
			ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
			Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
			return bitmap;
		} catch (Exception e) {
			//Logger.error(e);
		}

		return null;
	}
	
	public static Bitmap resizeImage(String srcPath, float expectedW, float expectedH, int expectedSize) {
		try {
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);
			
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			
			float hh = expectedH;
			float ww = expectedW;
			if (expectedW == 0)
				ww = 480f;
			if (expectedH == 0)
				hh = 800f;
			
			int be = 1;
			if (w > h && w > ww) {
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			return compressImage(bitmap, expectedSize);
		} catch (Exception e) {
			//Logger.error(e);
		}
		
		return null;
	}
}
