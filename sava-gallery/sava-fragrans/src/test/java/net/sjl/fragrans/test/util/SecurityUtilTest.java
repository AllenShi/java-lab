package net.sjl.fragrans.test.util;

import net.sjl.fragrans.util.SecurityUtil;
import org.junit.Test;

public class SecurityUtilTest {

    @Test
    public void testEscapeTableName() {
        String rawTable = "USERS WHERE 1 = 1; SELECT * FROM USERS";
        String table = SecurityUtil.trimSql(rawTable);
        String sql = "SELECT * FROM " + table;

        System.out.format("The SQL is %s\n", sql);
    }

    @Test
    public void testEscapeParams() {
        String nickName = "' OR 1=1 OR ''=''";
        String sql = "SELECT * FROM USERS WHERE name = '" + nickName + " AND isactive = 'Y'";

        System.out.format("The raw SQL is %s\n", sql);

        String escpNickName =  SecurityUtil.escapeSql(nickName);
        sql = "SELECT * FROM USERS WHERE name = '" + escpNickName + "' AND isactive = 'Y'";

        System.out.format("The escaped SQL is %s\n", sql);
    }


    @Test
    public void testEscapeInParams() {
        String nickName = "SELECT name FROM USER_ROLE WHERE role = 'A'";
        String sql = "SELECT * FROM USERS WHERE name IN (" + nickName + ") AND isactive = 'Y'";

        System.out.format("The raw SQL is %s\n", sql);

        String escpNickName =  SecurityUtil.escapeSql(nickName);
        sql = "SELECT * FROM USERS WHERE name IN (" + escpNickName + ") AND isactive = 'Y'";

        System.out.format("The escaped SQL is %s\n", sql);
    }


    @Test
    public void testEscapeAllSQL() {
        String nickName = "' OR 1=1 OR ''=''";
        String sql = "SELECT * FROM USERS WHERE name = '" + nickName + " AND isactive = 'Y'";

        System.out.format("The raw SQL is %s\n", sql);


        sql = SecurityUtil.escapeSql(sql);

        System.out.format("The escaped all SQL is %s\n", sql);
    }
}
