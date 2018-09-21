package com.cuong.daos.impl;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.EntityChangeEventHandler;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.BaseManageable;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;

public abstract class GenericDAO<ID, T extends BaseManageable<T>> {

	private static final String ROOT_KEY = "ROOT_KEY";

	private Class<T> modelClass;

	private Map<String, ChildEventListener> childEventListeners = new HashMap<>();
	private Map<String, ValueEventListener> valueEventListeners = new HashMap<>();

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	public abstract DatabaseReference getRootRef();

	public Map<String, ChildEventListener> getChildEventListeners() {
		return childEventListeners;
	}

	public Map<String, ValueEventListener> getValueEventListeners() {
		return valueEventListeners;
	}

	public void listenAll(EntityEventHandler<T> entityEventHandler) {
		removeListenAll();
		ChildEventListener childEventListener = new ChildEventListener() {

			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				T entity = snapshot.getValue(getModelClass());
				entityEventHandler.onEntityRemoved(entity);
			}

			@Override
			public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
				T entity = snapshot.getValue(getModelClass());
				entityEventHandler.onEntityMoved(entity);
			}

			@Override
			public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
				T entity = snapshot.getValue(getModelClass());
				entityEventHandler.onEntityChanged(entity);
			}

			@Override
			public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
				T entity = snapshot.getValue(getModelClass());
				entityEventHandler.onEntityAdded(entity);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				entityEventHandler.onEntityCancelled(error.getMessage());
			}
		};
		getChildEventListeners().put(ROOT_KEY, childEventListener);
		getRootRef().addChildEventListener(childEventListener);
	}

	public void removeListenAll() {
		ChildEventListener childEventListener = getChildEventListeners().get(ROOT_KEY);
		if (childEventListener != null) {
			getRootRef().removeEventListener(childEventListener);
		}
	}

	public void listen(String id, EntityChangeEventHandler<T> entityChangeEventHandler) {
		ValueEventListener valueEventListener = new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				T entity = snapshot.getValue(getModelClass());
				entityChangeEventHandler.onEntityChange(entity);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				entityChangeEventHandler.onCancelled(error.getMessage());
			}
		};
		getValueEventListeners().put(id, valueEventListener);
		getRootRef().child(id).addValueEventListener(valueEventListener);
	}

	public void removeListen(String id) {
		ValueEventListener valueEventListener = getValueEventListeners().get(id);
		if (valueEventListener != null) {
			getRootRef().child(id).removeEventListener(valueEventListener);
		}
	}

	public void loadAll(OnComplete<List<T>> onComplete) {
		getRootRef().addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				List<T> results = new ArrayList<>();
				for (DataSnapshot entitySnapshot : snapshot.getChildren()) {
					T entity = entitySnapshot.getValue(getModelClass());
					results.add(entity);
				}
				if (onComplete != null) {
					onComplete.onSuccess(results);
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				if (onComplete != null) {
					onComplete.onError(error.getMessage());
				}
			}
		});
	}

	public void load(String id, OnComplete<T> onComplete) {
		getRootRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				T entity = snapshot.getValue(getModelClass());
				if (onComplete != null) {
					onComplete.onSuccess(entity);
				} else {
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				if (onComplete != null) {
					onComplete.onError(error.getMessage());
				}
			}
		});
	}

	public void save(T entity, OnComplete<T> onComplete) {
		DatabaseReference entityRef = getRootRef().push();
		entity.setId(entityRef.getKey());
		Date currentDate = new Date();
		entity.setCreatedAt(currentDate.getTime());
		entity.setUpdatedAt(currentDate.getTime());
		entityRef.setValue(entity, new CompletionListener() {

			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				if (error == null) {
					if (onComplete != null) {
						onComplete.onSuccess(entity);
					}
				} else {
					if (onComplete != null) {
						onComplete.onError(error.getMessage());
					}
				}
			}
		});
	}

	public void update(T entity, OnComplete<T> onComplete) {
		getRootRef().child(entity.getId()).runTransaction(new Handler() {

			@Override
			public void onComplete(DatabaseError error, boolean committed, DataSnapshot currentData) {
				if (error == null) {
					if (onComplete != null) {
						onComplete.onSuccess(entity);
					}
				} else {
					if (onComplete != null) {
						onComplete.onError(error.getMessage());
					}
				}
			}

			@Override
			public Result doTransaction(MutableData currentData) {
				if (currentData == null) {
					return Transaction.success(currentData);
				}
				T currentEntity = currentData.getValue(getModelClass());
				entity.setUpdatedAt(new Date().getTime());
				entity.cloneTo(currentEntity);
				return Transaction.success(currentData);
			}
		});
	}

	public void delete(String id, OnComplete<T> onComplete) {
		load(id, new OnComplete<T>() {

			@Override
			public void onSuccess(T object) {
				getRootRef().child(id).removeValue(new CompletionListener() {
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						if (error == null) {
							if (onComplete != null) {
								onComplete.onSuccess(object);
							}
						} else {
							if (onComplete != null) {
								onComplete.onError(error.getMessage());
							}
						}
					}
				});
			}

			@Override
			public void onError(String error) {
				if (onComplete != null) {
					onComplete.onError(error);
				}
			}
		});
	}

	public Class<T> getModelClass() {
		return modelClass;
	}

}
