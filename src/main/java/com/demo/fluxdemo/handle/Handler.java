package com.demo.fluxdemo.handle;

import com.demo.fluxdemo.exception.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class Handler {

    /**
     * 处理WebExchangeBindException异常
     * @param bindException
     * @return
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleBindException(WebExchangeBindException bindException){
        return new ResponseEntity<String>(toStr(bindException),HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理checkException异常
     * @param checkException
     * @return
     */
    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handleCheckException(CheckException checkException){
        return new ResponseEntity<String>(checkException.getFieldName() + "错误的值" +
                checkException.getFieldValue(),HttpStatus.BAD_REQUEST);
    }


    /**
     * 参数绑定异常转化为字符串
     * @param bindException
     * @return
     */
    private String toStr(WebExchangeBindException bindException) {
        //得到list<FieldError> 然后Stream化，再映射成字符串
        return bindException.getFieldErrors().stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .reduce("" ,(s1,s2) -> s1 + "\n" + s2);
    }

}
