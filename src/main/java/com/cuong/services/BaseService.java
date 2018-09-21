package com.cuong.services;

import java.util.List;

import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.EntityChangeEventHandler;
import com.cuong.eventhandlers.EntityEventHandler;

public interface BaseService<ID, T> {

	void listenAll(EntityEventHandler<T> entityEventHandler);

	void removeListenAll();

	void listen(ID id, EntityChangeEventHandler<T> entityChangeEventHandler);

	void removeListen(ID id);

	void loadAll(OnComplete<List<T>> onComplete);

	void load(ID id, OnComplete<T> onComplete);

	void save(T entity, OnComplete<T> onComplete);

	void update(T entity, OnComplete<T> onComplete);

	void delete(ID id, OnComplete<T> onComplete);

}
