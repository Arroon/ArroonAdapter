package com.arroon.listview.adapter.base;

public class DataType {

	public static enum ViewType {
		TEXT_VIEW, IMAGE_VIEW, BUTTON
	};

	public ViewType viewType;
	public String methodName;
	public int viewId;

	/**
	 * 
	 * @param viewType
	 *            TEXT_VIEW,TEXT_VIEW
	 * @param methodName
	 *            ������
	 * @param viewId
	 *            �ؼ�id
	 */
	public DataType(ViewType viewType, String methodName, int viewId) {
		super();
		this.viewType = viewType;
		this.methodName = methodName;
		this.viewId = viewId;
	}
}
