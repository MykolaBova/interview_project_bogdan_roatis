package roatis.bogdan.places.view.custom;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import roatis.bogdan.places.R;

/**
 * Created by Bogdan Roatis on 7/3/17.
 */

public class PlaceItem extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private ImageView ivPlace;
    private ImageView ivPlaceChecked;

    private TextView tvTitle;

    private OnPlaceClickListener onPlaceClickListener;
    private OnPlaceLongClickListener onPlaceLongClickListener;

    public PlaceItem(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        ivPlace = (ImageView) itemView.findViewById(R.id.iv_place);
        ivPlaceChecked = (ImageView) itemView.findViewById(R.id.iv_place_checked);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setOnPlaceClickListener(OnPlaceClickListener onPlaceClickListener) {
        this.onPlaceClickListener = onPlaceClickListener;
    }

    public void setOnPlaceLongClickListener(OnPlaceLongClickListener onPlaceLongClickListener) {
        this.onPlaceLongClickListener = onPlaceLongClickListener;
    }

    public void setStatus(boolean isChecked) {
        ivPlace.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        ivPlaceChecked.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        if (!isChecked) {
            itemView.setBackgroundResource(R.color.default_place_background);
        } else {
            itemView.setBackgroundResource(R.color.selected_place_background);
        }
    }

    @Override
    public void onClick(View v) {
        if (onPlaceClickListener != null) {
            onPlaceClickListener.onPlaceClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onPlaceLongClickListener != null) {
            onPlaceLongClickListener.onPlaceLongClick(v, getAdapterPosition());
            return true;
        }
        return false;
    }

    public void setIcon(String url) {
        Picasso.with(itemView.getContext()).load(url).into(ivPlace);
    }

    public interface OnPlaceClickListener {
        void onPlaceClick(View view, int position);
    }

    public interface OnPlaceLongClickListener {
        void onPlaceLongClick(View view, int position);
    }
}
