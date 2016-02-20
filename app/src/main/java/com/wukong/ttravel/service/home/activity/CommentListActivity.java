package com.wukong.ttravel.service.home.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.wukong.ttravel.R;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wukong on 2/20/16.
 */
public class CommentListActivity extends Activity {

    @Bind(R.id.action_bar_title)
    TextView actionBarTitle;

    @Bind(R.id.comment_list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);

        actionBarTitle.setText("全部评价");




    }
}
