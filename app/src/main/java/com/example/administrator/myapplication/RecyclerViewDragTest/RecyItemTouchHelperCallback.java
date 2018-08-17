package com.example.administrator.myapplication.RecyclerViewDragTest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.administrator.myapplication.MainRecyclerViewAdapter;

import java.util.Collections;

public class RecyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    RecyclerView.Adapter mAdapter;
    boolean isSwipeEnable;

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        isSwipeEnable = true;
    }

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter, boolean isSwipeEnable) {
        mAdapter = adapter;
        this.isSwipeEnable = isSwipeEnable;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(((MainRecyclerViewAdapter) mAdapter).getItemViews(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(((MainRecyclerViewAdapter) mAdapter).getItemViews(), i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        mAdapter.notifyItemRemoved(adapterPosition);
        ((MainRecyclerViewAdapter) mAdapter).getItemViews().remove(adapterPosition);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            try {
                setDraging(true);
                animateWobble(viewHolder.itemView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }


    private ObjectAnimator animator;
    private void animateWobble(View v) {
        animator = createBaseWobble(v);
        animator.setFloatValues(-2, 2);
        animator.start();
    }

    private ObjectAnimator createBaseWobble(final View v) {
        ObjectAnimator animator = new ObjectAnimator();
        animator.setDuration(180);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setPropertyName("rotation");
        animator.setTarget(v);
        return animator;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        try{
            setDraging(false);
            if(mListener != null){
                mListener.onDragStop();
            }
            if(animator != null){
                animator.end();
                animator.cancel();
                viewHolder.itemView.setRotation(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }

    private OnDevItemDropListener mListener;
    public void setOnDevItemDropListener(OnDevItemDropListener listener){
        this.mListener = listener;
    }
    public interface OnDevItemDropListener{
        void onDragStop();
    }
    private boolean isDraging = false;

    public boolean isDraging() {
        return isDraging;
    }

    public void setDraging(boolean draging) {
        isDraging = draging;
    }
}
