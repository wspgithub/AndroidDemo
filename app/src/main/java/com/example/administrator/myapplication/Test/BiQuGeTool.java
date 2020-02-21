package com.example.administrator.myapplication.Test;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BiQuGeTool extends GetStoryTool {


    @Override
    public String getTitle(Document document) {
        String title="";
        for (Element a : document.getElementsByTag("h1")) {
            if (a.text().contains("第")||a.text().contains("章")) {
                title = a.text();
                break;
            }
        }
        return title;
    }

    @Override
    public String getContent(Document document) {
        String Content="";
        for (Element a : document.getElementsByTag("div")) {
            if (a.id().equals("content")) {
                Content = a.toString().replace("// 免费电子书下载//","")
                .replace("<div id=\"content\">","")
                .replace("</div>","")
                .replace("// 访问下载txt小说//","")
                .replace("<br>","")
                .replace("&nbsp;&nbsp;&nbsp;&nbsp;","\n");
                break;
            }
        }
        return Content;
    }

    @Override
    public String getLastUrl(Document document) {
        String lastUrl="";
        for (Element a : document.getElementsByTag("a")) {
            if ( a.attr("href").contains(".html")&&a.text().contains("上")) {
                lastUrl = a.attr("href");
                break;
            }
        }

        return lastUrl;
    }

    @Override
    public String getNextUrl(Document document) {
        String nextUrl="";
        for (Element a : document.getElementsByTag("a")) {
            if ( a.attr("href").contains(".html")&&a.text().contains("下")) {
                nextUrl = a.attr("href");
                break;
            }
        }

        return nextUrl;
    }
}
