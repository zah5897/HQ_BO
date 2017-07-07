package com.zhan.bottle.ui.fragments.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhan.bottle.R;
import com.zhan.bottle.model.service.BottleService;
import com.zhan.bottle.model.service.UserManager;
import com.zhan.bottle.ui.activitys.EditBottleActivity;
import com.zhan.bottle.ui.activitys.LoginActivity;
import com.zhan.bottle.ui.base.LazyFragment;
import com.zhan.bottle.ui.widget.BottlePoolView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zah on 2017/6/27.
 */

public class TabSeaFragment extends LazyFragment {
    @BindView(R.id.bottle_pool)
    BottlePoolView bottlePool;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sea, container, false);
        initView(view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initComponent() {
        //adapter = new StarCardAdapter(new ArrayList<StarModel>());
        BottleService.get().loadTabStar();
    }

    private void initView(View view) {
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(adapter);
//        // mRecyclerView.addItemDecoration(getItemDecoration());
//        mRecyclerView.setEmptyView(emptyView);
//        emptyView.setBtnListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lazyLoad();
//            }
//        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTabStarEvent(String event) {
//        adapter.setCur_star_type(event.current_star_type);
//        if (event.fortune != null) {
//            adapter.setFortune(event.fortune);
//        }
//        if (event.stars != null) {
//            event.stars.add(0, new StarModel(-1));
////            event.stars.add(new StarModel(-2));
//            adapter.setData(event.stars);
//        }
    }

    @OnClick(R.id.throw_one)
    public void onViewClicked() {
        if (UserManager.get().isLogin()) {
            to(EditBottleActivity.class);
        } else {
            to(LoginActivity.class);
        }
    }
}
