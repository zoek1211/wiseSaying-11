package com.back.domain.wiseSaying.service;

import com.back.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingRepository();
    }

    // 명언 배열 역순으로 변환
    public List<WiseSaying> findForList() {
        return wiseSayingRepository.findForList();
    }

    // 명언 등록 메서드
    // WiseSaying 객체를 생성하고, id를 증가시키고, content와 author를 설정한 후, 명언 배열에 추가
    public WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(content, author);

        wiseSayingRepository.save(wiseSaying);

        return wiseSaying;
    }

    // id에 해당하는 명언을 찾는 메서드
    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    // 명언 수정 메서드
    // WiseSaying 객체의 content와 author를 수정
    public void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
        wiseSayingRepository.save(wiseSaying);
    }

    public boolean delete(int id) {
        return wiseSayingRepository.deleteById(id);
    }
}