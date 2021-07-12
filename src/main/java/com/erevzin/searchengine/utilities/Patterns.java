package com.erevzin.searchengine.utilities;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class Patterns {

    public static final String UTF_16 = "UTF-16";
    public static final String UTF_8 = "UTF-8";
    public static Pattern idContentSeparatorPattern = Pattern.compile("\":");
    public static Pattern spacesPattern = Pattern.compile("\\s+");
    public static Pattern utf16StartPattern = Pattern.compile("\\\\u");
    public static Pattern andQueryPattern = Pattern.compile("AND");
    public static Pattern orQueryPattern = Pattern.compile("OR");
    public static Pattern charactersToExcludePattern = Pattern.compile("[,.()!?]");

}
