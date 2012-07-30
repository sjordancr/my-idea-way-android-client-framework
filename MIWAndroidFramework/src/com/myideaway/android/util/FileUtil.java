package com.myideaway.android.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    /**
     * д�ļ�
     *
     * @param fromPath Դ��ַ
     * @param toPath   Ŀ�ĵ�ַ
     * @param bufSize  �������С
     * @return ����״̬
     * @throws java.io.IOException
     */
    public static boolean writeFile(String fromPath, String toPath, int bufSize) throws IOException {
        boolean status = false;
        FileInputStream fis = null;
        FileOutputStream fos = null;

        File parentFile = new File(toPath).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            fis = new FileInputStream(fromPath);
            fos = new FileOutputStream(toPath);

            byte[] buf = new byte[bufSize];
            int bufLength = 0;
            while ((bufLength = fis.read(buf)) != -1) {
                fos.write(buf, 0, bufLength);
                fos.flush();
            }

            status = true;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    /**
     * д�ļ�
     *
     * @param fromStream Դ�ļ�������
     * @param toPath     Ŀ���ļ���ַ
     * @param bufSize    �������С
     * @return ����״̬
     * @throws java.io.IOException
     */
    public static boolean writeFile(InputStream fromStream, String toPath,
                                    int bufSize, boolean closeStream) throws IOException {
        boolean status = false;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        File parentFile = new File(toPath).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        try {
            bis = new BufferedInputStream(fromStream);
            bos = new BufferedOutputStream(new FileOutputStream(toPath));

            byte[] buf = new byte[bufSize];
            int bufLength = 0;
            while ((bufLength = bis.read(buf)) != -1) {
                bos.write(buf, 0, bufLength);
                bos.flush();
            }

            status = true;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }

                if (bis != null && closeStream) {
                    bis.close();
                } else {
                    bis.skip(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    /**
     * �޸��ļ���
     *
     * @param fileName �ļ���
     * @param addition ׷���ַ�
     * @return
     */
    public static String changeFileName(String fileName, String addition) {
        String tempFileName = fileName;

        try {
            String name = tempFileName.substring(0, tempFileName.lastIndexOf("."));
            String postfix = tempFileName.substring(tempFileName.lastIndexOf("."),
                    tempFileName.length());
            tempFileName = name + "_" + addition + postfix;
        } catch (StringIndexOutOfBoundsException ex) {
            tempFileName = fileName + "_" + addition;
        }


        return tempFileName;
    }

    public static boolean moveFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);

        if (!fromFile.exists()) {
            return false;
        }

        toFile.getParentFile().mkdirs();

        try {
            FileInputStream inStream = new FileInputStream(fromFile);
            FileOutputStream outStream = new FileOutputStream(toFile);

            byte[] buf = new byte[8192];
            int len = 0;
            while ((len = inStream.read(buf)) != -1) {
                outStream.write(buf, 0, len);
            }

            outStream.close();
            inStream.close();

            fromFile.deleteOnExit();

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static String getFileNameFromPath(String path) {
        String name = path.substring(path.lastIndexOf("/") + 1);
        return name;
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        boolean result = file.exists();
        return result;
    }

    /**
     * 删除文件夹
     *
     * @param path 文件夹路径
     */
    public static void deleteDirectory(String path) {
        deleteAllFile(path); //删除完里面所有内容
        String filePath = path;
        File myFilePath = new File(filePath);
        myFilePath.delete(); //删除空文件夹
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径
     */
    public static void deleteAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                deleteAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                deleteDirectory(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }

}
