import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class App {
    private Scanner sc = new Scanner(System.in);

    private int lastId = 0;

    private List<WiseSaying> wiseSayings = new ArrayList<>(); // arraylist로 변경

    // run app 진입점 시작
    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.println("명령) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                actionWrite();
            } else if (cmd.equals("목록")) {  //목록 출력
                actionlist();
            } else if (cmd.startsWith("삭제")) { // 삭제
                actionDelete(cmd);
            } else if (cmd.startsWith("수정")) { // 수정
                actionModify(cmd);
            }
        }

        sc.close(); // 진입점 끝
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
    private void actionDelete(String cmd) {
        String[] cmdBits = cmd.split("=", 2); // 삭제 명령어와 id를 분리

        // id가 입력되지 않은 경우 처리
        if  (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        // id를 정수로 변환
        int id = Integer.parseInt(cmdBits[1]);

        boolean deleted = delete(id);

        // 삭제된 인덱스가 -1이면 해당 id의 명언이 존재하지 않음을 알림
        if (!deleted) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        // 삭제 성공 메시지 출력
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    // 삭제 메서드
    private boolean delete(int id) {
        // 기존 로직보다 성능은 안좋지만 가독성이 좋음
        // 실무에선 이런 선택을 하면 안된다
        // removeIf 보여주기 위해 사용함
        return wiseSayings
                .removeIf(
                      wiseSaying -> wiseSaying.getId() == id
                );
    }

    // 수정 명령어 처리
    private void actionModify(String cmd) {
        String[] cmdBits = cmd.split("=", 2); // 수정 명령어와 id 분리

        // id가 입력되지 않은 경우 처리
        // cmdBits 배열의 길이가 2보다 작거나, 두 번째 요소가 비어있으면 id가 입력되지 않은 것으로 간주
        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]); // id를 정수로 변환

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

    // id에 해당하는 명언의 인덱스를 찾는 메서드
    private int findIndexById(int id) {
        // stream을 사용하여 명언 배열에서 id에 해당하는 명언의 인덱스를 찾음
       return IntStream.range(0, wiseSayings.size())
                .filter(index -> wiseSayings.get(index).getId() == id) // id에 해당하는 명언의 인덱스를 찾음
                .findFirst() // 첫 번째로 찾은 인덱스를 반환
                .orElse(-1); // id에 해당하는 명언이 없는 경우 -1 반환
    }

    // id에 해당하는 명언을 찾는 메서드
    // findIndexById 메서드를 사용하여 id에 해당하는 명언의 인덱스를 찾고, 해당 인덱스의 명언을 반환
    private WiseSaying findById(int id) {
        int index = findIndexById(id);

        if (index == -1) return null;

        return wiseSayings.get(index);
    }

    // 명언 수정 메서드
    // WiseSaying 객체의 content와 author를 수정
    private void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }




}
