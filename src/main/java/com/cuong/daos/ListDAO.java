package com.cuong.daos;

import com.cuong.models.List;

public interface ListDAO extends BaseDAO<String, List> {

	void updateName(String id, String name, OnComplete<List> onComplete);

}
