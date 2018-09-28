package com.cuong.daos;

import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.List;
import com.cuong.models.Word;

public interface ListDAO extends BaseDAO<String, List> {

	void listenWordIDsOf(String listId, EntityEventHandler<String> wordIDEventHandler);

	void removeListenWordIDsOf(String listId);

	void loadWordIDsOf(String listId, OnComplete<java.util.List<String>> onComplete);

	void updateName(String id, String name, OnComplete<List> onComplete);

	void deleteWordId(String listId, String wordId, OnComplete<String> onComplete);

}
