package com.back;

public class Main {
    // 프로그램 진입점
    public static void main(String[] args) {
        App app = new App();
        app.run();

//        testRq1();
    }

    // Rq 클래스의 파라미터 파싱이 잘 되는지 확인하는 테스트 메서드
    private static void testRq1() {
        Rq rq = new Rq("목록?searchKeywordType=content&searchKeyword=자바&page=2");

        String searchKeywordType = rq.getParam("searchKeywordType", "");
        String searchKeyword = rq.getParam("searchKeyword", "");

        String sort = rq.getParam("sort", "idDesc");
        int page = rq.getParamAsInt("page", -1);
        int id = rq.getParamAsInt("id", -1);

        System.out.println("action Name: " + rq.getActionName());
        System.out.println("Param Search Keyword Type: " + searchKeywordType);
        System.out.println("Param Search Keyword: " + searchKeyword);
        System.out.println("Param Sort: " + sort);
        System.out.println("Param Page: " + page);
        System.out.println("Param Id: " + id);
    }
}


