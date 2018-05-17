package wcy.godinsec.wcy_dandan.test.activitys;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import wcy.godinsec.wcy_dandan.R;
import wcy.godinsec.wcy_dandan.appbase.BaseActivity;
import wcy.godinsec.wcy_dandan.test.aimator.CharEvaluator;
import wcy.godinsec.wcy_dandan.utils.LogUtils;

/**
 * Auther：杨玉安 on 2018/5/14 11:57
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class AnimationActivity extends BaseActivity {
    @BindView(R.id.tween_scale)
    Button mTweenScale;
    @BindView(R.id.tween_translate)
    Button mTweenTranslate;
    @BindView(R.id.tween_rotate)
    Button mTweenRotate;
    @BindView(R.id.tween_set)
    Button mTweenSet;
    @BindView(R.id.propert_scale)
    Button mPropertScale;
    @BindView(R.id.propert_translate)
    Button mPropertTranslate;
    @BindView(R.id.propert_rotate)
    Button mPropertRotate;
    @BindView(R.id.propert_set)
    Button mPropertSet;
    @BindView(R.id.image_animation)
    ImageView mImageAnimation;
    @BindView(R.id.tv_color)
    TextView mTvColor;
    @BindView(R.id.menu)
    Button mMenu;
    @BindView(R.id.item1)
    Button mItem1;
    @BindView(R.id.item2)
    Button mItem2;
    @BindView(R.id.item3)
    Button mItem3;
    @BindView(R.id.item4)
    Button mItem4;
    @BindView(R.id.item5)
    Button mItem5;

    //补间动画
    private Animation scaleAnimation;
    private Animation translateAnimation;
    private Animation rotateAnimation;
    private Animation setAnimation;
    private boolean mIsMenuOpen;


    //属性动画
    private ScaleAnimation mScaleAnimation;
    private TranslateAnimation mTranslateAnimation;
    private RotateAnimation mRotateAnimation;

    private ValueAnimator mValueAnimator;

    @Override
    protected int setContentlayout() {
        return R.layout.activity_animation_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //values动画的两种监听方法
//        valuesAnimatorListener();

        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_translationy);
        animator.setTarget(mImageAnimation);
        animator.start();
//
//        //颜色Evaluator
//        colorEvaluator();
//
//        //字符Evaluator
//        charEvaluator();

        //利用Keyframe做出闹铃效果
//        keyFrameMethod();
    }


    @OnClick({R.id.menu, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5,R.id.tween_scale, R.id.tween_translate, R.id.tween_rotate, R.id.tween_set, R.id.propert_scale, R.id.propert_translate, R.id.propert_rotate, R.id.propert_set, R.id.image_animation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tween_scale:
                mScaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mScaleAnimation.setDuration(7000);
                mImageAnimation.startAnimation(mScaleAnimation);

//                scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.animation_tween_scale);
//                mImageAnimation.startAnimation(scaleAnimation);
                break;
            case R.id.tween_translate:
                mTranslateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                mTranslateAnimation.setDuration(7000);
                mImageAnimation.startAnimation(mTranslateAnimation);

//                translateAnimation = AnimationUtils.loadAnimation(this,R.anim.animation_tween_translate);
//                mImageAnimation.startAnimation(translateAnimation);
                break;
            case R.id.tween_rotate:
                mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                mRotateAnimation.setDuration(2000);
                mImageAnimation.startAnimation(mRotateAnimation);

//                rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_tween_rotate);
//                mImageAnimation.startAnimation(rotateAnimation);
                break;
            case R.id.tween_set:
                setAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_tween_set);
                mImageAnimation.startAnimation(setAnimation);
                break;
            case R.id.propert_scale:
                ObjectAnimator animator = ObjectAnimator.ofFloat(mImageAnimation, "scaleX", 1.5f, 2, 3, 1.5f, 3.5f, 1.5f, 2, 1.8f);
                animator.setDuration(50000);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.start();

//                ObjectAnimator animator = ObjectAnimator.ofFloat(mImageAnimation,"scaleX",1,3,1);
//                animator.setDuration(2000);
//                animator.start();
                break;
            case R.id.propert_translate:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageAnimation, "translationX", 0, 200, -100, 100, 200, -100, -300, 0, 200, 50, -50);
                animator1.setDuration(50000);
                animator1.setRepeatMode(ValueAnimator.REVERSE);
                animator1.setRepeatCount(ValueAnimator.INFINITE);
                animator1.start();
                break;
            case R.id.propert_rotate:
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageAnimation, "rotationX", 0, 360, 0);
                animator2.setDuration(2000);
                animator2.start();
                break;
            case R.id.propert_set:
                //属性动画组合动画
                animatorSetMethod();


                break;
            case R.id.image_animation:
                break;
            case R.id.menu:
                LogUtils.e("mIsMenuOpen===="+mIsMenuOpen);
                if (!mIsMenuOpen) {
                    mIsMenuOpen = true;
                    doAnimateOpen(mItem1, 0, 5, 500);
                    doAnimateOpen(mItem2, 1, 5, 500);
                    doAnimateOpen(mItem3, 2, 5, 500);
                    doAnimateOpen(mItem4, 3, 5, 500);
                    doAnimateOpen(mItem5, 4, 5, 500);
                } else {
                    mIsMenuOpen = false;
                    doAnimateClose(mItem1, 0, 5, 500);
                    doAnimateClose(mItem2, 1, 5, 500);
                    doAnimateClose(mItem3, 2, 5, 500);
                    doAnimateClose(mItem4, 3, 5, 500);
                    doAnimateClose(mItem5, 4, 5, 500);
                }
                break;
            case R.id.item1:
                break;
            case R.id.item2:
                break;
            case R.id.item3:
                break;
            case R.id.item4:
                break;
            case R.id.item5:
                break;
        }
    }

    private void doAnimateClose(final View view, int index, int total,
                                int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        set.setDuration(1 * 500).start();
    }

    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1 * 500).start();
    }

    private void animatorSetMethod() {
        ObjectAnimator tvBackGroundColor = ObjectAnimator.ofInt(mTvColor, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        tvBackGroundColor.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator tvTranslate1 = ObjectAnimator.ofFloat(mTvColor, "translationY", 0, 500, 0);
        tvTranslate1.setStartDelay(2000);
        ObjectAnimator tvTranslate2 = ObjectAnimator.ofFloat(mTvColor, "translationX", 0, 500, 0);
        tvTranslate2.setStartDelay(2000);
        AnimatorSet set = new AnimatorSet();
//        set.playSequentially(tvBackGroundColor,tvTranslate1,tvTranslate2);
//        set.playTogether(tvBackGroundColor,tvTranslate1,tvTranslate2);

        AnimatorSet.Builder play = set.play(tvBackGroundColor);
        play.with(tvTranslate1);
//        play.with(tvTranslate2);
        set.setDuration(2000);
        set.start();
    }


    private void charEvaluator() {
        ValueAnimator char_animator = ValueAnimator.ofObject(new CharEvaluator(), new Character('A'), new Character('Z'));
        char_animator.setDuration(3000);
        char_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char curValue = (char) animation.getAnimatedValue();
                LogUtils.e("color = = " + curValue);
                mTvColor.setText(String.valueOf(curValue));
            }
        });
        char_animator.start();
    }

    private void colorEvaluator() {
        ValueAnimator color_animator = ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
        color_animator.setEvaluator(new ArgbEvaluator());
        color_animator.setDuration(3000);
        color_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                LogUtils.e("color = = " + curValue);
                mTvColor.setBackgroundColor(curValue);
            }
        });
        color_animator.start();
    }

    private void keyFrameMethod() {
        /**
         * scaleX放大1.1倍
         */
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 2f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 2f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 2f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 2f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 2f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 2f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 2f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 2f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 2f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofKeyframe("ScaleX", scaleXframe0, scaleXframe1, scaleXframe2, scaleXframe3,
                scaleXframe4, scaleXframe5, scaleXframe6, scaleXframe7, scaleXframe8, scaleXframe9, scaleXframe10);


