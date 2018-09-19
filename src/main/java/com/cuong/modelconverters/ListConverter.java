package com.cuong.modelconverters;

import java.util.Calendar;
import com.cuong.viewmodels.List;

public class ListConverter {

	public static List getViewModel(com.cuong.models.List list) {
		List viewModel = new List();
		viewModel.setId(list.getId());
		viewModel.setName(list.getName());
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(list.getCreatedAt());
		viewModel.setCreatedAt(calendar.getTime());
		viewModel.setNumberOfWords((list.getWordIDs() != null) ? list.getWordIDs().size() : Long.valueOf(0));
		return viewModel;
	}

	public static com.cuong.models.List getModel(List list) {
		com.cuong.models.List model = new com.cuong.models.List();
		model.setId(list.getId());
		model.setName(list.getName());
		model.setCreatedAt(list.getCreatedAt().getTime());
		return model;
	}

	public static void copy(com.cuong.models.List destination, com.cuong.models.List source) {
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.setCreatedAt(source.getCreatedAt());
		destination.setWordIDs(source.getWordIDs());
	}

}
