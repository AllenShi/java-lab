package net.sjl.fragrans.sql.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLInSubQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import net.sjl.fragrans.log.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DruidParser {
    private static Logger LOGGER = LoggerFactory.getLogger(DruidParser.class);

    public static void convertSelectSQL(String sql) {
        LoggerContext.enableTrace();
        SQLStatementParser parser = new MySqlStatementParser(sql);
        // 使用Parser解析生成AST，这里SQLStatement就是AST
        SQLStatement statement = parser.parseStatement();

        // 使用visitor来访问AST
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        // 从visitor中拿出你所关注的信息
        LOGGER.info("The columns is {}", visitor.getColumns());
        LoggerContext.disableTrace();
    }

    public static void convertInsertSQL(String sql){
        LoggerContext.enableTrace();
        try{
            SQLStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement statement = parser.parseStatement();
            MySqlInsertStatement insert = (MySqlInsertStatement)statement;
            String tableName = insert.getTableName().getSimpleName();

            List<SQLExpr> columns = insert.getColumns();

            LOGGER.info("The table name is {}, and the columns are {}", tableName, columns);
        }catch(Exception e){ // 发生异常，则返回原始 sql
            LOGGER.error("The error thrown is {}", e);
        }
        LoggerContext.disableTrace();
    }

    public static void convertUpdateSQL(String sql){
        LoggerContext.enableTrace();
        try{
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement stmt = parser.parseStatement();
            MySqlUpdateStatement update = (MySqlUpdateStatement)stmt;
            SQLTableSource ts = update.getTableSource();
            if(ts != null && ts.toString().contains(",")){
                System.out.println(ts.toString());
                LOGGER.warn("Do not support Multiple-table udpate syntax...");
            }

            String tableName = update.getTableName().getSimpleName();
            SQLExpr se = update.getWhere();
            LOGGER.info("The where clause is {}", se);
            // where中有子查询： update company set name='com' where id in (select id from xxx where ...)
            if(se instanceof SQLInSubQueryExpr){
                LOGGER.info("The sub-query in where clause is {}", se);
            }

        }catch(Exception e){
            LOGGER.warn(e.getMessage());
        }
        LoggerContext.disableTrace();
    }
}
