package com.libaplas;

import android.content.Context;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Hallo {
    public static void s(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
    public static void r(){
        System.out.println("ini dari lib GITHUB");
    }
    
    public static void test_submit1() {
      File files = new File("./build/reports/tests/testDebugUnitTest/index.html");
        try {
            String dir2 = files.getCanonicalPath();
            uploads(dir2);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void uploads(String selectedPath) throws Exception {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        String pathToOurFile = selectedPath;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        FileInputStream fileInputStream = new FileInputStream(new File(
                pathToOurFile));
        URL url = new URL("http://192.168.1.7/upload/upload.php");
        connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type",
                "multipart/form-data;boundary=" + boundary);
        outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
        outputStream
                .writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                        + pathToOurFile + "\"" + lineEnd);
        outputStream.writeBytes(lineEnd);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        buffer = new byte[bufferSize];

        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        while (bytesRead > 0) {
            outputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }
        outputStream.writeBytes(lineEnd);
        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        int serverResponseCode = connection.getResponseCode();
        String serverResponseMessage = connection.getResponseMessage();
        fileInputStream.close();
        outputStream.flush();
        outputStream.close();
    }    
}
