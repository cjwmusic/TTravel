package com.wukong.ttravel.service.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.R;
import com.wukong.ttravel.Utils.ImgUtil;
import com.wukong.ttravel.service.home.model.DestCity;
import com.wukong.ttravel.service.home.model.Tailor;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by wukong on 2/18/16.
 */
public class TailorsAdapter extends BaseAdapter{

    public static int HOLDER_TYPE_CITY = 1;
    public static int HOLDER_TYPE_TAILOR = 2;

    private ArrayList mData;
    private LayoutInflater mInflater;
    private Context mContext;

    public TailorsAdapter(Context context, ArrayList<Tailor> data) {

        mContext = context;
        mData = data;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData(ArrayList data) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Object item = mData.get(position);

        if (item.getClass().equals(Tailor.class)) { //是Tailor

            convertView = getTailorView(convertView, parent);
            HashMap tag = (HashMap)convertView.getTag();
            ViewHolder holder = (ViewHolder)tag.get("holder");

            Tailor tailor = (Tailor) item;
            holder.albumView.setImageURI(ImgUtil.getCDNUrlWithPathStr(tailor.getMembAlbum()));
            holder.prodName.setText(tailor.getProdName());
            holder.nickName.setText(tailor.getMembNickName());
            holder.order.setText("成交量" + tailor.getMembOrder());
            holder.avatar.setImageURI(ImgUtil.getCDNUrlWithPathStr(tailor.getMembPhoto()));
            holder.price.setText("￥" + tailor.getProdMinPrice() + "~" + tailor.getProdMaxPrice());

            setStars(holder, tailor.getMembScore());

        }else { //是City

            convertView = getCityView(convertView, parent);
            HashMap tag = (HashMap)convertView.getTag();
            CityViewHolder cityViewHolder = (CityViewHolder)tag.get("holder");

            DestCity destCity = (DestCity) item;
            cityViewHolder.albumView.setImageURI(ImgUtil.getCDNUrlWithPathStr(destCity.getDestCityPicture()));
            cityViewHolder.cityName.setText(destCity.getDestCityName());
            //设置搜索栏是否显示
            if (destCity.isFirst()) {
                cityViewHolder.searchBar.setVisibility(View.VISIBLE);
                //绑定点击监听事件
                cityViewHolder.searchBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到城市列表页面
                        Router.sharedRouter().open("cityList");
                    }
                });

            } else {
                cityViewHolder.searchBar.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    class ViewHolder {

        TextView prodName;
        TextView nickName;
        TextView order;
        SimpleDraweeView albumView;
        SimpleDraweeView avatar;

        TextView price;

        ImageView star0;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
    }

    class CityViewHolder {
        SimpleDraweeView albumView;
        TextView cityName;
        RelativeLayout searchBar;
    }

    private void setStars(ViewHolder holder,Integer score) {
        if (score == 0) {
            holder.star0.setImageResource(R.drawable.home_star_inactive);
            holder.star1.setImageResource(R.drawable.home_star_inactive);
            holder.star2.setImageResource(R.drawable.home_star_inactive);
            holder.star3.setImageResource(R.drawable.home_star_inactive);
            holder.star4.setImageResource(R.drawable.home_star_inactive);
        }else if (score == 1) {
            holder.star0.setImageResource(R.drawable.home_star_active);
            holder.star1.setImageResource(R.drawable.home_star_inactive);
            holder.star2.setImageResource(R.drawable.home_star_inactive);
            holder.star3.setImageResource(R.drawable.home_star_inactive);
            holder.star4.setImageResource(R.drawable.home_star_inactive);
        }else if (score == 2) {
            holder.star0.setImageResource(R.drawable.home_star_active);
            holder.star1.setImageResource(R.drawable.home_star_active);
            holder.star2.setImageResource(R.drawable.home_star_inactive);
            holder.star3.setImageResource(R.drawable.home_star_inactive);
            holder.star4.setImageResource(R.drawable.home_star_inactive);
        }else if (score == 3) {
            holder.star0.setImageResource(R.drawable.home_star_active);
            holder.star1.setImageResource(R.drawable.home_star_active);
            holder.star2.setImageResource(R.drawable.home_star_active);
            holder.star3.setImageResource(R.drawable.home_star_inactive);
            holder.star4.setImageResource(R.drawable.home_star_inactive);
        }else if (score == 4) {
            holder.star0.setImageResource(R.drawable.home_star_active);
            holder.star1.setImageResource(R.drawable.home_star_active);
            holder.star2.setImageResource(R.drawable.home_star_active);
            holder.star3.setImageResource(R.drawable.home_star_active);
            holder.star4.setImageResource(R.drawable.home_star_inactive);
        }else if (score == 5) {
            holder.star0.setImageResource(R.drawable.home_star_active);
            holder.star1.setImageResource(R.drawable.home_star_active);
            holder.star2.setImageResource(R.drawable.home_star_active);
            holder.star3.setImageResource(R.drawable.home_star_active);
            holder.star4.setImageResource(R.drawable.home_star_active);
        }

    }

    private View getCityView(View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.home_item_city, parent, false);
            CityViewHolder cityViewHolder = new CityViewHolder();
            cityViewHolder.albumView = (SimpleDraweeView) convertView.findViewById(R.id.album_image);
            cityViewHolder.cityName = (TextView) convertView.findViewById(R.id.city_name);
            cityViewHolder.searchBar = (RelativeLayout) convertView.findViewById(R.id.search_bar);

            HashMap tag = new HashMap();
            tag.put("type",HOLDER_TYPE_CITY);
            tag.put("holder",cityViewHolder);
            convertView.setTag(tag);

        } else {
            HashMap tag = (HashMap)convertView.getTag();
            if ((int)tag.get("type") != HOLDER_TYPE_CITY) {
                return getCityView(null,parent);
            }
        }

        return convertView;
    }

    private View getTailorView(View convertView, ViewGroup parent) {
        if (convertView == null) {
            //create
            convertView = mInflater.inflate(R.layout.home_item_tailor, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.albumView = (SimpleDraweeView) convertView.findViewById(R.id.album_image);
            holder.prodName = (TextView) convertView.findViewById(R.id.prod_name);
            holder.nickName = (TextView) convertView.findViewById(R.id.nick_name);
            holder.order = (TextView) convertView.findViewById(R.id.order);
            holder.avatar = (SimpleDraweeView) convertView.findViewById(R.id.image_avatar);
            holder.price = (TextView) convertView.findViewById(R.id.price);

            holder.star0 = (ImageView) convertView.findViewById(R.id.start0);
            holder.star1 = (ImageView) convertView.findViewById(R.id.start1);
            holder.star2 = (ImageView) convertView.findViewById(R.id.start2);
            holder.star3 = (ImageView) convertView.findViewById(R.id.start3);
            holder.star4 = (ImageView) convertView.findViewById(R.id.start4);

            HashMap tag = new HashMap();
            tag.put("type", HOLDER_TYPE_TAILOR);
            tag.put("holder", holder);
            convertView.setTag(tag);

        } else {
            HashMap tag = (HashMap)convertView.getTag();
            if ((int)tag.get("type") != HOLDER_TYPE_TAILOR) {
                return getTailorView(null,parent);
            }
        }

        return convertView;
    }

}
