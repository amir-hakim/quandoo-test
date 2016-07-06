package com.test.quandoo.view.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.quandoo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RuleRowView class, is responsible to draw a row for 8 rules in the RuleEditorActivity
 */
public class RuleRowView extends LinearLayout {
    @BindView(R.id.textViewRuleRowItem)
    TextView textViewRuleRowItem;

    /*
     * Constructors
     */
    public RuleRowView(Context context) {
        this(context, null);
    }
    public RuleRowView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    /**
     * Init method to read attrs and get name of the text from it
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        // Inflate view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_rule_row, null);
        ButterKnife.bind(this, view);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.itemRule, 0, 0);

            try {
                String ruleName = a.getString(R.styleable.itemRule_name);
                textViewRuleRowItem.setText(ruleName);
            } finally {
                a.recycle();
            }
        }

        // Add inflated view to screen
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        removeAllViews();
        addView(view, params);
    }
}
