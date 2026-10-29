package com.back.domain.wiseSaying.controller;

import com.back.Rq;
import com.back.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();
    private final Scanner scanner;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    // 명언 입력받음
    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = write(content, author);

        System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSaying.getId());
    }

    // 삭제 명령어 처리
    public void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }

        boolean deleted = delete(id);

        // 삭제된 명언이 없으면 해당 id의 명언이 존재하지 않음을 알림
        if (!deleted) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
    }

    // 수정 명령어 처리
    public void actionModify(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }

        // id 에 해당하는 명언 찾음
        WiseSaying wiseSaying = findById(id);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        // 명언 수정
        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        modify(wiseSaying, content, author);
    }

    // 내부로직 시작
    // 명언 배열 역순으로 변환
    private List<WiseSaying> findForList() {
        return wiseSayings.reversed();
    }

    // 명언 등록 메서드
    // WiseSaying 객체를 생성하고, id를 증가시키고, content와 author를 설정한 후, 명언 배열에 추가
    private WiseSaying write(String content, String author) {
        // 생성자 를 사용하여 WiseSaying 객체 생성
        // id를 증가시키고, content와 author를 설정
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);

        wiseSayings.add(wiseSaying); // arraylist 에 추가

        return wiseSaying;
    }

    // id에 해당하는 명언을 찾는 메서드
    // WiseSaying 객체를 반환, 없으면 null 반환
    private WiseSaying findById(int id) {
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // 명언 수정 메서드
    // WiseSaying 객체의 content와 author를 수정
    private void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }

    private boolean delete(int id) {
        // 기존 로직보다 성능은 안 좋지만, 가독성이 좋음
        // 실무에서는 이런 선택을 하면 안된다.
        // 하지만 removeIf를 보여주기 위해서 사용함
        return wiseSayings
                .removeIf(
                        wiseSaying -> wiseSaying.getId() == id
                );
    }
    // 내부로직 끝
}