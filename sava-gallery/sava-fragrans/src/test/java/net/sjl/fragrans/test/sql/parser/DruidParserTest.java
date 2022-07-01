package net.sjl.fragrans.test.sql.parser;

import net.sjl.fragrans.sql.parser.DruidParser;
import org.junit.Test;

public class DruidParserTest {

    @Test
    public void testConvertSelectSQL() {

        String sql = "SELECT a, b FROM user";
        DruidParser.convertSelectSQL(sql);
    }

    @Test
    public void testConvertInsertSQL() {

        String sql = "INSERT INTO A(userid, username) VALUES(1, 'a'), (2, 'b')";
        DruidParser.convertInsertSQL(sql);
    }

    @Test
    public void testConvertUpdateSQL() {

        String sql = "UPDATE A SET username = 'c' WHERE userid = 2";
        DruidParser.convertUpdateSQL(sql);
    }
}
