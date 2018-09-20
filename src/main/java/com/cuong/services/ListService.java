package com.cuong.services;

import com.cuong.daos.OnComplete;
import com.cuong.eventhandlers.ListItemEventHandler;
import com.cuong.models.List;

public interface ListService {

	void setItemEventHandler(ListItemEventHandler handler);

	void removeItemEventHandler();

	void save(List list, OnComplete<List> onComplete);

	void delete(String listId, OnComplete<List> onComplete);

}
