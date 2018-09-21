package com.cuong.eventhandlers;

public interface EntityChangeEventHandler<T> {

	void onEntityChange(T entity);

	void onCancelled(String error);

}