/**
 * scaleY放大1.1倍
 */
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 2f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 2f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 2f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 2f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 2f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 2f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 2f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 2f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 2f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofKeyframe("ScaleY", scaleYframe0, scaleYframe1, scaleYframe2, scaleYframe3,
                scaleYframe4, scaleYframe5, scaleYframe6, scaleYframe7, scaleYframe8, scaleYframe9, scaleYframe10);


        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -180f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 180f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -180f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 180f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -180f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 180f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -180f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 180f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -180f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mImageAnimation, holder, holder1, holder2);
//        animator.setRepeatMode(ValueAnimator.REVERSE); //设置循环模式
//        animator.setRepeatCount(ValueAnimator.INFINITE); //设置循环次数
        animator.setDuration(50000);
        animator.start();
    }

    private void valuesAnimatorListener() {
//        mValueAnimator = ValueAnimator.ofInt(0, 1000);
//        mValueAnimator.setDuration(1000);
//        mValueAnimator.setInterpolator(new LinearInterpolator());
//        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE); //设置循环模式
//        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE); //设置循环次数
//        mValueAnimator.start();
        mValueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_value);
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                mImageAnimation.layout(mImageAnimation.getLeft(), animatedValue, mImageAnimation.getRight(), animatedValue + mImageAnimation.getHeight());
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LogUtils.e("===onAnimationStart===");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.e("===onAnimationEnd===");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                LogUtils.e("===onAnimationCancel===");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                LogUtils.e("===onAnimationRepeat===");
            }
        });
    }

}
