package com.tongbanjie.gusofia.zk.test;


import com.tongbanjie.gusofia.zk.zookeeper.ZookeeperMgr;

public class ZkTest {

    private static final String ZK_HOST = "192.168.1.20:2181";
    private static final String ZK_PREFIX = "/zktest";

    public static void main(String[] args) {
        try {
            ZookeeperMgr.getInstance().init(ZK_HOST, ZK_PREFIX, true);
            ZookeeperMgr.getInstance().makeDir(ZK_PREFIX+"/gulidong","123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
