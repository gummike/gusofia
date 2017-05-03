package com.tongbanjie.gusofia.zk.test;

import com.tongbanjie.gusofia.zk.zookeeper.inner.ResilientActiveKeyValueStore;
import com.tongbanjie.gusofia.zk.zookeeper.utils.ZooUtils;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author zi.you
 * @date 17/4/20
 */
public class ZkWatchNode_getChildren_Test implements Watcher {

    private static Logger LOGGER = LoggerFactory.getLogger(ZkWatchNode_getChildren_Test.class);

    private ResilientActiveKeyValueStore store = null;

    @Override
    public void process(WatchedEvent event) {
        LOGGER.info("============= other start =============");
        Stat stat = new Stat();
        List<String> children = null;
        switch (event.getType()) {
            case NodeChildrenChanged: {
                // 会触发此事件
                LOGGER.info("[NodeChildrenChanged]");
                break;
            }
            case NodeCreated: {
                // 会触发此事件
                LOGGER.info("[NodeCreated]");
                break;
            }
            case NodeDataChanged: {
                LOGGER.info("[NodeDataChanged]");
                break;
            }
            case NodeDeleted: {
                // 会触发此事件
                LOGGER.info("[NodeDeleted]");
                break;
            }
            case None: {
                LOGGER.info("[None]");
                break;
            }
        }
        try {
            children = store.getZk().getChildren("/rootPath/node1", this);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("[result] : {}", children);
        LOGGER.info("[stat] : {}", stat);
    }

    public void init() throws IOException, InterruptedException, KeeperException {
        store = new ResilientActiveKeyValueStore(false);
        store.connect("192.168.1.20:2181");
        // create root node
        store.write("/rootPath", ZooUtils.getIp());
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZkWatchNode_getChildren_Test test = new ZkWatchNode_getChildren_Test();
        test.init();
        // create super node for prepare
        test.store.write("/rootPath/node1", "oneValue");
        // read and watch
        Stat stat = new Stat();
        List<String> children = test.store.getZk().getChildren("/rootPath/node1", test, stat);

        LOGGER.info("=============first start =============");
        LOGGER.info("[children] : {}", children);
        LOGGER.info("[stat] : {}", stat);
        LOGGER.info("=============first end =============");

        // stay alive until process is killed or thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }


}
