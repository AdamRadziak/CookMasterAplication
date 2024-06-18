package com.example.cookmasteraplication.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonTools {

    public static String encode2Base64String(String value)  {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeFromBase64String(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    public static Bitmap createImagefromBytes(byte[] b){
        byte[] data = Base64.getEncoder().encode(b);
        return BitmapFactory.decodeByteArray(data, 0, b.length);
    }
}
