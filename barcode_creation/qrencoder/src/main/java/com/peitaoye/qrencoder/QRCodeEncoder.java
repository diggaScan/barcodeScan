/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peitaoye.qrencoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import com.peitaoye.qrencoder.Exception.WriterException;
import com.peitaoye.qrencoder.common.BitMatrix;
import com.peitaoye.qrencoder.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * This class does the work of decoding the user's request and extracting all the data
 * to be encoded in a barcode.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class QRCodeEncoder {

    private static final String TAG = QRCodeEncoder.class.getSimpleName();

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private final Context activity;
    private String contents_data;
    private String displayContents;

    private BarcodeFormat format;
    private String content_type;
    private final int dimension;


    public QRCodeEncoder(Context activity, String content, int dimension, String type) throws WriterException {
        this.activity = activity;
        this.dimension = dimension;
        content_type = type;
        encodeQRCodeContents(content, type);
    }

    String getDisplayContents() {
        return displayContents;
    }


    private void encodeQRCodeContents(String content, String type) {
        switch (type) {
            case Contents.Type.TEXT:
                if (content != null && !content.isEmpty()) {
                    contents_data = content;
                    displayContents = content;
                }
                break;

            case Contents.Type.EMAIL:
                if (content != null) {
                    contents_data = "mailto:" + content;
                    displayContents = content;
                }
                break;

            case Contents.Type.PHONE:

                if (content != null) {
                    contents_data = "tel:" + content;
                    displayContents = PhoneNumberUtils.formatNumber(content);
                }
                break;

            case Contents.Type.SMS:
                if (content != null) {
                    contents_data = "sms:" + content;
                    displayContents = PhoneNumberUtils.formatNumber(content);
                }
                break;

//      case Contents.Type.CONTACT:
//        Bundle contactBundle = intent.getBundleExtra(Intents.Encode.DATA);
//        if (contactBundle != null) {
//
//          String name = contactBundle.getString(ContactsContract.Intents.Insert.NAME);
//          String organization = contactBundle.getString(ContactsContract.Intents.Insert.COMPANY);
//          String address = contactBundle.getString(ContactsContract.Intents.Insert.POSTAL);
//          List<String> phones = getAllBundleValues(contactBundle, Contents.PHONE_KEYS);
//          List<String> phoneTypes = getAllBundleValues(contactBundle, Contents.PHONE_TYPE_KEYS);
//          List<String> emails = getAllBundleValues(contactBundle, Contents.EMAIL_KEYS);
//          String url = contactBundle.getString(Contents.URL_KEY);
//          List<String> urls = url == null ? null : Collections.singletonList(url);
//          String note = contactBundle.getString(Contents.NOTE_KEY);
//
//          ContactEncoder encoder = useVCard ? new VCardContactEncoder() : new MECARDContactEncoder();
//          String[] encoded = encoder.encode(Collections.singletonList(name),
//                                            organization,
//                                            Collections.singletonList(address),
//                                            phones,
//                                            phoneTypes,
//                                            emails,
//                                            urls,
//                                            note);
//          // Make sure we've encoded at least one field.
//          if (!encoded[1].isEmpty()) {
//            contents = encoded[0];
//            displayContents = encoded[1];
//            title = activity.getString(R.string.contents_contact);
//          }
//
//        }
//        break;
//
//      case Contents.Type.LOCATION:
//        Bundle locationBundle = intent.getBundleExtra(Intents.Encode.DATA);
//        if (locationBundle != null) {
//          // These must use Bundle.getFloat(), not getDouble(), it's part of the API.
//          float latitude = locationBundle.getFloat("LAT", Float.MAX_VALUE);
//          float longitude = locationBundle.getFloat("LONG", Float.MAX_VALUE);
//          if (latitude != Float.MAX_VALUE && longitude != Float.MAX_VALUE) {
//            contents = "geo:" + latitude + ',' + longitude;
//            displayContents = latitude + "," + longitude;
//            title = activity.getString(R.string.contents_location);
//          }
//        }
//        break;
        }
    }


    private static List<String> toList(String[] values) {
        return values == null ? null : Arrays.asList(values);
    }

    public Bitmap encodeAsBitmap(@Nullable String file) throws WriterException {
        String contentsToEncode = contents_data;
        if (contentsToEncode == null) {
            return null;
        }
        BitMatrix result;
        try {
            result = new QRCodeWriter().encode(contentsToEncode, dimension, dimension);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        if (bitmap != null && file != null) {
            Log.d("info","bitmap != null && file != null");
            storeBitmap(bitmap, file);
        }
        return bitmap;
    }

    private void storeBitmap(Bitmap bitmap, String file) {
        try {
            File file_stored = new File(file);
            if (file_stored.exists()) {
                file_stored.delete();
            }
            file_stored.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Log.d("info","fail");
            e.printStackTrace();
            e.getCause();
        }
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}
