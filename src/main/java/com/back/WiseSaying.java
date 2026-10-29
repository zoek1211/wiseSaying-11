package com.back;

public class WiseSaying {
    // encapsulation
    // always use private for fields to protect the data
    // and provide public getter and setter methods to access and modify the data
    private int id;
    private String content;
    private String author;

    // 생성자
    // WiseSaying 객체를 생성할 때 id, content, author를 설정
    // id는 final이라 생성 후 변경 불가
    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }
}
