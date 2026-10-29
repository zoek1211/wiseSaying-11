package com.back;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WiseSaying {
    // encapsulation
    // always use private for fields to protect the data
    // and provide public getter and setter methods to access and modify the data
    private int id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String content;
    private String author;
    private static DateTimeFormatter forPrintDateTimeFormatter =
        DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isNew() {
        return getId() == 0;
    }

    public String getForPrintCreateDate() {
        return createDate.format(forPrintDateTimeFormatter);
    }

    public String getForPrintModifyDate() {
        return modifyDate.format(forPrintDateTimeFormatter);
    }
}
