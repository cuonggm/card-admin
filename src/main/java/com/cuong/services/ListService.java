package com.cuong.services;

import com.cuong.eventhandlers.ListItemEventHandler;
import com.cuong.models.List;

public interface ListService {

	void setListItemEventHandler(ListItemEventHandler handler);

	void removeListItemEventHandler();

	void save(List list);

	void delete(String listId);

}
