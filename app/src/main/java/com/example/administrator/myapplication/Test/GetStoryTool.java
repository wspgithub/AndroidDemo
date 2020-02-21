package com.example.administrator.myapplication.Test;

import org.jsoup.nodes.Document;

abstract class GetStoryTool {
    public abstract String getTitle(Document document);
    public abstract String getContent(Document document);
    public abstract String getLastUrl(Document document);
    public abstract String getNextUrl(Document document);
}
