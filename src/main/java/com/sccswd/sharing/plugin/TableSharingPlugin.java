package com.sccswd.sharing.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * <a href="https://sccswd.github.io">Github</a><br/>
 *
 * @author sccswd
 * @since 1.0 2017/9/12
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class TableSharingPlugin implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableSharingPlugin.class);
    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Long value) {
        THREAD_LOCAL.set(value);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String initialSQL = statementHandler.getBoundSql().getSql();
        String resultSQL = convert(initialSQL);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("initialSQL:{}", initialSQL);
            LOGGER.debug("resultSQL:{}", resultSQL);
        }
        if (!initialSQL.equals(resultSQL)) {
            Field field = BoundSql.class.getDeclaredField("sql");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, statementHandler.getBoundSql(), resultSQL);
        }
        return invocation.proceed();
    }

    private String convert(String sql) {
        Long id = THREAD_LOCAL.get();
        if (id == null) {
            throw new RuntimeException("未设置用于分表的字段值");
        }
        long sharingIndex = id % 2;

        return sql.replace("@{sharingIndex}", sharingIndex + "");
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
