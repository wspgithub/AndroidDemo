package com.example.administrator.myapplication;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class StoryGetTest {

    public static void main(String[] args) throws IOException {

        /* 读入TXT文件 */
        String pathname = "E:\\ASProject\\AndroidDemo\\story.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"gbk"));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            stringBuffer.append(inputLine);
        }
      //  Log.e("okhttp","成功"+stringBuffer.toString());
       // readTitle(stringBuffer.toString());
       // readContent(stringBuffer.toString());
        readLast(stringBuffer.toString());
        readNext(stringBuffer.toString());


    }

    private static String readTitle(String text){
        String title="";
        int start = text.indexOf("<h1>");
        int end = text.indexOf("</h1>");
        title=text.substring(start+4,end);
        System.out.print(title);
        return title;
    }

    private static String readContent(String text){
        String title="";
        String content = "";
        int start =  0;
        int end = 0;

        start = text.indexOf("<div id=\"content\">");
        content = text.substring(start+18,text.length());
        end = content.indexOf("</div>");
        content = content.substring(0,end);

        content=content.replace("// 免费电子书下载//","").replace("<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;","\n").replace("&nbsp;&nbsp;&nbsp;&nbsp;","\n");
        System.out.print(content);
        return title;
    }

    //<a href="      ">上一章</a>
    private static String readLast(String text){
        String lastUrl="";
        int start =  0;
        int end = 0;
        end = text.indexOf("\">上一章</a>");
        String content = "";
        for (int i = 0; i < 10; i++) {
            content = text.substring(end-10*i,end);
            if(content.contains("<a href="))
               break;
        }

        start = content.indexOf("href=\"");
        content = content.substring(start+6,content.length());
        return content;
    }

    private static String readNext(String text){
        String lastUrl="";
        int start =  0;
        int end = 0;
        end = text.indexOf("\">下一章</a>");
        String content = "";
        for (int i = 0; i < 10; i++) {
            content = text.substring(end-10*i,end);
            if(content.contains("<a href="))
                break;
        }

        start = content.indexOf("href=\"");
        content = content.substring(start+6,content.length());
        return content;
    }

}
