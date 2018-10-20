package com.app.service;

import java.io.*;

public class ByteConverter {
    public static byte[] getBytes(File file) throws FileNotFoundException, IOException
    {
        byte[] buffer = new byte[10_000];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        int read;
        while((read = fis.read(buffer)) != -1) bos.write(buffer, 0, read);
        fis.close();
        bos.close();
        return bos.toByteArray();
    }
}
