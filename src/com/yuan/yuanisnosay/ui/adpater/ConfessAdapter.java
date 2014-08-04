package com.yuan.yuanisnosay.ui.adpater;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.yuan.yuanisnosay.R;
import com.yuan.yuanisnosay.Util;

public class ConfessAdapter extends BaseAdapter {
	public static final int TYPE_NORMAL = 0;	//主界面表白类型
	public static final int TYPE_MINE = 1;		//个人界面表白类型 
	
	private int mType;	//adapter类型 
	Context mContext;
	LayoutInflater mInflater;
	LinkedList<ConfessItem> mConfessList;
	
	ImageLoader mImageLoader;
	DisplayImageOptions mOptions;
	ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	static class ViewHolder {
		TextView content;
		TextView publishDate;
		TextView position;
		TextView author;
		Button btnFlower;
		Button btncomment;
		ImageView ivIcon;
		View layoutContent;
		View layoutPicture;
		View layoutInfo;
	}

	public ConfessAdapter(Context context, int type ,LinkedList<ConfessItem> confessList) {
		mType = type;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mConfessList = confessList;
		
		mImageLoader=ImageLoader.getInstance();
		mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
		mOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
//		.displayer(new RoundedBitmapDisplayer(20))
		.build();
	}

	@Override
	public int getCount() {
		return mConfessList.size();
	}

	@Override
	public Object getItem(int position) {
		return mConfessList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			if(mType == TYPE_NORMAL){
				convertView=mInflater.inflate(R.layout.item_confess, null);
			}else{
				convertView=mInflater.inflate(R.layout.item_my_confess, null);
			}
			
			viewHolder=new ViewHolder();
			viewHolder.content=(TextView)convertView.findViewById(R.id.textView_confessItem_content);
			viewHolder.publishDate=(TextView)convertView.findViewById(R.id.textView_confessItem_publishTime);
			viewHolder.position=(TextView)convertView.findViewById(R.id.textView_confessItem_position);
			
			viewHolder.btnFlower=(Button)convertView.findViewById(R.id.button_confessItem_flowers);
			viewHolder.btncomment=(Button)convertView.findViewById(R.id.button_confessItem_comment);
			
			viewHolder.layoutContent=convertView.findViewById(R.id.relativeLayout_content);
			viewHolder.layoutPicture=convertView.findViewById(R.id.relativeLayout_picture);
			viewHolder.layoutInfo=convertView.findViewById(R.id.relativeLayout_info);
			
			if(mType == TYPE_NORMAL){
				viewHolder.author=(TextView)convertView.findViewById(R.id.textView_confessItem_author);
				viewHolder.ivIcon=(ImageView)convertView.findViewById(R.id.imageView_confessItem_icon);
			}
			
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		ConfessItem curConfess=mConfessList.get(position);
		viewHolder.content.setText(curConfess.getContent());
		viewHolder.publishDate.setText(Util.formatDateTime(curConfess.getPublishDate()));
		viewHolder.position.setText(curConfess.getPosition().getRegionName());
		
		
		viewHolder.layoutPicture.setVisibility(View.GONE);
//		viewHolder.layoutInfo.setVisibility(View.GONE);
		
		if(mType == TYPE_NORMAL){
			viewHolder.author.setText(curConfess.getAuthor());
			mImageLoader.displayImage(curConfess.getIcon(), viewHolder.ivIcon,mOptions,animateFirstListener);
		}
		
		return convertView;
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
