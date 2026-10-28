package com.back;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

// 사용자가 입력한 명령어를 파싱하는 클래스
// 예) "삭제?id=1" -> actionName: "삭제", paramsMap: {id=1}
public class Rq {
    private final String actionName; // ? 앞부분 (명령어 이름)
    private final Map<String, String> paramsMap; // ? 뒷부분 (key=value 파라미터들)

    public Rq(String cmd) {

        // cmd를 ? 기준으로 actionName과 queryString으로 나누기
        String[] cmdBits = cmd.split("\\?", 2);
        // actionName은 cmdBits[0]에 들어있고, queryString은 cmdBits[1]에 들어있음
        actionName = cmdBits[0];
        // ?가 있으면 뒷부분을 queryString으로, 없으면 빈 문자열로 설정
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        // queryString을 & 기준으로 나누고, 각 부분을 = 기준으로 key와 value로 나눠 Map에 저장
        paramsMap = Arrays.stream(queryString.split("&"))
                .map(part -> part.split("=", 2))
                // key와 value가 모두 있는 경우만 남김 (예: "id=" 나 "=1" 은 제외)
                .filter(bits -> bits.length == 2 &&
                        !bits[0].trim().isEmpty() &&
                        !bits[1].trim().isEmpty())
                .collect(Collectors.toMap(
                        bits -> bits[0].trim(),
                        bits -> bits[1].trim(),
                        (previous, current) -> current // 같은 key가 여러 번 나오면 마지막 값 사용
                ));
    }

    // 명령어 이름 반환
    public String getActionName() {
        return actionName;
    }

    // getParam 메서드: paramName에 해당하는 값을 반환, 없으면 defaultValue 반환
    public String getParam(String paramName, String defaultValue) {
       return paramsMap.getOrDefault(paramName, defaultValue);
    }

    // getParamAsInt 메서드: paramName에 해당하는 값을 int로 변환해 반환
    // 값이 없거나 숫자가 아니면 defaultValue 반환
    public int getParamAsInt(String paramName, int defaultValue) {
        String value = getParam(paramName, "");

        if (value.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
