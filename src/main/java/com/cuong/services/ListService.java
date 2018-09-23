package com.cuong.services;

import java.io.File;

import com.cuong.daos.OnComplete;
import com.cuong.models.List;

public interface ListService extends BaseService<String, List> {

	void importTextFile(File file);

	void deleteCascade(String id, OnComplete<List> onComplete);

	void updateName(String id, String name, OnComplete<List> onComplete);

}
