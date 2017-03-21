package com.example.tungphan.wizelinecleanshortenchallenge.ui.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tungphan.wizelinecleanshortenchallenge.R;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityRequestCode;
import com.example.tungphan.wizelinecleanshortenchallenge.constant.ActivityResult;
import com.example.tungphan.wizelinecleanshortenchallenge.databinding.BaseActivityBinding;
import com.example.tungphan.wizelinecleanshortenchallenge.model.StartSearchTweetEvent;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.iviewlistener.IBaseActivityListener;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.model.BaseActivityModel;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.NewTweetActivity;
import com.example.tungphan.wizelinecleanshortenchallenge.ui.view.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.tungphan.wizelinecleanshortenchallenge.constant.PrefConstants.PREFS_NAME;
import static com.example.tungphan.wizelinecleanshortenchallenge.constant.PrefConstants.PREFS_SEARCH_HISTORY;

/**
 * Created by tungphan on 3/17/17.
 */

public class BaseActivityViewModel extends BaseObservable implements IBaseActivityListener {
    private final String TAG = BaseActivityViewModel.class.getSimpleName();
    private BaseActivityModel baseActivityModel = new BaseActivityModel();
    private Context context;
    private BaseActivityBinding baseActivityBinding;
    private SharedPreferences sharedPreferences;
    private Set<String> history;

    private View.OnClickListener fabClickListenter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickAddTweetButton();
        }
    };

    private View.OnClickListener closeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity) context).setResult(ActivityResult.CANCELED);
            ((Activity) context).finish();
        }
    };

    private View.OnClickListener searchBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SearchActivity.class);
            context.startActivity(intent);
        }
    };

    private TextView.OnEditorActionListener searchEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                callStartSearch(view.getText().toString());
                new Handler().post(new Runnable() {
                    public void run() {
                        baseActivityBinding.appBarBase.searchEdittext.dismissDropDown();
                    }
                });
            }
            return false;
        }
    };

    private void callStartSearch(String query) {
        addSearchInput(baseActivityBinding.appBarBase.searchEdittext.getText().toString());
        EventBus.getDefault().post(new StartSearchTweetEvent(query));
    }

    private AdapterView.OnItemClickListener searchItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String query = "";
            query = parent.getItemAtPosition(position).toString();
            callStartSearch(query);
        }
    };

    public IBaseActivityListener getIBaseActivityListener() {
        return this;
    }

    public BaseActivityViewModel(Context context, BaseActivityBinding baseActivityBinding) {
        this.context = context;
        this.baseActivityBinding = baseActivityBinding;
        baseActivityBinding.appBarBase.fab.setOnClickListener(fabClickListenter);
        baseActivityBinding.appBarBase.closeButton.setOnClickListener(closeBtnClickListener);
        baseActivityBinding.appBarBase.searchButton.setOnClickListener(searchBtnClickListener);
        baseActivityBinding.appBarBase.searchEdittext.setInputType(InputType.TYPE_CLASS_TEXT);
        baseActivityBinding.appBarBase.searchEdittext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        baseActivityBinding.appBarBase.searchEdittext.setOnEditorActionListener(searchEditorActionListener);
        baseActivityBinding.appBarBase.searchEdittext.setOnItemClickListener(searchItemClickListener);
    }


    public void clickAddTweetButton() {
        Intent intent = new Intent(context, NewTweetActivity.class);
        ((Activity) context).startActivityForResult(intent
                , ActivityRequestCode.START_NEW_TWEET_ACTIVITY_REQUEST_CODE);
    }

    private void setAutoCompleteSource() {
        String[] autoComplete = history.toArray(new String[history.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context
                , android.R.layout.simple_list_item_1, autoComplete);
        baseActivityBinding.appBarBase.searchEdittext.setAdapter(adapter);
    }

    private void addSearchInput(String input) {
        if (!history.contains(input)) {
            history.add(input);
            setAutoCompleteSource();
        }
    }

    private void savePrefs() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, history);
        editor.apply();
    }

    @Override
    public void onCreate() {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        history = sharedPreferences.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>());
        if (history.size() == 0) {
            history = new HashSet<String>(Arrays.asList(getAutoCompleteFromString()));
        }
        setAutoCompleteSource();
    }

    private String getAutoCompleteResources() {
        return context.getResources().getString(R.string.a_noun)
                + context.getResources().getString(R.string.b_noun)
                + context.getResources().getString(R.string.c_noun)
                + context.getResources().getString(R.string.d_noun)
                + context.getResources().getString(R.string.e_noun)
                + context.getResources().getString(R.string.f_noun)
                + context.getResources().getString(R.string.g_noun)
                + context.getResources().getString(R.string.h_noun)
                + context.getResources().getString(R.string.i_noun)
                + context.getResources().getString(R.string.j_noun)
                + context.getResources().getString(R.string.k_noun)
                + context.getResources().getString(R.string.l_noun)
                + context.getResources().getString(R.string.m_noun)
                + context.getResources().getString(R.string.n_noun)
                + context.getResources().getString(R.string.o_noun)
                + context.getResources().getString(R.string.p_noun)
                + context.getResources().getString(R.string.q_noun)
                + context.getResources().getString(R.string.r_noun)
                + context.getResources().getString(R.string.s_noun)
                + context.getResources().getString(R.string.t_noun)
                + context.getResources().getString(R.string.u_noun)
                + context.getResources().getString(R.string.v_noun)
                + context.getResources().getString(R.string.w_noun)
                + context.getResources().getString(R.string.x_noun)
                + context.getResources().getString(R.string.y_noun)
                + context.getResources().getString(R.string.z_noun);
    }

    private String[] getAutoCompleteFromString() {
        return getAutoCompleteResources().split(", ");
    }

    @Override
    public void onStop() {
        savePrefs();
    }
}
