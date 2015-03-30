/**
 * 
 */
package com.google.code.shardbatis.builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.code.shardbatis.strategy.ShardStrategy;


/**
 * @author sean.he
 * 
 */
public class ShardbatisConfig {
    private static final class ShardbatisConfigHolder {
        private static final ShardbatisConfig instance = new ShardbatisConfig();
    }


    public static ShardbatisConfig getInstance() {
        return ShardbatisConfigHolder.instance;
    }

    private Map<String/* tableName */, ShardStrategy> strategyRegister = new HashMap<String, ShardStrategy>();

    private Set<String> ignoreSet;
    private Set<String> parseSet;


    /* Default Constructor is good habit */
    private ShardbatisConfig() {
    }


    /**
     * 注册分表策略
     * 
     * @param tableName
     * @param strategy
     */
    public void register(String tableName, ShardStrategy strategy) {
        this.strategyRegister.put(tableName.toLowerCase(), strategy);
    }


    /**
     * 查找对应表的分表策略
     * 
     * @param tableName
     * @return
     */
    public ShardStrategy getStrategy(String tableName) {
        return strategyRegister.get(tableName.toLowerCase());
    }


    /**
     * 增加ignore id配置
     * 
     * @param id
     */
    public synchronized void addIgnoreId(String id) {
        if (ignoreSet == null) {
            ignoreSet = new HashSet<String>();
        }
        ignoreSet.add(id);
    }


    /**
     * 增加parse id配置
     * 
     * @param id
     */
    public synchronized void addParseId(String id) {
        if (parseSet == null) {
            parseSet = new HashSet<String>();
        }
        parseSet.add(id);
    }


    /**
     * 判断是否配置过parse id<br>
     * 如果配置过parse id,shardbatis只对parse id范围内的sql进行解析和修改
     * 
     * @return
     */
    public boolean isConfigParseId() {
        return parseSet != null;
    }


    /**
     * 判断参数ID是否在配置的parse id范围内
     * 
     * @param id
     * @return
     */
    public boolean isParseId(String id) {
        return parseSet != null && parseSet.contains(id);
    }


    /**
     * 判断参数ID是否在配置的ignore id范围内
     * 
     * @param id
     * @return
     */
    public boolean isIgnoreId(String id) {
        return ignoreSet != null && ignoreSet.contains(id);
    }
}
