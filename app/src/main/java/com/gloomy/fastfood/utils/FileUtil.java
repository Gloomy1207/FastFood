package com.gloomy.fastfood.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by HungTQB on 1/21/16.
 */
public final class FileUtil {
    private static final String APP_NAME = "Weawow";
    private static final String TAG = FileUtil.class.getSimpleName();

    private FileUtil() {
        // No instance
    }

    /**
     * @return root directory of photo
     */
    public static File createPhotoFile() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_NAME);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new RuntimeException("Can't create folder " + dir.toString());
            }
        }
        return new File(dir, String.format(Locale.getDefault(), "Image%d.jpg", new Date().getTime()));
    }

    /**
     * Get path from URI get from gallery and from folder which create by app
     * http://stackoverflow.com/questions/3401579/get-filename-and-path-from-uri-from-mediastore
     */
    public static String getRealPathFromURI(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // MediaStore (and general)
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                // File
                return uri.getPath();
            }
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * This method is used to save a bitmap to file.
     *
     * @param bitmap to save.
     * @return return file path.
     */
    public static String writeBitmapToFile(@NonNull Context context, @NonNull Bitmap bitmap) {
        File file = createPhotoFile();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        try {
            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "Save bitmap to file error: " + e);
            return null;
        }
        // Mounted this photo to media
        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, null);
        return file.getAbsolutePath();
    }

    /**
     * This method is used to delete a file.
     */
    public static void deleteFile(Context context, String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.delete()) {
                Log.d(TAG, "deleteFile - success");
            } else {
                Log.d(TAG, "deleteFile - failure");
            }
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        }
    }
}
