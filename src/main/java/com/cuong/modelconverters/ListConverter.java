package com.cuong.modelconverters;

import java.util.Calendar;
import com.cuong.viewmodels.List;

public class ListConverter {

	public static List getViewModel(com.cuong.models.List list) {
		List viewModel = new List();
		viewModel.setId(list.getId());
		viewModel.setName(list.getName());
		Calendar createdAtCalendar = Calendar.getInstance();
		createdAtCalendar.setTimeInMillis(list.getCreatedAt());
		viewModel.setCreatedAt(createdAtCalendar.getTime());
		Calendar updatedAtCalendar = Calendar.getInstance();
		updatedAtCalendar.setTimeInMillis(list.getUpdatedAt());
		viewModel.setUpdatedAt(updatedAtCalendar.getTime());
		viewModel.setNumberOfWords((list.getWordIDs() != null) ? list.getWordIDs().size() : Long.valueOf(0));
		return viewModel;
	}

}
