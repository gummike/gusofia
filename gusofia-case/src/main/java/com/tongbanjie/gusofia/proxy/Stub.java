package com.tongbanjie.gusofia.proxy;

/**
 * @author zi.you
 * @date 17/6/20
 */
public class Stub implements Itf {

    @Override
    public void getName() {
        System.out.println("MIKE");
    }

    @Override
    public void getGender() {
        System.out.println("BOY");
    }
}
