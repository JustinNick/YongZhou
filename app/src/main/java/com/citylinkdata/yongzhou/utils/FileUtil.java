package com.citylinkdata.yongzhou.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import android.os.Environment;

/**
 * Created by KEEPTONG on 2017/7/10.
 */

public class FileUtil {

    public static String FILE_DIR_NAME = "yongzhou";



    public static File getApkFile(String apkName){

       File tmpFile = new File(Environment.getExternalStorageDirectory(),FILE_DIR_NAME);

       if (!tmpFile.exists()) {
           tmpFile.mkdir();
       }

       File file = new File(tmpFile,apkName);
       return file;
   }


    public static String FormetFileSizeToMb(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        fileSizeString = df.format((double) fileS / 1048576);
        return fileSizeString;
    }

}
