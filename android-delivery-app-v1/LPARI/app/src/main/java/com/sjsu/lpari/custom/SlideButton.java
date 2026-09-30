package com.sjsu.lpari.custom;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.sjsu.lpari.listeners.SlideButtonListener;

public class SlideButton extends SeekBar implements SlideButtonListener{

    private Drawable thumb;
    private SlideButtonListener listener;


    private boolean slideResetCheck = false;

    public SlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        this.thumb = thumb;
        //this.thumb.setAlpha(50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (thumb.getBounds().contains((int) event.getX(), (int) event.getY())) {
                super.onTouchEvent(event);
            } else {
                return false;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if(getProgress() < 10) {
                //highlightSlider();
                // Toast.makeText(getContext(), "Less than 10% ...", Toast.LENGTH_SHORT).show();
                //thumb.setAlpha(50);
                slideResetCheck = true;
            }
            if (getProgress() > 75) {
                handleSlide();
                //  thumb.setAlpha(255);
                slideResetCheck =false;
            }

            if(!slideResetCheck)
                setProgress(100);
            else
                setProgress(2);
            // thumb.setAlpha(50);
        } else {
            super.onTouchEvent(event);
            // Toast.makeText(getContext(), "Button slided 3...", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public void handleSlide() {
        listener.handleSlide();
    }



    public void setSlideButtonListener(SlideButtonListener listener) {
        this.listener = listener;
    }
}


