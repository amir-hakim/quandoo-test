package com.test.quandoo.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.quandoo.R;
import com.test.quandoo.controller.GOLConfig;
import com.test.quandoo.database.RuleSet;
import com.test.quandoo.database.RuleSetDataSource;
import com.test.quandoo.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * This activity handles the rule editor.
 */
public class RuleEditorActivity extends BaseActivity {

    @BindView(R.id.buttonSaveRule)
    Button buttonSaveRule;
    @BindView(R.id.buttonLoadRule)
    Button buttonLoadRule;
    @BindView(R.id.buttonDeleteRule)
    Button buttonDeleteRule;
    /**
     * A data access object.
     */
    private RuleSetDataSource datasource;
    /**
     * Holds all our RadioGroup Objects.
     * Every RadioGroup contains 3 RadioButtons each for one
     * of the different rules.
     */
    private List<RadioGroup> buttons;
    /**
     * Holds the rules.
     */
    private int[] gameRule = new int[9];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruleeditor);

        ButterKnife.bind(this);

        datasource = new RuleSetDataSource(this);

        // fill the list with ButtonGroups
        buttons = new ArrayList<RadioGroup>();
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView0));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView1));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView2));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView3));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView4));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView5));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView6));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView7));
        buttons.add(getRadioGrouoFromRuleItem(R.id.ruleRowView8));

        // set listeners
        for (int i = 0; i < buttons.size(); i++) {

            // add listeners
            RadioGroup group = buttons.get(i);
            for (int j = 0; j < group.getChildCount(); j++) {
                RadioButton button = (RadioButton) group.getChildAt(j);
                final int finalI = i;
                final int finalJ = j;

                button.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (finalJ == 0) {
                            gameRule[finalI] = GOLConfig.BIRTH_RULE;
                        } else if (finalJ == 1) {
                            gameRule[finalI] = GOLConfig.DEATH_RULE;
                        } else {
                            gameRule[finalI] = GOLConfig.UNDEFINED;
                        }

                    }
                });
            }
        }

        // set initial state of the buttons -> currently used rules
        gameRule = GOLConfig.getRuleSet();
        updateRadioButtons();
    }

    private RadioGroup getRadioGrouoFromRuleItem(int ruleResId) {
        return (RadioGroup) findViewById(ruleResId).findViewById(R.id.radioGroupRuleRowItem);
    }

    /**
     * Creates an AlertDialog that asks the user to pick a RuleSet that should
     * be deleted from the database. If the user does not cancel the dialog
     * another dialog will ask if he really wants to delete the selected {@link RuleSet}.
     */
    private void deleteRuleSet() {
        // open connection to db
        datasource.open();

        // get all saved RuleSets
        final List<RuleSet> values = datasource.getAllRulesets();
        int size = values.size();

        // create an array that holds the RuleSets names as CharSequence
        final CharSequence[] items = new CharSequence[size];

        for (int i = 0; i < size; i++) {
            items[i] = values.get(i).getName();
        }

        // we the context as final
        final Context context = this;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(getString(R.string.pick_ruleset));
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, final int click_which) {
                // does the user really want to delete the entry?
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setMessage(getString(R.string.deleted_ruleset_warning) + " " + items[click_which] + "?")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // delete entry and tell the user
                                datasource.deleteRuleset(values.get(click_which));
                                Toast.makeText(getApplicationContext(), getString(R.string.deleted_ruleset) + items[click_which],
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert2 = builder2.create();
                alert2.show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Saves the current state of the RadioGroup objects as a {@link RuleSet} in the
     * database, if the user entered a name for it. If the user did not enter
     * a name he will be asked to do so.
     */
    private void saveRuleSet() {
        TextView name = (TextView) findViewById(R.id.editTextName);

        // entered a name for the RuleSet?
        if (name.getText().length() > 0) {
            // create a new RuleSet object and fill it with data
            RuleSet ruleSet = new RuleSet();
            ruleSet.setName(name.getText().toString());
            ruleSet.setRuleSet(gameRule);

            // open connection to db and save the RuleSet
            datasource.open();
            datasource.createRuleset(ruleSet);

            Toast.makeText(this, getString(R.string.saved_success), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.save_warning))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Creates an AlertDialog that asks the user to pick a {@link RuleSet} that should
     * be loaded from the database.
     */
    private void loadRuleSets() {
        // open connection to db
        datasource.open();

        // get all saved RuleSets
        final List<RuleSet> values = datasource.getAllRulesets();
        int size = values.size();

        // create an array that holds the RuleSets names as CharSequence
        final CharSequence[] items = new CharSequence[size];

        for (int i = 0; i < size; i++) {
            items[i] = values.get(i).getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.pick_ruleset));
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // update state of RadioGroups
                gameRule = values.get(which).getRuleSet();
                updateRadioButtons();
                Toast.makeText(getApplicationContext(), items[which], Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Updates the RadioButton objects by getting the currently, globally used rules
     * and apllying it to the RadioButtons.
     */
    private void updateRadioButtons() {
        // get the rules
        RadioButton temp;

        for (int i = 0; i < buttons.size(); i++) {
            temp = (RadioButton) buttons.get(i).getChildAt(0);
            int rule = gameRule[i];

            if (rule == GOLConfig.DEATH_RULE) {
                temp = (RadioButton) buttons.get(i).getChildAt(1);
            } else if (rule == GOLConfig.UNDEFINED) {
                temp = (RadioButton) buttons.get(i).getChildAt(2);
            }

            temp.toggle();
        }
    }

    /**
     * On Save Rule button clicked
     */
    @OnClick(R.id.buttonSaveRule)
    protected void onSaveRuleButtonClicked() {
        saveRuleSet();
    }

    /**
     * On Load Rule button clicked
     */
    @OnClick(R.id.buttonLoadRule)
    protected void onLoadRuleButtonClicked() {
        loadRuleSets();
    }

    /**
     * On Delete Rule button clicked
     */
    @OnClick(R.id.buttonDeleteRule)
    protected void onDeleteRuleButtonClicked() {
        deleteRuleSet();
    }

    @Override
    protected void onPause() {
        // close connection to db if opened
        datasource.close();

        // be sure to set the rules when leaving the activity
        GOLConfig.setRuleSet(gameRule);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
