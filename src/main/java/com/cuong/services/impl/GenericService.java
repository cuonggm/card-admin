package com.cuong.services.impl;

import java.util.List;
import com.cuong.daos.BaseDAO;
import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.EntityChangeEventHandler;
import com.cuong.eventhandlers.EntityEventHandler;

public abstract class GenericService<ID, T> {

	public abstract BaseDAO<ID, T> getMainDAO();

	public void listenAll(EntityEventHandler<T> entityEventHandler) {
		getMainDAO().listenAll(entityEventHandler);
	}

	public void removeListenAll() {
		getMainDAO().removeListenAll();
	}

	public void listen(ID id, EntityChangeEventHandler<T> entityChangeEventHandler) {
		getMainDAO().listen(id, entityChangeEventHandler);
	}

	public void removeListen(ID id) {
		getMainDAO().removeListen(id);
	}

	public void loadAll(OnComplete<List<T>> onComplete) {
		getMainDAO().loadAll(onComplete);
	}

	public void load(ID id, OnComplete<T> onComplete) {
		getMainDAO().load(id, onComplete);
	}

	public void save(T entity, OnComplete<T> onComplete) {
		getMainDAO().save(entity, onComplete);
	}

	public void update(T entity, OnComplete<T> onComplete) {
		getMainDAO().update(entity, onComplete);
	}

	public void delete(ID id, OnComplete<T> onComplete) {
		getMainDAO().delete(id, onComplete);
	}

}
