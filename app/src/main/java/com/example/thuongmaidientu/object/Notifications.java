package com.example.thuongmaidientu.object;

import java.io.Serializable;

public class Notifications implements Serializable {
    String name, content;

    public Notifications(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Notifications ()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
