package com.example.movingimage;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Block {

    private ImageView mImageView;
    private POSITION position;
    private boolean isUp;
    private boolean isVisible;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        if (isVisible) {
            mImageView.setVisibility(View.VISIBLE);
        } else {
            mImageView.setVisibility(View.INVISIBLE);
        }

    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public Block(ImageView imageView) {
        mImageView = imageView;
        position = POSITION.LEFT;
        isVisible = mImageView.getVisibility() == View.VISIBLE;
    }

    public int getLeftMargin() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        return params.leftMargin;
    }

    public int getBottomMargin() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        return params.bottomMargin;
    }

    public void setBottomMargin(int bottomMrgn) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        params.bottomMargin = bottomMrgn;
        mImageView.setLayoutParams(params);
    }

    public void setLeftMargin(int leftMrgn) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
        params.leftMargin = leftMrgn;
        mImageView.setLayoutParams(params);
    }

    public int getWidth() {
        return mImageView.getWidth();
    }

    public int getHeight() {
        return mImageView.getHeight();
    }

    public POSITION getPosition() {
        return position;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Block {" +
                ", name= " + mImageView.getDrawable() +
                ", position= " + position +
                ", isUp= " + isUp +
                ", bottomMargin=" + this.getBottomMargin() +
                ", visible=" + this.isVisible() +
                '}';
    }
}
