package com.cuong.services;

import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.List;

public interface ListService {

	void setItemEventHandler(EntityEventHandler<List> handler);

	void removeItemEventHandler();

	void save(List list, OnComplete<List> onComplete);

	void delete(String listId, OnComplete<List> onComplete);

}
