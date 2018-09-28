package com.cuong.services;

import com.cuong.eventhandlers.EntityEventHandler;
import com.cuong.models.Word;

public interface SpecifiedListService {

	void setWordEventHandler(EntityEventHandler<Word> wordEventHandler);

	void deleteWordCascade(String wordId);

}
