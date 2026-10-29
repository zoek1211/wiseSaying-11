package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);

    // run app 진입점 시작
    public void run() {
        System.out.println("== 명언 앱 ==");

        // systemcontroller 도입
        SystemController systemController = new SystemController();

        // wisesaying controller 도입
        WiseSayingController wiseSayingController = new WiseSayingController(sc);

        while (true) {
            System.out.println("명령) ");
            String cmd = sc.nextLine().trim();

            // 입력받은 명령어를 actionName과 파라미터로 파싱
            Rq rq = new Rq(cmd);

            // 모던 스위치문 사용
            switch (rq.getActionName()) {
                case "종료"-> {
                    systemController.actionExit();
                    return;
                }
                case "목록" -> wiseSayingController.actionList();
                case "등록" -> wiseSayingController.actionWrite();
                case "삭제" -> wiseSayingController.actionDelete(rq);
                case "수정" -> wiseSayingController.actionModify(rq);
            }
        }
    }
}
