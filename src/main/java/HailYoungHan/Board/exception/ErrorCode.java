package HailYoungHan.Board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST */
    NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 이름은 이미 존재하는 멤버입니다: %s"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "해당 이메일은 이미 존재합니다: %s"),

    /* 404 NOT FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 멤버 ID가 없습니다: %s"),
    MEMBER_NOT_FOUND_BY_NAME(HttpStatus.NOT_FOUND, "해당 멤버 이름이 없습니다: %s"),
    POST_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 게시물 ID가 없습니다: %s"),
    COMMENT_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 댓글 ID가 없습니다: %s");


    private final HttpStatus httpStatus;
    private final String detail; // %s 가 포함된 detail
}