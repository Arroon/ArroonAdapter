package com.arroon.listview.adapter.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ArroonAdapter<E> extends BaseAdapter {

	private Context context;
	private int layoutId;
	private List<E> list;
	private List<DataType> dataTypes;
	private AdapterImageLoader imageLoader;

	public ArroonAdapter(Context context, int layoutId, List<E> list,
			List<DataType> dataTypes, AdapterImageLoader displayImage) {
		super();
		this.context = context;
		this.layoutId = layoutId;
		this.list = list;
		this.dataTypes = dataTypes;
		this.imageLoader = displayImage;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutId, arg2,
					false);
		}
		Object o = list.get(position);
		for (DataType dataType : dataTypes) {
			View view = ViewHolder.get(convertView, dataType.viewId);
			try {
				Method method = o.getClass().getMethod(dataType.methodName);
				String text = (String) method.invoke(o);
				switch (dataType.viewType) {
				case TEXT_VIEW:
					((TextView) view).setText(text);
					break;
				case IMAGE_VIEW:
					imageLoader.displayImage((ImageView) view, text);
					break;
				case BUTTON:
					((Button) view).setText(text);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		addInternalClickListener(convertView, position, list.get(position));
		return convertView;
	}

	@SuppressLint("UseSparseArrays")
	public void setOnInViewClickListener(Integer key,
			onInternalClickListener onClickListener) {
		if (canClickItem == null)
			canClickItem = new HashMap<Integer, onInternalClickListener>();
		canClickItem.put(key, onClickListener);
	}

	public interface onInternalClickListener {
		public void OnClickListener(View parentV, View v, Integer position,
				Object values);
	}

	public Map<Integer, onInternalClickListener> canClickItem;

	private void addInternalClickListener(final View itemV,
			final Integer position, final Object valuesMap) {
		if (canClickItem != null) {
			for (Integer key : canClickItem.keySet()) {
				View inView = itemV.findViewById(key);
				final onInternalClickListener inviewListener = canClickItem
						.get(key);
				if (inView != null && inviewListener != null) {
					inView.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							inviewListener.OnClickListener(itemV, v, position,
									valuesMap);
						}
					});
				}
			}
		}
	}

	public static interface AdapterImageLoader {
		public void displayImage(ImageView imageView, String url);
	}
}
