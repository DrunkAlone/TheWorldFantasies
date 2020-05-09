package com.example.worldtest.ui.dashboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worldtest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Moment> list;
    private String [] imageurl;
    private String image;
    GrideViewAdapter adapter;
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ic_launcher)                      //设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.ic_launcher)               //设置图片uri为空或者是错位的时候显示的图片
            .showImageOnFail(R.mipmap.ic_launcher)                    //设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                                          //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                            //设置下载的图片是否缓存在SD中
            .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
            .build();                                                     //创建配置或的DisplayImageOption对象

    ImageLoader imageLoader = ImageLoader.getInstance();

    public ListViewAdapter(Context context, List<Moment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.time = (TextView)convertView.findViewById(R.id.time);
            viewHolder.gridview = (MyGridView) convertView.findViewById(R.id.gridview);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Moment moment = list.get(position);
        viewHolder.name.setText(moment.getUser_name());//用户名
        viewHolder.text.setText(moment.getContent());//发表内容
        viewHolder.time.setText(moment.getCreatedAt());//发表时间
        /*
        //显示发表消息的用户的头像
        BmobQuery<advertisement> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("name", moment.getUser_name());//根据用户名查找对应的图片头像
        categoryBmobQuery.findObjects(new FindListener<advertisement>() {
            @Override
            public void done(List<advertisement> object, BmobException e) {
                if (e == null) {
                    String touxiang = object.get(0).getPicture().getFileUrl();
                    Log.w("BMOB",touxiang);

                    imageLoader.displayImage(touxiang, viewHolder.image, options);

                } else {
                    Log.e("BMOB", e.toString());

                }
            }
        });
*/
        if (moment.getPicture() == null) { // 没有图片资源就隐藏GridView
            viewHolder.gridview.setVisibility(View.GONE);
        } else {
            image = moment.getPicture();
            imageurl = image.split(";");
            adapter = new GrideViewAdapter(context,imageurl);
            viewHolder.gridview.setAdapter(adapter);
            viewHolder.gridview.setTag(position);
            System.out.println("进入grid view");
        }


        return convertView;
    }


    public  class ViewHolder {
        ImageView image;
        TextView name;
        TextView text;
        TextView time;
        MyGridView gridview;
    }

}
