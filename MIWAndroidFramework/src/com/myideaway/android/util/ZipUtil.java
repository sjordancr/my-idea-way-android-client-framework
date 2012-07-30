package com.myideaway.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: cdm
 * Date: 12-4-12
 * Time: PM2:29
 */

/**
 * 解压只有一个文件组成的zip到当前目录，并且给解压出的文件重命名
 */
public class ZipUtil {
    public static void unzipSingleFileHereWithFileName(String zipPath, String name) throws IOException {
        File zipFile = new File(zipPath);
        File unzipFile = new File(zipFile.getParent() + "/" + name);
        ZipInputStream zipInStream = null;
        FileOutputStream unzipOutStream = null;
        try {
            zipInStream = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry zipEntry = zipInStream.getNextEntry();
            if (!zipEntry.isDirectory()) {
                unzipOutStream = new FileOutputStream(unzipFile);
                byte[] buf = new byte[4096];
                int len = -1;
                while((len = zipInStream.read(buf)) != -1){
                    unzipOutStream.write(buf, 0, len);
                }
            }
        } finally {
            if(unzipOutStream != null){
                unzipOutStream.close();
            }

            if (zipInStream != null) {
               zipInStream.close();
            }
        }
    }
}
