package test.task.stm.BuyTickets.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException duplicateKeyException, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + duplicateKeyException);
        ErrorResponse errorResponse = new ErrorResponse("Не удалось добавить объект", duplicateKeyException.getCause().getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException dataAccessException, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + dataAccessException);
        ErrorResponse errorResponse = new ErrorResponse("Не удалось найти объект", dataAccessException.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Ошибка в параметрах запроса", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException dataNotFoundException, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + dataNotFoundException);
        ErrorResponse errorResponse = new ErrorResponse("Данные не найдены", dataNotFoundException.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponse> handleRegistrationException(RegistrationException registrationException, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + registrationException);
        ErrorResponse errorResponse = new ErrorResponse("Регистрация этого пользователя невозможна", registrationException.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Невозможно сохранить объект", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException badRequestException, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + badRequestException);
        ErrorResponse errorResponse = new ErrorResponse("Невозможно выполнить запрос", badRequestException.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Неверные параметры запроса", exception.getCause().getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Невалидные параметры запроса", exception.getBody().getDetail());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Невалидные параметры запроса", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Невалидные параметры запроса", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Невалидные параметры запроса", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyException(Exception exception, HttpServletRequest request) {
        log.error(request.getRequestURL() + " raised " + exception);
        ErrorResponse errorResponse = new ErrorResponse("Непредвиденная ошибка", exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
