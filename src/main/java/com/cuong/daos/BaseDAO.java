package com.cuong.daos;

import java.util.List;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

public interface BaseDAO<ID, T> {

	void listenAll(ChildEventListener childEventListener);

	void removeListenAll();

	void listen(ID id, ValueEventListener valueEventListener);

	void removeListen(ID id);

	void loadAll(OnComplete<List<T>> onComplete);

	void load(ID id, OnComplete<T> onComplete);

	void save(T entity, OnComplete<T> onComplete);

	void update(T entity, OnComplete<T> onComplete);

	void delete(ID id, OnComplete<T> onComplete);

}
