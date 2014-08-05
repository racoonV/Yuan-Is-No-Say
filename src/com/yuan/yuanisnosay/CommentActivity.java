package com.yuan.yuanisnosay;

import java.util.ArrayList;

import org.json.JSONObject;

import com.yuan.yuanisnosay.server.ServerAccess;
import com.yuan.yuanisnosay.server.ServerAccess.ServerResponseHandler;
import com.yuan.yuanisnosay.ui.adpater.ConfessItem;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends ActionBarActivity {
	
	public static final String PARENT_CONFESS = "confessItem";
	public static final String POST_ID = "postID";
	private TextView mParentConfess;
	private static ListView mCommentList;
	private static EditText mCommentContent;
	private static Button mCommentSend;
	private int postID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		mParentConfess = (TextView) findViewById(R.id.textView_parentConfess);
		mCommentList = (ListView) findViewById(R.id.listView_commentList);
		mCommentContent = (EditText) findViewById(R.id.editText_commentContent);
		mCommentSend = (Button) findViewById(R.id.button_commentSend);
		
		Intent intent = getIntent();
		postID = intent.getIntExtra(POST_ID, 0);
		
		setParentConfess(postID);
		setCommentListContent(postID);
		
	
		
	}
	//TODO
	public void setParentConfess(int postID) {
		//ConfessItem confessItem = ServerAccess.getCommentList(postID, handler)
		return ;		
	}
	
	private void setCommentListContent(final int postID) {
		final ArrayList<String> strs = new ArrayList<String>();
		strs.add("first");
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_dropdown_item_1line, strs);
		mCommentList.setAdapter(adapter);
		mCommentSend.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View arg0) { 
                // TODO Auto-generated method stub 
            	String newComment = mCommentContent.getText().toString();
            	if (0 == newComment.length()) {
            		Toast.makeText(getApplicationContext(), "请输入评论内容", 1000).show();
            	} else {
            		//TODO 发送评论
            		ServerAccess.getCommentList(postID, new ServerResponseHandler() {

            			@Override
            			public void onSuccess(JSONObject result) {
            				// TODO Auto-generated method stub
            				
            			}

            			@Override
            			public void onFailure(Throwable error) {
            				// TODO Auto-generated method stub
            				
            			}
            			
            		});
            		strs.add(newComment);
                	adapter.notifyDataSetChanged(); 
                	mCommentContent.setText("");
                	
            	}
            } 

        });
		
		//TODO 设置CommentList
		
		
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
