package com.cuong.services;

import com.cuong.eventhandlers.ListItemEventHandler;

public interface ListService {

	void setListItemEventHandler(ListItemEventHandler handler);

	void removeListItemEventHandler();

	void delete(String listId);

}
