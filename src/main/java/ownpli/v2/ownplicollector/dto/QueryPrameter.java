package ownpli.v2.ownplicollector.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
public class QueryPrameter {

    private final String name;
    private final String value;

    @Builder
    public QueryPrameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 쿼리 파라미터 생성
     * @param name
     * @param value
     * @return
     */
    public static QueryPrameter of(String name, String value) {
        return QueryPrameter.builder()
                .name(name)
                .value(value)
                .build();
    }

    /**
     * value가 null인지 확인
     * @return
     */
    public boolean isNotNull() {
        return value != null;
    }

    /**
     * 쿼리문 반환
     * name:value
     * @return
     */
    public String getQuery() {
        encodeValue(value);
        return name + ":" + value;
    }

    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
