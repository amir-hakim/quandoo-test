package com.test.quandoo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.test.quandoo.controller.GOLConfig;

import java.util.ArrayList;
import java.util.List;

public class RuleSetDataSource {
	private SQLiteDatabase database;
	private GOLSQLiteHelper dbHelper;
	private String[] allColums = { GOLSQLiteHelper.COLUMN_ID,
			GOLSQLiteHelper.COLUMN_NAME, GOLSQLiteHelper.COLUMN_N0,
			GOLSQLiteHelper.COLUMN_N1, GOLSQLiteHelper.COLUMN_N2,
			GOLSQLiteHelper.COLUMN_N3, GOLSQLiteHelper.COLUMN_N4,
			GOLSQLiteHelper.COLUMN_N5, GOLSQLiteHelper.COLUMN_N6,
			GOLSQLiteHelper.COLUMN_N7, GOLSQLiteHelper.COLUMN_N8 };
	
	public RuleSetDataSource(Context context) {
		dbHelper = new GOLSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public void createRuleset(RuleSet ruleset) {
		ContentValues values = new ContentValues();
		values.put(GOLSQLiteHelper.COLUMN_NAME, ruleset.getName());
		int[] rules = ruleset.getRuleSet();
		values.put(GOLSQLiteHelper.COLUMN_N0, rules[0]);
		values.put(GOLSQLiteHelper.COLUMN_N1, rules[1]);
		values.put(GOLSQLiteHelper.COLUMN_N2, rules[2]);
		values.put(GOLSQLiteHelper.COLUMN_N3, rules[3]);
		values.put(GOLSQLiteHelper.COLUMN_N4, rules[4]);
		values.put(GOLSQLiteHelper.COLUMN_N5, rules[5]);
		values.put(GOLSQLiteHelper.COLUMN_N6, rules[6]);
		values.put(GOLSQLiteHelper.COLUMN_N7, rules[7]);
		values.put(GOLSQLiteHelper.COLUMN_N8, rules[8]);
		long id = database.insert(GOLSQLiteHelper.TABLE_RULES, null, values);
		
		if (id == -1) {
			Log.w(GOLConfig.APP_NAME, "Could not insert core data into database!");
		}
	}
	
public List<RuleSet> getAllRulesets() {
	List<RuleSet> ruleSets = new ArrayList<RuleSet>();
	Cursor cursor = database.query(
			GOLSQLiteHelper.TABLE_RULES,
			allColums, 
			null, null, 
			null, null, null);
	
	// cursor will be in front of the first entry so we 
	// need to move it to the first item
	cursor.moveToFirst();
	while(!cursor.isAfterLast()) {
		RuleSet ruleSet = cursorToRuleSet(cursor);
		ruleSets.add(ruleSet);
		cursor.moveToNext();
	}
	
	cursor.close();
	return ruleSets;
}
	
	public void deleteRuleset(RuleSet ruleSet) {
		long id = ruleSet.getId();
		database.delete(GOLSQLiteHelper.TABLE_RULES, GOLSQLiteHelper.COLUMN_ID + " = " + id, null);
	}
	
	private RuleSet cursorToRuleSet(Cursor cursor) {
		RuleSet ruleSet = new RuleSet();
		ruleSet.setId(cursor.getLong(0));
		ruleSet.setName(cursor.getString(1));
		int[] rules = new int[9];
		
		for (int i = 0; i < rules.length; i++) {
			rules[i] = cursor.getInt(i + 2); 
		}
		
		ruleSet.setRuleSet(rules);
		
		return ruleSet;
	}

}
