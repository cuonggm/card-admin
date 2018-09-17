package com.cuong.eventhandlers;

import com.cuong.models.List;

public interface ListItemEventHandler {

	void onListAdded(List list);

	void onListChanged(List list);

	void onListRemoved(List list);

	void onListMoved(List list);

	void onListCancelled(String error);

}
