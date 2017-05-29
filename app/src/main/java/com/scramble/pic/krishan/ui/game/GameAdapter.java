package com.scramble.pic.krishan.ui.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scramble.krishan.picscramble.R;
import com.scramble.pic.krishan.utils.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krishan on 23/05/17.
 */

public class GameAdapter extends
        RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<String> mItemList;
    private Context mContext;
    private AdapterEventListener adapterEventListener;
    private boolean showPlaceHolder;

    GameAdapter(List<String> itemList,
                AdapterEventListener adapterEventListener) {
        mItemList = itemList;
        showPlaceHolder = false;
        this.adapterEventListener = adapterEventListener;
    }

    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameAdapter.ViewHolder holder, int position) {
        if (!showPlaceHolder) {
            String imageUrl = mItemList.get(position);
            Picasso.with(mContext)
                    .load(imageUrl)
                    .transform(new CircleTransform())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            adapterEventListener.imageLoaded();
                        }

                        @Override
                        public void onError() {
                        }
                    });
        }
        else {
            holder.imageView.setImageResource(R.drawable.ic_camera_iris);
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    void notifyChange(List<String> items) {
        mItemList = items;
        notifyDataSetChanged();
    }

    void setShowPlaceHolder(boolean isDisplayPlaceHolder) {
        this.showPlaceHolder = isDisplayPlaceHolder;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView (R.id.itemImage)
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick (R.id.itemImage)
        void onItemClick(View view) {
            if (view.getTag() == null) {
                return;
            }

            Integer clickedPos = (Integer) view.getTag();
            adapterEventListener.onItemClicked(clickedPos);
        }
    }

    public interface AdapterEventListener {
        void onItemClicked(int pos);

        void imageLoaded();
    }
}
