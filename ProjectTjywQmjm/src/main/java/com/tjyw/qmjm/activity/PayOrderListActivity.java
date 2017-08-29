package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.model.Order;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.result.RetroListResult;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.PayOrderListItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.SmoothRefreshLayout;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 25/08/2017.
 */
@RequiresPresenter(PayPresenter.class)
public class PayOrderListActivity extends BaseToolbarActivity<PayPresenter<PayOrderListActivity>>
        implements SmoothRefreshLayout.OnRefreshListener, OnApiPayPostListener.PostPayOrderListListener {

    static final int PAGE_SIZE = 10;

    @From(R.id.payOrderRefreshLayout)
    protected SmoothRefreshLayout payOrderRefreshLayout;

    @From(R.id.payOrderContainer)
    protected RecyclerView payOrderContainer;

    protected FastItemAdapter<PayOrderListItem> payOrderAdapter;

    protected int pageNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_pay_order_list);
        tSetToolBar(getString(R.string.atom_pub_resStringPayList));

        immersionBarWith()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();

        payOrderAdapter = new FastItemAdapter<PayOrderListItem>();
        payOrderAdapter.withOnClickListener(new FastAdapter.OnClickListener<PayOrderListItem>() {
            @Override
            public boolean onClick(View v, IAdapter<PayOrderListItem> adapter, PayOrderListItem item, int position) {
                IClientActivityLaunchFactory.launchPayOrderNameListActivity(PayOrderListActivity.this, item.src.name);
                return true;
            }
        });

        payOrderContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        payOrderContainer.setAdapter(payOrderAdapter);
        payOrderContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider16dp, R.dimen.atom_pubResDimenRecyclerViewDivider16dp)
                        .showLastDivider()
                        .build());

        payOrderRefreshLayout.setOnRefreshListener(this);
        payOrderRefreshLayout.autoRefresh(true);
    }

    @Override
    public void onRefreshBegin(boolean isRefresh) {
        if (isRefresh) {
            maskerShowProgressView(true);
            getPresenter().postPayOrderList(pageNo = 0 , PAGE_SIZE);
        } else {
            getPresenter().postPayOrderList((++ pageNo) * PAGE_SIZE, PAGE_SIZE);
        }
    }

    @Override
    public void onRefreshComplete(boolean isSuccessful) {

    }

    @Override
    public void postOnPayOrderListSuccess(RetroListResult<Order> result) {
        if (null == result) {
            maskerHideProgressView();
            payOrderRefreshLayout.setDisableLoadMore(true);
            payOrderRefreshLayout.refreshComplete();
            return ;
        } else if (result.size() == 0 && payOrderRefreshLayout.isRefreshing()) {
            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringFavoriteListNoData), 0);
        } else {
            maskerHideProgressView();
        }

        int size = result.size();
        List<PayOrderListItem> itemList = new ArrayList<PayOrderListItem>();
        for (int i = 0; i < size; i ++) {
            itemList.add(new PayOrderListItem(result.get(i)));
        }

        if (payOrderRefreshLayout.isRefreshing()) {
            payOrderAdapter.set(itemList);
        } else {
            payOrderAdapter.add(itemList);
        }

        payOrderRefreshLayout.setDisableLoadMore(payOrderAdapter.getAdapterItemCount() >= result.totalCount);
        payOrderRefreshLayout.refreshComplete();
    }
}