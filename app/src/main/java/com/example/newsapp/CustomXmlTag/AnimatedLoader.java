package com.example.newsapp.CustomXmlTag;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.newsapp.R;

public class AnimatedLoader extends androidx.appcompat.widget.AppCompatImageView {
    public AnimatedLoader(Context context) {
        super(context);
        init();
    }

    public AnimatedLoader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackgroundResource(R.drawable.loading_animation);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
    }
}