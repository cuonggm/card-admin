package com.cuong.eventhandlers;

public interface EntityEventHandler<T> {

	void onEntityAdded(T entity);

	void onEntityChanged(T entity);

	void onEntityRemoved(T entity);

	void onEntityMoved(T entity);

	void onEntityCancelled(String error);

}
