package ru.totsystems.stocks_api.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.totsystems.stocks_api.exception.MultipartFileConvertException;
import ru.totsystems.stocks_api.exception.NotFoundException;
import ru.totsystems.stocks_api.exception.XMLConvertException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseWebResponse> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseWebResponse> handleOtherException(@NonNull final RuntimeException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(XMLConvertException.class)
    public ResponseEntity<BaseWebResponse> handleXMLConvertException(@NonNull final XMLConvertException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(MultipartFileConvertException.class)
    public ResponseEntity<BaseWebResponse> handleMultipartFileConvertException(@NonNull final MultipartFileConvertException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseWebResponse(createErrorMessage(exc)));
    }

    private String createErrorMessage(Exception exception) {
        final String message = exception.getMessage();
        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return message;
    }
}

