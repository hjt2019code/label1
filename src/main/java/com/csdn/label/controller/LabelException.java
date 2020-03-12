package com.csdn.label.controller;

import com.csdn.entity.Result;
import com.csdn.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *统一的异常处理
 */
@ControllerAdvice
public class LabelException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e)
    {
      e.printStackTrace();
      return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
