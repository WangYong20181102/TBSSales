package com.tbs.sales.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tbs.sales.R;
import com.tbs.sales.bean.ComFollowRecordingBean;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Wang on 2019/3/28 18:00.
 */
public class FollowUpRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<ComFollowRecordingBean> beanList;
    private OnPlayVideoListener onPlayVideo = null;

    public void setOnPlayVideoListener(OnPlayVideoListener onPlayVideoListener) {
        onPlayVideo = onPlayVideoListener;
    }


    public static interface OnPlayVideoListener {
        void onResult(MediaPlayer mediaPlayer, MyViewHolder holder, int position);

        void onMoveResult(boolean b, SeekBar seekBar, int position);
    }

    public FollowUpRecordAdapter(Context context, List<ComFollowRecordingBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.follow_up_record_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.imagePlay.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            if (position == 0) {//顶部空白区域
                ((MyViewHolder) holder).viewTopBg.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).imageSelect.setImageResource(R.mipmap.select);
            } else {
                ((MyViewHolder) holder).viewTopBg.setVisibility(View.GONE);
                ((MyViewHolder) holder).imageSelect.setImageResource(R.mipmap.un_select);
            }
            if (position == beanList.size() - 1) {  //隐藏最后一条线
                ((MyViewHolder) holder).viewLeftLine.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).viewLeftLine.setVisibility(View.VISIBLE);
            }
            //姓名
            ((MyViewHolder) holder).tvName.setText(beanList.get(position).getFollow_name());
            //日期
            ((MyViewHolder) holder).tvData.setText(beanList.get(position).getCreate_time());
            //标签
            List<String> strings = beanList.get(position).getTag_list();
            if (strings.isEmpty()) {
                ((MyViewHolder) holder).linearBiaoQian.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).linearBiaoQian.setVisibility(View.VISIBLE);
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < strings.size(); i++) {
                    builder.append(strings.get(i) + "    ");
                }
                ((MyViewHolder) holder).tvBiaoqian.setText(builder.toString());
            }
            //跟进内容
            ((MyViewHolder) holder).tvDesc.setText(beanList.get(position).getDesc());
            ((MyViewHolder) holder).tvChangeDesc.setText(beanList.get(position).getChange_desc());
            //建议沟通方式
            if (TextUtils.isEmpty(beanList.get(position).getCommunic_desc())) {
                ((MyViewHolder) holder).linearSuggestType.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).linearSuggestType.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).tvCommunicDesc.setText(beanList.get(position).getCommunic_desc());
            }

            //语音
            if (TextUtils.isEmpty(beanList.get(position).getVoice_url())) {
                ((MyViewHolder) holder).rlVideoVoice.setVisibility(View.GONE);
            } else {
                ((MyViewHolder) holder).rlVideoVoice.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).imagePlay.setTag(R.id.tag_three, position);
                initMediaPlayer(beanList.get(position).getVoice_url(), (MyViewHolder) holder, position);
            }

        }
    }

    /**
     * 初始化
     *
     * @param voice_url
     * @param holder
     * @param position
     */
    private void initMediaPlayer(String voice_url, final MyViewHolder holder, int position) {
        final SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        MediaPlayer mediaPlayer = new MediaPlayer();
        holder.seekBar.setOnSeekBarChangeListener(new MySeekBar(position));
        holder.imagePlay.setTag(R.id.tag_first, mediaPlayer);
        holder.imagePlay.setTag(R.id.tag_second, holder);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(voice_url);//指定音频文件的路径
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.seekTo(0);
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    holder.seekBar.setMax(mp.getDuration());
                    holder.tvTotalTime.setText(format.format(mp.getDuration()) + "");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_top_bg)
        View viewTopBg;
        @BindView(R.id.image_select)
        ImageView imageSelect;
        @BindView(R.id.view_left_line)
        View viewLeftLine;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_biaoqian)
        TextView tvBiaoqian;
        @BindView(R.id.linear_biaoqian)
        LinearLayout linearBiaoQian;
        @BindView(R.id.linear_suggest_type)
        LinearLayout linearSuggestType;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_change_desc)
        TextView tvChangeDesc;
        @BindView(R.id.tv_communic_desc)
        TextView tvCommunicDesc;
        @BindView(R.id.image_play)
        ImageView imagePlay;
        @BindView(R.id.seekBar)
        public SeekBar seekBar;
        @BindView(R.id.tv_current_time)
        public TextView tvCurrentTime;
        @BindView(R.id.tv_total_time)
        TextView tvTotalTime;
        @BindView(R.id.rl_video_voice)
        RelativeLayout rlVideoVoice;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_play:
                if (onPlayVideo != null) {
                    onPlayVideo.onResult((MediaPlayer) v.getTag(R.id.tag_first), (MyViewHolder) v.getTag(R.id.tag_second), (Integer) v.getTag(R.id.tag_three));
                }
                break;
        }
    }

    /*进度条处理*/
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        private int position;

        public MySeekBar(int position) {
            this.position = position;
        }

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (onPlayVideo != null) {
                onPlayVideo.onMoveResult(true, null, position);
            }
        }

        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (onPlayVideo != null) {
                onPlayVideo.onMoveResult(false, seekBar, position);
            }
        }
    }
}
