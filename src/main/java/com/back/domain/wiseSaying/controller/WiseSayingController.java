package com.back.domain.wiseSaying.controller;

import com.back.Rq;
import com.back.WiseSaying;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
        this.wiseSayingService = new WiseSayingService();
    }

    public void actionList() {
        System.out.println("번호 / 작성날짜 / 수정날짜 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> forListWiseSayings = wiseSayingService.findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s / %s / %s\n",
                    wiseSaying.getId(), wiseSaying.getForPrintCreateDate(),
                    wiseSaying.getForPrintModifyDate(),
                    wiseSaying.getAuthor(), wiseSaying.getContent()
            );
        }
    }

    // 명언 입력받음
    public void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = wiseSayingService.write(content, author);

        System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSaying.getId());
    }

    // 삭제 명령어 처리
    public void actionDelete(Rq rq) {
        int id = rq.getParamAsInt("id", -1);

        if (id == -1) {
            System.out.println("id를 숫자로 입력해주세요.");
            return;
        }

        boolean deleted = wiseSayingService.delete(id);

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
        WiseSaying wiseSaying = wiseSayingService.findById(id);

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

        wiseSayingService.modify(wiseSaying, content, author);
    }
}