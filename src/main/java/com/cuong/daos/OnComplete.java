package com.cuong.daos;

public interface OnComplete<T> {

	void onSuccess(T object);

	void onError(String error);

}
