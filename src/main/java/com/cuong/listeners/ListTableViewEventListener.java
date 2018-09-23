package com.cuong.listeners;

import com.cuong.viewmodels.List;

public interface ListTableViewEventListener {

	void onNameEditCommit(List list, String oldValue, String newValue);

	void onShowWordsButtonClicked(List list);

	void onDeleteButtonClicked(List list);

}
