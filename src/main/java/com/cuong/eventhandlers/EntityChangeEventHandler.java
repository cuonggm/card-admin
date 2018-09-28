package com.cuong.eventhandlers;

public interface EntityChangeEventHandler<T> {

	void onEntityChange(String id, T entity);

	void onCancelled(String error);

}
