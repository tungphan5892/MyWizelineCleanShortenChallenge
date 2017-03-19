package com.example.tungphan.wizelinecleanshortenchallenge.viewmodels;


import android.content.Context;

import com.example.tungphan.wizelinecleanshortenchallenge.databinding.SearchActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.eventbus.SearchEdittextDoneEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.iviewlistener.ISearchActivityListener;


/**
 * Created by tungphan on 3/9/17.
 */

public class SearchActivityViewModel implements ISearchActivityListener {

    private Context context;
    private SearchActivityBinding searchActivityBinding;

    public SearchActivityViewModel(Context context, SearchActivityBinding searchActivityBinding) {
        this.context = context;
        this.searchActivityBinding = searchActivityBinding;
    }

    public ISearchActivityListener getISearchTweetViewModel() {
        return this;
    }

    @Override
    public void searchEditTextDone(SearchEdittextDoneEvent searchEdittextDoneEvent) {

    }
}
