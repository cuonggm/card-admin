package com.cuong.utils;

import java.net.URL;

public class PathUtils {

	public static URL getViewFile(String viewName) {
		return getPath("/views/" + viewName);
	}

	private static URL getPath(String path) {
		if (PathUtils.class.getResource("/resources" + path) != null) {
			return PathUtils.class.getResource("/resources" + path);
		}
		return PathUtils.class.getResource(path);
	}

	private PathUtils() {

	}

}
