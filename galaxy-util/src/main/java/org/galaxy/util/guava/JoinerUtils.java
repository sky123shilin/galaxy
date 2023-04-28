package org.galaxy.util.guava;

import com.google.common.base.Joiner;

public class JoinerUtils {

    private static final String NULL = "null";
    public static final Joiner blankJoiner = Joiner.on("").skipNulls();
    public static final Joiner blankJoinerWithNull = Joiner.on("").useForNull("null");
    public static final Joiner spaceJoiner = Joiner.on(" ").skipNulls();
    public static final Joiner spaceJoinerWithNull = Joiner.on(" ").useForNull("null");
    public static final Joiner commaJoiner = Joiner.on(",").skipNulls();
    public static final Joiner commaJoinerWithNull = Joiner.on(",").useForNull("null");
    public static final Joiner equalJoiner = Joiner.on("=").skipNulls();
    public static final Joiner equalJoinerWithNull = Joiner.on("=").useForNull("null");
    public static final Joiner vLineJoiner = Joiner.on("|").skipNulls();
    public static final Joiner vLineJoinerWithNull = Joiner.on("|").useForNull("null");
    public static final Joiner hLineJoiner = Joiner.on("-").skipNulls();
    public static final Joiner hLineJoinerWithNull = Joiner.on("-").useForNull("null");
    public static final Joiner underlineJoiner = Joiner.on("_").skipNulls();
    public static final Joiner underlineJoinerWithNull = Joiner.on("_").useForNull("null");
    public static final Joiner pathJoiner = Joiner.on("/").skipNulls();
    public static final Joiner pathJoinerWithNull = Joiner.on("/").useForNull("null");

    public JoinerUtils() {}

}
