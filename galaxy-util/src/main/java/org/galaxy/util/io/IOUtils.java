package org.galaxy.util.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    private static final int BUFFER_SIZE = 4096;

    private IOUtils(){}

    public static long write(InputStream is, OutputStream os) throws IOException {
        return write(is, os, BUFFER_SIZE);
    }

    public static long write(InputStream is, OutputStream os, int bufferSize) throws IOException {
        long total = 0L;
        byte[] buff = new byte[bufferSize];

        while(is.available() > 0) {
            int read = is.read(buff, 0, buff.length);
            if (read > 0) {
                os.write(buff, 0, read);
                total += read;
            }
        }

        return total;
    }

    public static String read(Reader reader) throws IOException {
        StringWriter writer = new StringWriter();

        String var3;
        try {
            write(reader, writer);
            var3 = writer.getBuffer().toString();
        } finally {
            writer.close();
        }

        return var3;
    }

    public static long write(Writer writer, String string) throws IOException {
        Reader reader = new StringReader(string);

        long var4;
        try {
            var4 = write(reader, writer);
        } finally {
            reader.close();
        }

        return var4;
    }

    public static long write(Reader reader, Writer writer) throws IOException {
        return write(reader, writer, BUFFER_SIZE);
    }

    public static long write(Reader reader, Writer writer, int bufferSize) throws IOException {
        long total = 0L;

        int read;
        for(char[] buf = new char[8192]; (read = reader.read(buf)) != -1; total += read) {
            writer.write(buf, 0, read);
        }

        return total;
    }

    public static String[] readLines(File file) throws IOException {
        return file != null && file.exists() && file.canRead() ? readLines((InputStream)(new FileInputStream(file))) : new String[0];
    }

    public static String[] readLines(InputStream is) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String[] var5;
        try {
            String line;
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            var5 = (String[])lines.toArray(new String[0]);
        } finally {
            reader.close();
        }

        return var5;
    }

    public static void writeLines(OutputStream os, String[] lines) throws IOException {

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(os))) {
            String[] var6 = lines;
            int var5 = lines.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                String line = var6[var4];
                writer.println(line);
            }

            writer.flush();
        }
    }

    public static void writeLines(File file, String[] lines) throws IOException {
        if (file == null) {
            throw new IOException("File is null.");
        } else {
            writeLines((OutputStream)(new FileOutputStream(file)), lines);
        }
    }

    public static void appendLines(File file, String[] lines) throws IOException {
        if (file == null) {
            throw new IOException("File is null.");
        } else {
            writeLines((OutputStream)(new FileOutputStream(file, true)), lines);
        }
    }


}
