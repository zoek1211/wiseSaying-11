package com.back.domain.wiseSaying.service;

import com.back.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingService {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    // 명언 배열 역순으로 변환
    public List<WiseSaying> findForList() {
        return wiseSayings.reversed();
    }

    // 명언 등록 메서드
    // WiseSaying 객체를 생성하고, id를 증가시키고, content와 author를 설정한 후, 명언 배열에 추가
    public WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);

        wiseSayings.add(wiseSaying);

        return wiseSaying;
    }

    // id에 해당하는 명언을 찾는 메서드
    // WiseSaying 객체를 반환, 없으면 null 반환
    public WiseSaying findById(int id) {
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 명언 수정 메서드
    // WiseSaying 객체의 content와 author를 수정
    public void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }

    // 기존 로직보다 성능은 안 좋지만, 가독성이 좋음
    // 실무에서는 이런 선택을 하면 안된다.
    // 하지만 removeIf를 보여주기 위해서 사용함
    public boolean delete(int id) {
        return wiseSayings
                .removeIf(
                        wiseSaying -> wiseSaying.getId() == id
                );
    }
}