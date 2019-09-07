package com.iee.file.archive.simple;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
 
public class ObjectFileOperator {
    /**
     * 保存对象
     * 
     * @param ser
     * @param file
     * @throws IOException
     */
    public static void saveObject(final Serializable ser, final String fileDir,
            final String file) {
        new Thread(new Runnable() {
 
            public void run() {
                FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                File filePath = null;
                try {
                    filePath = new File(fileDir);
                    if (!filePath.exists()) {
                        filePath.mkdirs();
                    }
                    filePath = null;
                    filePath = new File(fileDir, file);
                    fos = new FileOutputStream(filePath, false);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(ser);
                    oos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        oos.close();
                    } catch (Exception e) {
                    }
                    try {
                        fos.close();
                    } catch (Exception e) {
                    }
                }
 
            }
        }).start();
 
    }
 
    /**
     * 读取对象
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static Serializable readObject(String file) {
        File temp = new File(file);
        if (!temp.exists())
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(temp);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除文件
            if (e instanceof InvalidClassException) {
                if (null != temp) {
                    temp.delete();
                }
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
 
}
