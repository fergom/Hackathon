package com.a480.fernando.hackathon.model;

/**
 * Created by Fernando on 07/04/2017.
 */

public class Document {

    private String title;
    private String href;

    public Document() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
