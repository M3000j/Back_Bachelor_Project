package com.example.m3000j.backgammon;

import android.content.ClipData;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import static com.example.m3000j.backgammon.GameActivity.primeIndex;

public class TouchMove implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (v.getParent() instanceof FrameLayout) {
                    if (v.getParent().getParent() instanceof GridLayout) {
                        GridLayout gridLayout = (GridLayout) v.getParent().getParent();
                        primeIndex = gridLayout.indexOfChild((FrameLayout) v.getParent());
                        final ClipData data = ClipData.newPlainText("", "");
                        FrameLayout vparent = (FrameLayout) v.getParent();
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadowBuilder, v, 0);
                        int count = Integer.valueOf(((TextView) vparent.getChildAt(1)).getText().toString());
                        count = count - 1;
                        if (count < 0) {
                            vparent.setVisibility(View.INVISIBLE);
                            count = 0;
                        } else if (count == 0) {
                            vparent.getChildAt(1).setVisibility(View.INVISIBLE);
                        }
                        ((TextView) vparent.getChildAt(1)).setText(String.valueOf(count));
                    } else {
                        final ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                        v.startDrag(data, shadowBuilder, v, 0);
                    }
                } else {
                    GridLayout gr = (GridLayout) v.getParent();
                    primeIndex = gr.indexOfChild(v);
                    Log.d("PI", "onTouch: " + primeIndex);
                    final ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return true;
    }
}
