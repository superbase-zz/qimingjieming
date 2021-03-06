package com.tjyw.qmjmqd.holder;

import android.view.View;
import android.widget.TextView;

import com.tjyw.atom.network.conf.ISymbol;
import com.tjyw.atom.network.model.NameZodiac;
import com.tjyw.atom.network.utils.ArrayUtil;
import atom.pub.inject.From;
import atom.pub.inject.Injector;
import com.tjyw.qmjmqd.R;

import java.util.List;

/**
 * Created by stephen on 21/09/2017.
 */
public class BaZiSheetHolder {

    @From(R.id.baZiShiShenFirst)
    protected TextView baZiShiShenFirst;

    @From(R.id.baZiShiShenSecond)
    protected TextView baZiShiShenSecond;

    @From(R.id.baZiShiShenThird)
    protected TextView baZiShiShenThird;

    @From(R.id.baZiShiShenFourth)
    protected TextView baZiShiShenFourth;

    @From(R.id.baZiGanZhiFirst)
    protected TextView baZiGanZhiFirst;

    @From(R.id.baZiGanZhiSecond)
    protected TextView baZiGanZhiSecond;

    @From(R.id.baZiGanZhiThird)
    protected TextView baZiGanZhiThird;

    @From(R.id.baZiGanZhiFourth)
    protected TextView baZiGanZhiFourth;

    @From(R.id.baZiWuXingFirst)
    protected TextView baZiWuXingFirst;

    @From(R.id.baZiWuXingSecond)
    protected TextView baZiWuXingSecond;

    @From(R.id.baZiWuXingThird)
    protected TextView baZiWuXingThird;

    @From(R.id.baZiWuXingFourth)
    protected TextView baZiWuXingFourth;

    @From(R.id.baZiCangGanFirst)
    protected TextView baZiCangGanFirst;

    @From(R.id.baZiCangGanSecond)
    protected TextView baZiCangGanSecond;

    @From(R.id.baZiCangGanThird)
    protected TextView baZiCangGanThird;

    @From(R.id.baZiCangGanFourth)
    protected TextView baZiCangGanFourth;

    @From(R.id.baZiNaYinFirst)
    protected TextView baZiNaYinFirst;

    @From(R.id.baZiNaYinSecond)
    protected TextView baZiNaYinSecond;

    @From(R.id.baZiNaYinThird)
    protected TextView baZiNaYinThird;

    @From(R.id.baZiNaYinFourth)
    protected TextView baZiNaYinFourth;

    public BaZiSheetHolder(View view) {
        Injector.inject(this, view);
    }

    public void sheet(NameZodiac nameZodiac) {
        sheetShiShen(nameZodiac.shishen);
        sheetGanZhi(nameZodiac.ganzhi);
        sheetWuXing(nameZodiac.wuxing);
        sheetCangGan(nameZodiac.zanggan);
        sheetNaYin(nameZodiac.nayin);
    }

    protected void sheetShiShen(List<String> list) {
        int size = null == list ? 0 : list.size();
        if (size > 0) {
            baZiShiShenFirst.setText(list.get(0));
        }

        if (size > 1) {
            baZiShiShenSecond.setText(list.get(1));
        }

        if (size > 2) {
            baZiShiShenThird.setText(list.get(2));
        }

        if (size > 3) {
            baZiShiShenFourth.setText(list.get(3));
        }
    }

    protected void sheetGanZhi(List<String> list) {
        int size = null == list ? 0 : list.size();
        if (size > 0) {
            baZiGanZhiFirst.setText(list.get(0));
        }

        if (size > 1) {
            baZiGanZhiSecond.setText(list.get(1));
        }

        if (size > 2) {
            baZiGanZhiThird.setText(list.get(2));
        }

        if (size > 3) {
            baZiGanZhiFourth.setText(list.get(3));
        }
    }

    protected void sheetWuXing(List<String> list) {
        int size = null == list ? 0 : list.size();
        if (size > 0) {
            baZiWuXingFirst.setText(list.get(0));
        }

        if (size > 1) {
            baZiWuXingSecond.setText(list.get(1));
        }

        if (size > 2) {
            baZiWuXingThird.setText(list.get(2));
        }

        if (size > 3) {
            baZiWuXingFourth.setText(list.get(3));
        }
    }

    protected void sheetCangGan(List<List<String>> list) {
        int size = null == list ? 0 : list.size();
        if (size > 0) {
            List<String> content = list.get(0);
            if (! ArrayUtil.isEmpty(content)) {
                int contentSize = content.size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < contentSize; i ++) {
                    builder.append(content.get(i));
                    if (i + 1 < contentSize) {
                        builder.append(ISymbol.ENTER);
                    }
                }

                baZiCangGanFirst.setText(builder.toString());
            }
        }

        if (size > 1) {
            List<String> content = list.get(1);
            if (! ArrayUtil.isEmpty(content)) {
                int contentSize = content.size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < contentSize; i ++) {
                    builder.append(content.get(i));
                    if (i + 1 < contentSize) {
                        builder.append(ISymbol.ENTER);
                    }
                }

                baZiCangGanSecond.setText(builder.toString());
            }
        }

        if (size > 2) {
            List<String> content = list.get(2);
            if (! ArrayUtil.isEmpty(content)) {
                int contentSize = content.size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < contentSize; i ++) {
                    builder.append(content.get(i));
                    if (i + 1 < contentSize) {
                        builder.append(ISymbol.ENTER);
                    }
                }

                baZiCangGanThird.setText(builder.toString());
            }
        }

        if (size > 3) {
            List<String> content = list.get(3);
            if (! ArrayUtil.isEmpty(content)) {
                int contentSize = content.size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < contentSize; i ++) {
                    builder.append(content.get(i));
                    if (i + 1 < contentSize) {
                        builder.append(ISymbol.ENTER);
                    }
                }

                baZiCangGanFourth.setText(builder.toString());
            }
        }
    }

    protected void sheetNaYin(List<String> list) {
        int size = null == list ? 0 : list.size();
        if (size > 0) {
            baZiNaYinFirst.setText(list.get(0));
        }

        if (size > 1) {
            baZiNaYinSecond.setText(list.get(1));
        }

        if (size > 2) {
            baZiNaYinThird.setText(list.get(2));
        }

        if (size > 3) {
            baZiNaYinFourth.setText(list.get(3));
        }
    }
}
