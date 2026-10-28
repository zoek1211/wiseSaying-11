package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);

    private int lastId = 0; // 마지막으로 등록된 명언 번호

    private final List<WiseSaying> wiseSayings = new ArrayList<>(); // arraylist로 변경

    // run app 진입점 시작
    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.println("명령) ");
            String cmd = sc.nextLine().trim();

            // 입력받은 명령어를 actionName과 파라미터로 파싱
            Rq rq = new Rq(cmd);

            // 모던 스위치문 사용
            switch (rq.getActionName()) {
                case "종료"-> {
                    System.out.println("프로그램이 종료합니다.");
                    return;
                }
                case "목록" -> actionlist();
                case "등록" -> actionWrite();
                case "삭제" -> actionDelete(rq);
                case "수정" -> actionModify(rq);
            }
        }
    }

    // 명언 입력받음
    private void actionWrite() {
        System.out.print("명언: ");
        String content = sc.nextLine().trim();

        System.out.print("작가: ");
        String author = sc.nextLine().trim();

        WiseSaying wiseSaying = write(content, author);

        System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSaying.getId());
    }

    // 명언 등록 메서드
    // WiseSaying 객체를 생성하고, id를 증가시키고, content와 author를 설정한 후, 명언 배열에 추가
    private WiseSaying write(String content, String author) {
        // 생성자 를 사용하여 WiseSaying 객체 생성
        // id를 증가시키고, content와 author를 설정
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);

        // arraylist에 추가
        wiseSayings.add(wiseSaying);

        return wiseSaying;
    }

    // 명언 목록 출력
    private void actionlist() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");

        List<WiseSaying> forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n",
                    wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    // 명언 배열을 역순으로 반환
    private List<WiseSaying> findForList() {
        return wiseSayings.reversed(); // 역순으로 반환
    }

    // 삭제 명령어 처리
    private void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }

        boolean deleted = delete(id);

        // 삭제된 명언이 없으면 해당 id의 명언이 존재하지 않음을 알림
        if (!deleted) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        // 삭제 성공 메시지 출력
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    // 삭제 메서드
    // id에 해당하는 명언을 삭제하고, 삭제되었으면 true 반환
    private boolean delete(int id) {
        // 기존 로직보다 성능은 안좋지만 가독성이 좋음
        // 무조건 전체탐색 진행 느림
        // 실무에선 이런 선택을 하면 안된다
        // removeIf 보여주기 위해 사용함
        return wiseSayings
                .removeIf(
                      wiseSaying -> wiseSaying.getId() == id
                );
    }

    // 수정 명령어 처리
    private void actionModify(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }

        // id에 해당하는 명언을 찾음
        WiseSaying wiseSaying = findById(id);

        // id에 해당하는 명언이 없는 경우
        if (wiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        // 명언 수정
        System.out.printf("명언(기존) :%s\n", wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();

        System.out.printf("작가(기존) :%s\n", wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        modify(wiseSaying, content, author);
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




}
