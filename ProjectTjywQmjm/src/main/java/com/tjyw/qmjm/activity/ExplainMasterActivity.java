package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostExplainListener;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;
import com.tjyw.qmjm.holder.HeaderWordHolder;
import com.tjyw.qmjm.holder.NameBaseInfoHolder;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class ExplainMasterActivity extends BaseToolbarActivity<NamingPresenter<ExplainMasterActivity>> implements OnApiPostErrorListener, OnApiPostExplainListener {

    @From(R.id.explainMasterHeaderContainer)
    protected ViewGroup explainMasterHeaderContainer;

    @From(R.id.explainNameContainer)
    protected ViewGroup explainNameContainer;

    @From(R.id.explainEvaluateValue)
    protected TextView explainEvaluateValue;

    @From(R.id.explainEvaluateDesc)
    protected TextView explainEvaluateDesc;

    @From(R.id.explainOverview)
    protected TextView explainOverview;

    @From(R.id.explainZodiac)
    protected TextView explainZodiac;

    @From(R.id.explainDestiny)
    protected TextView explainDestiny;

    @From(R.id.explainSanCai)
    protected TextView explainSanCai;

    @From(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

    protected ExplainMasterAdapter explainMasterAdapter;

    protected ListRequestParam listRequestParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_explain_host);
            tSetToolBar(getString(R.string.atom_pub_resStringMasterTabExplain));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(true)
                    .init();
        }

        explainOverview.setSelected(true);
        explainOverview.setOnClickListener(this);
        explainZodiac.setOnClickListener(this);
        explainDestiny.setOnClickListener(this);
        explainSanCai.setOnClickListener(this);

        explainMasterContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case ExplainMasterAdapter.POSITION.OVERVIEW:
                        setSelectedTab(explainOverview);
                        break ;
                    case ExplainMasterAdapter.POSITION.ZODIAC:
                        setSelectedTab(explainZodiac);
                        break ;
                    case ExplainMasterAdapter.POSITION.DESTINY:
                        setSelectedTab(explainDestiny);
                        break ;
                    case ExplainMasterAdapter.POSITION.SANCAI:
                        setSelectedTab(explainSanCai);
                }
            }
        });

        maskerShowProgressView(false);
        getPresenter().postExplain(
                listRequestParam.surname,
                listRequestParam.name,
                listRequestParam.day,
                listRequestParam.gender
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.explainOverview:
                setSelectedTab(v);
                explainMasterAdapter.showOverviewFragment(explainMasterContainer);
                break ;
            case R.id.explainZodiac:
                setSelectedTab(v);
                explainMasterAdapter.showZodiacFragment(explainMasterContainer);
                break ;
            case R.id.explainDestiny:
                setSelectedTab(v);
                explainMasterAdapter.showDestinyFragment(explainMasterContainer);
                break ;
            case R.id.explainSanCai:
                setSelectedTab(v);
                explainMasterAdapter.showSanCaiFragment(explainMasterContainer);
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        explainMasterHeaderContainer.setVisibility(View.INVISIBLE);
        maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
    }

    @Override
    public void postOnExplainSuccess(Explain explain) {
        maskerHideProgressView();
        explainMasterHeaderContainer.setVisibility(View.VISIBLE);
        explainMasterContainer.setAdapter(
                explainMasterAdapter = ExplainMasterAdapter.newInstance(getSupportFragmentManager(), explain)
        );

        explainEvaluateValue.setText(getString(R.string.atom_pub_resStringExplainEvaluate, explain.nameScore.evaluation));
        explainEvaluateDesc.setText(explain.nameScore.desc);

        explainNameContainer.removeAllViews();
        if (!ArrayUtil.isEmpty(explain.wordsList)) {
            Observable.from(explain.wordsList)
                    .take(4)
                    .compose(RxSchedulersHelper.<NameCharacter>io_main())
                    .subscribe(new Action1<NameCharacter>() {
                        @Override
                        public void call(NameCharacter character) {
                            explainNameContainer.addView(
                                    HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), character),
                                    explainNameContainer.getChildCount()
                            );
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
        }

        new NameBaseInfoHolder(findViewById(android.R.id.content)).baseInfo(explain.nameZodiac);
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        super.maskerOnClick(view, clickLabelRes);
        maskerShowProgressView(false);
        getPresenter().postExplain(
                listRequestParam.surname,
                listRequestParam.name,
                listRequestParam.day,
                listRequestParam.gender
        );
    }

    protected void setSelectedTab(View view) {
        if (! view.isSelected()) {
            view.setSelected(true);

            switch (view.getId()) {
                case R.id.explainOverview:
                    explainZodiac.setSelected(false);
                    explainDestiny.setSelected(false);
                    explainSanCai.setSelected(false);
                    break ;
                case R.id.explainZodiac:
                    explainOverview.setSelected(false);
                    explainDestiny.setSelected(false);
                    explainSanCai.setSelected(false);
                    break ;
                case R.id.explainDestiny:
                    explainOverview.setSelected(false);
                    explainZodiac.setSelected(false);
                    explainSanCai.setSelected(false);
                    break ;
                case R.id.explainSanCai:
                    explainOverview.setSelected(false);
                    explainZodiac.setSelected(false);
                    explainDestiny.setSelected(false);
            }
        }
    }
}
