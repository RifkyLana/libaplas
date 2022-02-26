package com.libaplas;

import android.content.Context;
import android.widget.Toast;

public class Hallo {
    public static void s(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
    public static void r(){
        System.out.println("ini dari lib GITHUB");
    }
}
