package com.tongbanjie.gusofia.zk.zookeeper.inner;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public class ConfigUpdater {

    public static final String PATH = "/config";

    private ResilientActiveKeyValueStore store;
    private Random random = new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new ResilientActiveKeyValueStore(true);
        store.connect(hosts);
    }

    public void run() throws InterruptedException, KeeperException {

        while (true) {
            String value = random.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n", PATH, value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

    public static void main(String[] args) throws Exception {

        ConfigUpdater configUpdater = new ConfigUpdater("192.168.1.20:2181");
        configUpdater.run();
    }
}
