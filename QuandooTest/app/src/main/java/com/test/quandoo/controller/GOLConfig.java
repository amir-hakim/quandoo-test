package com.test.quandoo.controller;

/**
 * GOLConfig (Game Of Life Config)is used for global variables and constants.
 */
public class GOLConfig {

    /**
     * Identifier for the application name.
     */
    public static final String APP_NAME = "Quandoo-Test";

    /**
     * Identifier for the fact, that the rule is undefined.
     */
    public static final int UNDEFINED = 0;

    /**
     * Identifier for the birth rule.
     */
    public static final int BIRTH_RULE = 1;

    /**
     * Identifier for the death rule.
     */
    public static final int DEATH_RULE = 2;

    /**
     * Identifier for the ruleset that is currently used.
     */
    private static int[] ruleSet;

    /**
     * The width of the device.
     */
    private static int viewportWidth;

    /**
     * The height of the device.
     */
    private static int viewportHeight;

    /**
     * Getter for {@link GOLConfig#viewportWidth}.
     *
     * @return {@link GOLConfig#viewportWidth}.
     */
    public static int getViewportWidth() {
        return viewportWidth;
    }

    /**
     * Setter for {@link GOLConfig#viewportWidth}.
     *
     * @param newViewportWidth The new width for the device.
     */
    public static void setViewportWidth(final int newViewportWidth) {
        viewportWidth = newViewportWidth;
    }

    /**
     * Getter for {@link GOLConfig#viewportHeight}.
     *
     * @return {@link GOLConfig#viewportHeight}.
     */
    public static int getViewportHeight() {
        return viewportHeight;
    }

    /**
     * Setter for {@link GOLConfig#viewportHeight}.
     *
     * @param newViewportHeight The new height for the device.
     */
    public static void setViewportHeight(final int newViewportHeight) {
        viewportHeight = newViewportHeight;
    }

    /**
     * Getter for {@link GOLConfig#ruleSet}.
     *
     * @return {@link GOLConfig#ruleSet}.
     */
    public static int[] getRuleSet() {
        return ruleSet;
    }

    /**
     * Setter for {@link GOLConfig#ruleSet}.
     *
     * @param newRuleSet The new ruleset for the game.
     */
    public static void setRuleSet(int[] newRuleSet) {
        ruleSet = newRuleSet;
    }
}
