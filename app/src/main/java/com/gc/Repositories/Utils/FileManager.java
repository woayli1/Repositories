package com.gc.Repositories.Utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileManager {

    private static String TAG = "FileManager";

    public File getFilePtah(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        try {
            file = new File(context.getExternalCacheDir().toString());
            if (!isExit(file)) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
                if (!isExit(file)) {
                    Log.d(TAG, "位置:内部存储1");
                    file = new File(Environment.getExternalStorageDirectory().toString());
                }
            }
        } catch (NullPointerException e3) {
            Log.d(TAG, "路径错误:begin:" + e3);
        }
        return file;
    }

    //检查路径是否存在，不存在则创建
    private boolean isExit(File file) {
        boolean isSuccess;
        if (!file.exists()) {
            isSuccess = file.mkdirs();
            Log.d(TAG, "路径:创建文件路径: " + isSuccess);
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }

    //检查外部存储是否可读可写
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    //复制文件 //源路径，目标路径，文件名
    public boolean copyFile(File src, String destPath, String filename) {
        if ((src == null) || (destPath == null)) {
            return false;
        }

        File dest = new File(destPath + "/" + filename);
        if (dest.exists()) {
            dest.delete(); // delete file
        }

        try {
            dest.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;

        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    //获取文件夹下的所有文件名
    public static List<String> getAllFilesName(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e(TAG, "空目录");
            return null;
        }
        List<String> s = new ArrayList<>();

        for (File i : files) {
            if (!i.isDirectory()) {
                s.add(i.toString());
            } else {
                s.add(i.toString());
                s.addAll(getAllFilesName(i.toString()));
            }
        }
        return s;
    }

    //获取文件夹下的所有文件夹名
    public static List<String> getAllFoldersName(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e(TAG, "空目录");
            return null;
        }
        List<String> s = new ArrayList<>();
        for (File i : files) {
            if (i.isDirectory()) {
                s.add(i.toString());
            }
        }
        return s;
    }

    //删除文件，可以是文件或文件夹
    public boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            Log.d(TAG, "删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    //删除某个文件
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e(TAG, "--Method--:Copy_Delete.deleteSingleFile: 删除单个文件成功：" + filePath$Name);
                return true;
            } else {
                Log.e(TAG, "--Method--:Copy_Delete.deleteSingleFile: 删除单个文件失败：" + filePath$Name);
                return false;
            }
        } else {
            Log.e(TAG, "--Method--:Copy_Delete.deleteSingleFile: 删除单个文件失败：" + filePath$Name + "不存在");
            return false;
        }
    }

    //删除目录及目录下的文件
    private boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Log.d(TAG, "删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File i : files) {
            // 删除子文件
            if (i.isFile()) {
                flag = deleteSingleFile(i.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (i.isDirectory()) {
                flag = deleteDirectory(i.getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            Log.d(TAG, "删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.d(TAG, "删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    public static void unZipFolder(String archive, String decompressDir) {
        try {
            BufferedInputStream bi;
            ZipFile zf = new ZipFile(archive);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();
                String entryName = ze2.getName();
                String path = decompressDir + "/" + entryName;
                if (ze2.isDirectory()) {
                    Log.i(TAG, "正在创建解压目录 - " + entryName);
                    File decompressDirFile = new File(path);
                    if (!decompressDirFile.exists()) {
                        decompressDirFile.mkdirs();
                    }
                } else {
                    Log.i(TAG, "正在创建解压文件 - " + entryName);
                    String fileDir = path.substring(0, path.lastIndexOf("/"));
                    File fileDirFile = new File(fileDir);
                    if (!fileDirFile.exists()) {
                        fileDirFile.mkdirs();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(decompressDir + "/" + entryName));
                    bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                    bos.close();
                }
            }
            zf.close();
        } catch (IOException e) {
            Log.i(TAG, "faile to unzip file");
        }
    }

    //MD5return后，第一位是0的话会出现0被抹掉的情况，所以需要判断长度，然后前面加0
    private String leftaddZeroForNum(String str, int strLength) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        StringBuffer sb;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);// 左(前)补0
            // sb.append(str).append("0");//右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public String rightaddZeroForNum(String str, int strLength) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        StringBuffer sb;
        while (strLen < strLength) {
            sb = new StringBuffer();
//            sb.append("0").append(str);// 左(前)补0
            sb.append(str).append("0");//右(后)补0
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    //返回某一文件的MD5值
    public String getFileMD5(File file) {
        if (!file.isFile() || !file.exists()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return leftaddZeroForNum(bigInt.toString(16), 32);
    }
}
