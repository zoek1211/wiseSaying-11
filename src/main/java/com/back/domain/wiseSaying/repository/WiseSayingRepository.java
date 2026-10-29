package com.back.domain.wiseSaying.repository;

import com.back.WiseSaying;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WiseSayingRepository {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    public List<WiseSaying> findForList() {
        return wiseSayings.reversed();
    }

    public void save(WiseSaying wiseSaying) {
        if (wiseSaying.isNew()) { // 최초 저장시 Id 0으로 셋
            wiseSaying.setId(++lastId);
            // 최초 게시물 작성날짜, 수정날짜
            LocalDateTime now = LocalDateTime.now();
            wiseSaying.setCreateDate(now);
            wiseSaying.setModifyDate(now);
            wiseSayings.add(wiseSaying);
        } else {
            // 수정
            wiseSaying.setModifyDate(LocalDateTime.now());
        }
    }

    public WiseSaying findById(int id) {
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteById(int id) {
        int index = findIndexById(id);

        if (index == -1) return false;

        wiseSayings.remove(index);

        return true;
    }

    private int findIndexById(int id) {
        return IntStream.range(0, wiseSayings.size())
                .filter(i -> wiseSayings.get(i).getId() == id)
                .findFirst()
                .orElse(-1);
    }
}