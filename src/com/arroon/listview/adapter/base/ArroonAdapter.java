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
	private List<E> list;
	private int layoutId;
	private String[] from;
	private int[] to;
	private AdapterImageLoader imageLoader;

	public ArroonAdapter(Context context, int layoutId, List<E> list,
			String[] from, int[] to) {
		super();
		this.context = context;
		this.list = list;
		this.layoutId = layoutId;
		this.from = from;
		this.to = to;
	}

	public static interface AdapterImageLoader {
		public void displayImage(ImageView imageView, String url);
	}

	public void setImageLoader(AdapterImageLoader imageLoader) {
		this.imageLoader = imageLoader;
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
		final int count = to.length;
		for (int i = 0; i < count; i++) {
			View view = ViewHolder.get(convertView, to[i]);
			try {
				Method method = o.getClass().getMethod(from[i]);
				String text = (String) method.invoke(o);
				if (view instanceof ImageView) {
					if (imageLoader != null) {
						imageLoader.displayImage((ImageView) view, text);
					}
				} else if (view instanceof Button) {
					((Button) view).setText(text);
				} else if (view instanceof TextView) {
					((TextView) view).setText(text);
				} else {
					throw new IllegalStateException(view.getClass().getName()
							+ " is not a "
							+ " view that can be bounds by this SimpleAdapter");
				}// 可以针对业务适配更多空间
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		addInternalClickListener(convertView, position, list.get(position));
		return convertView;
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

	@SuppressLint("UseSparseArrays")
	public void setOnInViewClickListener(Integer key,
			onInternalClickListener onClickListener) {
		if (canClickItem == null)
			canClickItem = new HashMap<Integer, onInternalClickListener>();
		canClickItem.put(key, onClickListener);
	}
}
