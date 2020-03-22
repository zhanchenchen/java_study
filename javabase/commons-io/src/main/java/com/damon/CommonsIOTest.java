package com.damon;


import org.apache.commons.io.*;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Hello world!
 */
public class CommonsIOTest {
    public static void main(String[] args) throws IOException {
        exampleByFileNameUtils();
    }

    public static void typically() throws IOException {
        /*As an example, consider the task of reading bytes from a URL, and printing them.
        This would typically done like this:*/
        InputStream inputStream = new URL("http://commons.apache.org").openStream();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.err.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
        }
    }

    //使用IOUtils工具读取流
    public static void exampleByIOUtils() throws IOException {
        //try-with-resource，流移入括号内，并且为常量，不能修改
        try (InputStream inputStream = new URL("http://commons.apache.org").openStream();) {
            System.err.println(IOUtils.toString(inputStream, Charset.defaultCharset()));
        } finally {
            //finally中不再手动关闭，会自动处理
        }
    }

    //使用FileUtils读取文件内容
    public static void exampleByFileUtils() throws IOException {
        File file = new File("e:"+File.separator+"java-test.txt");
        List<String> strings = FileUtils.readLines(file, "GBK");
        System.err.println(strings);
    }

    /*For example to normalize a filename removing double dot segments:*/
    public static void exampleByFileNameUtils() throws IOException {
        String fileName="e:/io/../java-test.txt";
        String normalize = FilenameUtils.normalize(fileName);
        System.err.println(normalize);  //e:\java-test.txt
    }
}
