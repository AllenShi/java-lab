package net.sjl.fragrans.util;

public class SecurityUtil {
    private static final int SIZE = 128;
    private static final CharMasks MASKS = new CharMasks(SIZE) {
        {
            this.addCharToMasks("\\\u0000\n\r'\"");
        }
    };

    private static final boolean[] ESCAPE_CMD_MASKS;
    private static final CharMasks FILTER_ORDER_BY_MASKS;

    static {
        ESCAPE_CMD_MASKS = MASKS.getMasks();
        FILTER_ORDER_BY_MASKS = new CharMasks(SIZE) {
            {
                // 0 - 9
                for(int i = 48; i < 58; ++i) {
                    this.addCharToMasks((char)i);
                }

                // A - Z
                for(int i = 65; i < 91; ++i) {
                    this.addCharToMasks((char)i);
                }

                // a - z
                for(int i = 97; i < 123; ++i) {
                    this.addCharToMasks((char)i);
                }

                this.addCharToMasks("_-.");
            }
        };
    }

    public SecurityUtil() {
    }

    public static String escapeSql(String sql) {
        if(true) {
            return filterStatically(sql);
        } else {
            return sql;
        }
    }

    public static String trimSql(String sql) {
        if(true) {
            return trimSqlStatically(sql);
        } else {
            return sql;
        }
    }


    static String filterStatically(String sql) {
        if (sql != null && !sql.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int lastPos = 0;

            for(int i = 0; i < sql.length(); ++i) {
                char c = sql.charAt(i);
                if (c < 128 && ESCAPE_CMD_MASKS[c]) {
                    sb.append(sql.substring(lastPos, i));
                    sb.append('\\').append(c);
                    lastPos = i + 1;
                } else if (c == 26) {
                    sb.append(sql.substring(lastPos, i)).append("0x1a");
                    lastPos = i + 1;
                }
            }

            return sb.append(sql.substring(lastPos)).toString();
        } else {
            return sql;
        }
    }

    static String trimSqlStatically(String columnName) {
        if (columnName != null && !columnName.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int lastPos = 0;

            for(int i = 0; i < columnName.length(); ++i) {
                char c = columnName.charAt(i);
                if (c >= 128 || !FILTER_ORDER_BY_MASKS.getMasks()[c]) {
                    sb.append(columnName.substring(lastPos, i));
                    lastPos = i + 1;
                }
            }

            return sb.append(columnName.substring(lastPos)).toString();
        } else {
            return columnName;
        }
    }
}
