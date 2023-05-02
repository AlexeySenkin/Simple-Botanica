package ru.botanica.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.botanica.exceptions.PlantExistsException;
import ru.botanica.exceptions.ServerHandleException;
import ru.botanica.responses.AppResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Отлавливает ошибки, связанные с существованием или отсутствием растения в БД
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchPlantExistsException(PlantExistsException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Отлавливает ошибки, связанные с работой сервера (обработка данных,
     * передача данных, запись данных, удаление данных)
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchServerHandleException(ServerHandleException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Отлавливает ошибки, связанные с неподдерживаемыми форматами файлов
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        log.error(exception.getMessage(), exception);
//        На всякий обратно на фронт не передаю реальные данные ошибки, а лишь прокидываю общую фразу, т.к.
//        это стандартное исключение. Отлов исключений ниже сделан по такому же принципу
        return new ResponseEntity<>(new AppResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                "Неподдерживаемый формат файлов"), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Отлавливает ошибки, связанные с невозможными значениями для полей
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchPropertyValueException(PropertyValueException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                "Указанные значения или их часть не могут быть null"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Отлавливает ошибки, связанные с невозможностью сохранить значение в БД
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                "Указанные значения или их часть не могут быть null"), HttpStatus.BAD_REQUEST);
    }

    /**
     * Отлавливает ошибки, связанные с null-значениями для обязательных полей, классов или параметров
     *
     * @param exception Ошибка
     * @return Сообщение об ошибке
     */
    @ExceptionHandler
    public ResponseEntity<?> catchException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new AppResponse(HttpStatus.BAD_REQUEST.value(),
                "Неизвестная ошибка"), HttpStatus.BAD_REQUEST);
    }
}
