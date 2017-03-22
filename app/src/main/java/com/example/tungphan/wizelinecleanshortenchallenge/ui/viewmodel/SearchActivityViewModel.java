package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;


import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tungphan.wizelinecleanshortenchallenge.BR;
import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SearchActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.SearchTweet;
import com.example.tungphan.wizelinecleanshortenchallenge.model.StartSearchTweetEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.network.Service;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.adapters.SearchTweetRecyclerAdapter;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.ISearchActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.SearchActivityModel;


import rx.Subscriber;
import rx.Subscription;


/**
 * Created by tungphan on 3/9/17.
 */

public class SearchActivityViewModel extends BaseObservable implements ISearchActivityListener {

    private Context context;
    private SearchActivityBinding searchActivityBinding;
    private SearchTweetRecyclerAdapter searchTweetRecyclerAdapter;
    private Service service;
    private final SearchActivityModel searchActivityModel = new SearchActivityModel();
    private Subscription subscription;

    public SearchActivityViewModel(Context context, SearchActivityBinding searchActivityBinding, Service service) {
        this.context = context;
        this.searchActivityBinding = searchActivityBinding;
        this.service = service;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        searchActivityBinding.searchTweetRecyclerView.setLayoutManager(mLayoutManager);
    }

    public ISearchActivityListener getISearchTweetViewModel() {
        return this;
    }

    @Override
    public void searchEditTextDone(StartSearchTweetEvent startSearchTweetEvent) {
        searchTweet(startSearchTweetEvent.getSearchQuery());
    }

    @Override
    public void onDestroy() {
        subscription.unsubscribe();
    }

    private void finishLoadingSearchTweet(SearchTweet searchTweet) {
        if (searchTweetRecyclerAdapter == null) {//init tweet recycler view adapter for the first time
            setVisibleEmptyBackground(false);
            searchTweetRecyclerAdapter = new SearchTweetRecyclerAdapter(context, searchTweet);
            searchActivityBinding.searchTweetRecyclerView.setAdapter(searchTweetRecyclerAdapter);
        } else {
            setVisibleProgressBar(false);
            searchTweetRecyclerAdapter.setSearchTweet(searchTweet);
            searchTweetRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void searchTweet(String query) {
        visibleLoading();
        subscription = service.searchTweet("\"" + query + "\"")
                .subscribe(new Subscriber<SearchTweet>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        errorSearchTweet();

                    }

                    @Override
                    public void onNext(SearchTweet searchTweet) {
                        finishLoadingSearchTweet(searchTweet);
                    }
                });
    }

    private void visibleLoading(){
        if (searchTweetRecyclerAdapter == null) {
            setVisibleEmptyBackground(true);
        } else if (searchTweetRecyclerAdapter.getItemCount() == 0) {
            setVisibleProgressBar(true);
        }
    }

    private void errorSearchTweet() {
        Snackbar.make(searchActivityBinding.parentView, R.string.error_search_in_response
                , Snackbar.LENGTH_LONG).show();
    }

    public void setVisibleEmptyBackground(boolean visibleEmptyBackground) {
        searchActivityModel.setVisibleEmptyBackground(visibleEmptyBackground);
        notifyPropertyChanged(BR.visibleEmptyBackground);
    }

    @Bindable
    public boolean isVisibleEmptyBackground() {
        return searchActivityModel.isVisibleEmptyBackground();
    }

    public void setVisibleProgressBar(boolean visibleProgressBar) {
        searchActivityModel.setVisibleProgressBar(visibleProgressBar);
        notifyPropertyChanged(BR.visibleProgressBar);
    }

    @Bindable
    public boolean isVisibleProgressBar() {
        return searchActivityModel.isVisibleProgressBar();
    }
}
