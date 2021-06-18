package com.yw.mybatis.framework.sqlnode;

import com.yw.mybatis.framework.DynamicContext;

/**
 * @author yangwei
 */
public interface SqlNode {
    void apply(DynamicContext context);
}
