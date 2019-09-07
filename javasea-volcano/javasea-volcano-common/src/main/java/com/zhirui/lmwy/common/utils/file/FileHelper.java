package com.zhirui.lmwy.common.utils.file;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by 包维君 on 2018/1/15.
 */

public final class FileHelper
{
    private static final Log log = LogFactory.getLog(FileHelper.class);
    private static String dbUri = "";

    private static final String[] FILEUNITS = new String[]{
            "B", "KB", "MB", "GB", "TB", "PB", "**"
    };

    /**
     * 格式化显示文件大小
     *
     * @param size
     * @return
     */
    public static String getFileSize(float size) {
        int i = 0;
        while (size > 1024) {
            i++;
            size /= 1024;
        }
        int sizeInt = (int) size;
        boolean isFloat = size - sizeInt > 0.0f;
        //防止数字过大导致数组下标溢出
        int lastIndex = FILEUNITS.length - 1;
        int index = i > lastIndex ? lastIndex : i;
        if (isFloat) {
            return String.format("%1$-1.2f%2$s", size, FILEUNITS[index]);
        }
        return String.format("%1$-1d%2$s", sizeInt, FILEUNITS[index]);
    }

    public static String getFileSuffix(String fileName){
        return  fileName.substring(fileName.lastIndexOf(".")+1);
    }
    public static String getFileContent(String fileName, String defaultEncoding, boolean autoEncoding)
    {
        String content = "";
        File file = new File(fileName);
        if (file.exists()) {
            InputStream inputStream = null;
            try {
                if (autoEncoding) {
                    Charset charset = CharsetToolkit.guessEncoding(file, 1024, Charset.forName(defaultEncoding));
                    defaultEncoding = charset.name();
                }
                inputStream = new FileInputStream(file);
                InputStreamReader reader = null;
                try {
                    reader = new InputStreamReader(inputStream, defaultEncoding);
                    StringBuffer sb = new StringBuffer();
                    int b;
                    while ((b = reader.read()) != -1)
                    {
                        sb.append((char)b);
                    }
                    content = sb.toString();
                } finally {
                    try {
                        reader.close();
                    } catch (Exception e) {
                    }
                }
            }
            catch (IOException e) {
                log.error(e);
            } finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                }
            }
        }
        return content;
    }


    public static String getFileExtName(String fileName)
    {
        int extIndex = fileName.lastIndexOf(".");
        String fileExt = (extIndex > 0) ? fileName.substring(extIndex + 1) : "";
        return fileExt;
    }

    public static String getFileNameElimExt(String filePath)
    {
        String fileName = getFileName(filePath).trim();
        if (fileName.length() > 0) {
            int extLength = getFileExtName(fileName).length();
            fileName = (extLength > 0) ? fileName.substring(0, fileName.length() - extLength - 1) : fileName;
        }
        return fileName;
    }

    public static String getFileName(String filePath)
    {
        filePath = StringUtils.replace(filePath, "\\", "/");
        int lastFolderIndex = filePath.lastIndexOf("/");
        String fileName = (lastFolderIndex > 0) ? filePath.substring(lastFolderIndex + 1) : filePath;
        return fileName;
    }

    public static String getFileFolder(String file)
    {
        file = StringUtils.replace(file, "\\", "/");

        return file.substring(0, file.lastIndexOf("/"));
    }

    public static void createFile(String file, InputStream in)
            throws IOException
    {
        createFolder(getFileFolder(file));

        FileOutputStream out = new FileOutputStream(file);
        try {
            byte[] bytes = new byte[512];
            int n = -1;
            while ((n = in.read(bytes)) != -1) {
                out.write(bytes, 0, n);
            }
            return;
        } finally {
            try {
                in.close();
                out.flush();
                out.close();
            }
            catch (Throwable e)
            {
            }
        }
    }

    public static void createFile(String file, String content, String encoding)  throws IOException
    {
        createFolder(getFileFolder(file));
        FileOutputStream out = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(out, encoding);
        try {
            writer.write(content);
            return;
        } finally {
            try {
                writer.flush();
                writer.close();
                out.flush();
                out.close();
            } catch (Throwable e) {
            }
        }
    }

    public static void createFolder(String folder)
    {
        File file = new File(folder);
        if (!(file.exists()))
            file.mkdirs();
    }
    public static boolean isTxtFile(String fileUri)
    {
        File file = new File(fileUri);
        if(file.exists() && "txt".equalsIgnoreCase(FileHelper.getFileExtName(fileUri))){
            return true;
        }else{
            return false;
        }
    }
    public static void deleteFile(String fileUri)
    {
        File file = new File(fileUri);
        if(file.exists()){
            file.delete();
        }
    }
    public static String getRealUri(String dataFile,String fileName){
        String folder = FileHelper.getFileFolder(dataFile);
        return folder + File.separator + fileName;
    }

    public static String getSameSplitUri(String dataFile){
        if(dataFile==null){
            dataFile = dbUri;
        }
        if(dataFile == null) return  File.separator;
        if(dataFile.indexOf("/") != -1) return "/";
        else if(dataFile.indexOf("\\") != -1) return "\\";
        else return File.separator;
    }

    public static int readyFile(String path)throws IOException {
        int counts = 0;
        File file=new File(path);
        long fileLength = file.length();
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(path));
            if (reader != null) {
                reader.skip(fileLength);
                counts = reader.getLineNumber();
                reader.close();
            }
        } catch (Exception ex) {
            counts = -1;
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return counts;
    }

}
