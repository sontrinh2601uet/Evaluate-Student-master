package com.evaluateStudent.customLayout;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.evaluateStudent.R;
import com.evaluateStudent.structure.Action;
import com.evaluateStudent.structure.ConnectToData;
import com.evaluateStudent.structure.Standard;

import java.util.ArrayList;

public class ActionLayout extends LinearLayout implements RatingBar.OnRatingBarChangeListener {

    private LinearLayout actionView;
    private TextView typeRate;
    private RatingBar rate;

    private Action action;

    public ActionLayout(Context context) {
        super(context);
    }

    public ActionLayout(Context context, LinearLayout actionView, Action action) {
        super(context);
        this.actionView = actionView;
        this.action = action;

        init(actionView);
    }

    public View getView() {
        return actionView;
    }

    private void init(LinearLayout actionView) {
        rate = actionView.findViewById(R.id.rating_bar);
        typeRate = actionView.findViewById(R.id.type_rate);

        rate.setOnRatingBarChangeListener(this);
        ((TextView) actionView.findViewById(R.id.action_content)).setText(action.getContent());

        rate.setRating(action.getRateQuality());
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch ((int) rating) {
            case 1:
                typeRate.setText(R.string.rating_type_1);
                break;
            case 2:
                typeRate.setText(R.string.rating_type_2);
                break;
            case 3:
                typeRate.setText(R.string.rating_type_3);
                break;
            case 4:
                typeRate.setText(R.string.rating_type_4);
                break;
            case 5:
                typeRate.setText(R.string.rating_type_5);
                break;
        }

        action.setRateQuality((int) rating);
    }
}
