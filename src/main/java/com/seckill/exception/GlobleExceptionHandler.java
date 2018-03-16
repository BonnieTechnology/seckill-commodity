package com.seckill.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
/**
 * 定义全局的异常处理
 * @author Administrator
 *
 */
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seckill.common.Result;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {


	/**
	 * 拦截所有请求中的异常
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public Result exceptionHander(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		if(e instanceof GlobleException) {
			GlobleException ge = (GlobleException) e;
			return Result.error(ge.getStatus(), ge.getMsg());
		}else if(e instanceof BindException) {
			BindException ex = (BindException) e;
			List<ObjectError> errors = ex.getAllErrors(); 
			String msg = errors.get(0).getDefaultMessage();
			return Result.error(501, msg);
		}else {
			return Result.error(501, "服务器异常，请稍后再试！");
		}
	}

}
