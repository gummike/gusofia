package com.tongbanjie.gusofia.pattern.test;

import com.tongbanjie.gusofia.pattern.Instance;
import com.tongbanjie.gusofia.pattern.singleton.SingletonStaticHolder;

/**
 * @author zi.you
 * @date 17/5/3
 */
public class SingletonTest {

    public static void main(String[] args) {
        Instance instance = SingletonStaticHolder.getInstance();
    }
}
