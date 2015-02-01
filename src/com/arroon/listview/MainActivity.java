package com.arroon.listview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.arroon.listview.adapter.base.ArroonAdapter;
import com.arroon.listview.adapter.base.ArroonAdapter.AdapterImageLoader;
import com.arroon.listview.adapter.base.DataType;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends ActionBarActivity {

	List<Movie> movies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(this);
		setContentView(listView);

		movies = new ArrayList<>();
		movies.add(new Movie("露水红颜", "drawable://" + R.drawable.image1, "立即下载"));
		movies.add(new Movie("别相信任何人", "drawable://" + R.drawable.image2,
				"立即观看"));
		movies.add(new Movie("PENGUINS", "drawable://" + R.drawable.image3,
				"付费观看"));
		movies.add(new Movie("CARGO", "drawable://" + R.drawable.image4, "立即下载"));
		movies.add(new Movie("单身男女2", "drawable://" + R.drawable.image5, "立即下载"));
		movies.add(new Movie("GIVER", "drawable://" + R.drawable.image6, "立即下载"));

		List<DataType> dataTypes = new ArrayList<>();
		dataTypes.add(new DataType(DataType.ViewType.TEXT_VIEW, "getName",
				R.id.tv_1));
		dataTypes.add(new DataType(DataType.ViewType.IMAGE_VIEW, "getLogo",
				R.id.iv));
		dataTypes.add(new DataType(DataType.ViewType.BUTTON, "getButtonText",
				R.id.btn));

		AdapterImageLoader imageLoader = new AdapterImageLoader() {
			@Override
			public void displayImage(ImageView imageView, String url) {
				ImageLoader.getInstance().init(
						new ImageLoaderConfiguration.Builder(
								getApplicationContext()).build());
				ImageLoader.getInstance().displayImage(url, imageView);
			}
		};

		ArroonAdapter<Movie> adapter = new ArroonAdapter<Movie>(this,
				R.layout.item_movie, movies, dataTypes, imageLoader);
		listView.setAdapter(adapter);
		adapter.setOnInViewClickListener(R.id.btn,
				new ArroonAdapter.onInternalClickListener() {
					@Override
					public void OnClickListener(View parentV, View v,
							Integer position, Object values) {
						Toast.makeText(
								MainActivity.this,
								movies.get(position).getButtonText()
										+ movies.get(position).getName(),
								Toast.LENGTH_SHORT).show();
					}
				});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Toast.makeText(MainActivity.this, "点击了Item " + position + " .",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
