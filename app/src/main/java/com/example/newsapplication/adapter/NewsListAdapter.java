package com.example.newsapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.newsapplication.R;
import com.example.newsapplication.response.Article;
import com.example.newsapplication.utils.Utils;

import java.util.List;

import javax.sql.DataSource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{

    private List<Article> articles;
    private Context context;
    private OnNewsClickListener onNewsClickListener;

    public NewsListAdapter(List<Article> articles, Context context, OnNewsClickListener onNewsClickListener) {
        this.articles = articles;
        this.context = context;
        this.onNewsClickListener = onNewsClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(view, onNewsClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holders, int position) {
        Article article = articles.get(position);
        holders.bindTo(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnNewsClickListener {
        void onNewsClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,  published_ad, source;
        ImageView imageView;
        ProgressBar progressBar;

        public ViewHolder(View itemView, final OnNewsClickListener onNewsClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNewsClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        onNewsClickListener.onNewsClick(getAdapterPosition());
                    }
                }
            });
            title = itemView.findViewById(R.id.title);
            published_ad = itemView.findViewById(R.id.publishedAt1);
            source = itemView.findViewById(R.id.source1);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.prograss_load_photo);
        }

        public void bindTo(Article article) {

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(new ColorDrawable(Color.parseColor("#f9f9fa")))
                    .error(new ColorDrawable(Color.parseColor("#f9f9fa")))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(context)
                    .load(article.getUrlToImage())
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);

            title.setText(article.getTitle());
            source.setText(article.getSource().getName());
            published_ad.setText(Utils.dateFormat(article.getPublishedAt()));
        }

    }
}
