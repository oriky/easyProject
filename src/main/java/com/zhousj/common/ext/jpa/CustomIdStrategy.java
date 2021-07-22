package com.zhousj.common.ext.jpa;

import com.zhousj.common.ext.IdWorker;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * @author zhousj
 * @date 2021/5/19
 */
@SuppressWarnings("unused")
public class CustomIdStrategy extends IdentityGenerator {

    public Serializable generate(SessionImplementor s, Object obj) {
        return IdWorker.generate();
    }
}
