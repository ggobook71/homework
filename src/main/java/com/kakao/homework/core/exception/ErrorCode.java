package com.kakao.homework.core.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C003", "Server Error"),
    INVALID_TYPE_VALUE(400, "C004", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C005", "Access is Denied"),
    INVALID_GET_LOCK(400, "C006", " get lock failed"),
    FAILED_GET_MONEY_DAYS(400, "B001", " 받기 가능기간이 만료 되었습니다."),
    FAILED_GET_MONEY_BAD_REQUEST(400, "B002", " 유효하지 않은 요청입니다."),
    FAILED_GET_MONEY_UN_TARGET(400, "B003", " 받기 대상자가 아닙니다."),
    FAILED_GET_MONEY_ONE_MORE(400, "B004", " 받기는 한번만 가능합니다."),
    FAILED_GET_MONEY_MINUTE(400, "B005", " 받기 가능시간이 만료 되었습니다."),
    FAILED_SEARCH_RECEIVER_INFO(400, "S001", " 조회 권한이 없거나 데이터가 존재하지 않습니다."),
    FAILED_GET_MONEY(400, "S001", " 할당 된 금액을 받기 실패 하였습니다."),
    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}