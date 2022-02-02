package br.com.reserve.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private T data;
	private List<String> errors;
	
	public Response() {
		super();
		errors = new ArrayList<String>();
	}

	public Response(T data, List<String> errors) {
		super();
		this.data = data;
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
