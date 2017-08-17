package com.bibinet.biunion.project.utils;

        import java.io.File;
        import java.text.DecimalFormat;
        import java.io.FileInputStream;

/**
 * Created by 吴昊 on 2017-5-8.
 */

public class FileUtils {

    public static void clearDir(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                clearDir(f.getPath());
                f.delete();
            }
        }
        file.delete();
    }

    public static long getFileSize(File f) {
        try {
            long size = 0;
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
            return size;
        }catch (Exception e){

        }
        return -1;
    }


    public static String formetFileSize(String path) {// 转换文件大小
        long fileS = getFileSize(new File(path));
        if(fileS <= 0){
            return "0.00M";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

}
