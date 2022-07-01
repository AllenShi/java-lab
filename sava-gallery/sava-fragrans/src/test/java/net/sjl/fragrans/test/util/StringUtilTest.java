package net.sjl.fragrans.test.util;

import net.sjl.fragrans.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void testEscapeTableName() {
        String rawTable = "ORDER WHERE 1 = 1; SELECT * FROM ORDER";
        String table = StringUtil.escapeSQL(rawTable, true);
        String sql = "SELECT * FROM " + table;

        System.out.format("The SQL is %s\n", sql);
    }

    @Test
    public void testEscapeParams() {
        String nickName = "' OR 1=1 OR ''=''";
        String sql = "SELECT * FROM Order WHERE nick_name = '" + nickName + " AND private = 'N'";

        System.out.format("The raw SQL is %s\n", sql);

        String escpNickName =  StringUtil.escapeSQL(nickName, true);
        sql = "SELECT * FROM Order WHERE nick_name = '" + escpNickName + "' AND private = 'N'";

        System.out.format("The escaped SQL is %s\n", sql);
    }


    @Test
    public void testApacheEscapeParams() {
        String nickName = "' OR 1=1 OR ''=''";
        String sql = "SELECT * FROM Order WHERE nick_name = '" + nickName + " AND private = 'N'";

        System.out.format("The raw SQL is %s\n", sql);

        String escpNickName =  StringEscapeUtils.escapeSql(nickName);
        sql = "SELECT * FROM Order WHERE nick_name = '" + escpNickName + "' AND private = 'N'";

        System.out.format("The escaped SQL is %s\n", sql);
    }


    @Test
    public void testEscapeString() {
        String name = null;
        String nickName = new StringBuilder().append("'").append(name).append("'").toString();

        System.out.format("The nickName is %s\n", nickName);
    }

}
