package com.cuong.daos;

import java.util.List;

public interface BaseDAO<ID, T> {

	void listenAll(OnComplete<T> onComplete);

	void removeListenAll();

	void listen(ID id, OnComplete<T> onComplete);

	void removeListen(ID id);

	void loadAll(OnComplete<List<T>> onComplete);

	void load(ID id, OnComplete<T> onComplete);

	void save(T entity, OnComplete<T> onComplete);

	void update(T entity, OnComplete<T> onComplete);

	void delete(ID id, OnComplete<T> onComplete);

}
