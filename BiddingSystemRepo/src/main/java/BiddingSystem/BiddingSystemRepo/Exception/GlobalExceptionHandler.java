package BiddingSystem.BiddingSystemRepo.Exception;


import BiddingSystem.BiddingSystemRepo.Exception.UserExceptions.BaseCustomException;
import BiddingSystem.BiddingSystemRepo.Model.Enum.AuctionStatusEnum;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleEnumError(
            HttpMessageNotReadableException ex,
            HttpServletRequest req
    ) {
        if (ex.getCause() instanceof InvalidFormatException ife &&
                ife.getTargetType().isEnum()) {

            ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            pd.setTitle("InvalidEnumValueException");
            pd.setDetail(
                    "Invalid value '" + ife.getValue() +
                            "' for enum " + ife.getTargetType().getSimpleName()
            );
            pd.setInstance(URI.create(req.getRequestURI()));
            pd.setProperty("timestamp", LocalDateTime.now().toString());

            pd.setProperty(
                    "allowedValues",
                    Arrays.stream(ife.getTargetType().getEnumConstants())
                            .map(Object::toString)
                            .toList()
            );

            return pd;
        }

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Malformed request");
        pd.setDetail("Invalid JSON format");
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("timestamp", LocalDateTime.now().toString());
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest req
    ) {
        if (ex.getMessage() != null && ex.getMessage().contains("No enum constant")) {

            ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            pd.setTitle("InvalidEnumValueException");
            pd.setDetail(ex.getMessage());
            pd.setInstance(URI.create(req.getRequestURI()));
            pd.setProperty("timestamp", LocalDateTime.now().toString());

            pd.setProperty(
                    "allowedValues",
                    Arrays.stream(AuctionStatusEnum.values())
                            .map(Enum::name)
                            .toList()
            );

            return pd;
        }

        throw ex;
    }

    @ExceptionHandler(BaseCustomException.class)
    public ProblemDetail handleBaseCustomException(BaseCustomException ex, HttpServletRequest req) {

        ProblemDetail pd = ProblemDetail.forStatus(ex.getStatus());
        pd.setTitle(ex.getClass().getSimpleName());
        pd.setDetail(ex.getMessage());
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("timestamp", LocalDateTime.now().toString());

        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest req
    ) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Validation Failed");
        pd.setDetail("One or more fields are invalid");
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("timestamp", LocalDateTime.now().toString());

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err ->
                        fieldErrors.put(err.getField(), err.getDefaultMessage())
                );

        pd.setProperty("errors", fieldErrors);

        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException ex
    ) {
        Map<String, Object> body = new HashMap<>();

        body.put("status", 400);
        body.put("error", "Bad Request");

        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        body.put("messages", messages);

        return ResponseEntity.badRequest().body(body);
    }



}
