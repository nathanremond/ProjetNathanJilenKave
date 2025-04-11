package com.example.projetnathanjilenkave;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HelperJson {

    public static String loadJSONFromRaw(Context context, int ressourceId){
        String json = null;

        try{
            InputStream is = context.getResources().openRawResource(ressourceId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return json;
    }
}