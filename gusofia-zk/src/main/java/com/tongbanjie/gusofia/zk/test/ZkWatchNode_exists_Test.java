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

/**
 * @author zi.you
 * @date 17/4/20
 */
public class ZkWatchNode_exists_Test implements Watcher {

    private static Logger LOGGER = LoggerFactory.getLogger(ZkWatchNode_exists_Test.class);

    private ResilientActiveKeyValueStore store = null;

    @Override
    public void process(WatchedEvent event) {
        LOGGER.info("============= exist notify =============");
        LOGGER.info("[eventType] : {}", event.getType());

        switch (event.getType()) {
            case NodeChildrenChanged: {
                LOGGER.info("[NodeChildrenChanged]");
                break;
            }
            case NodeCreated: {
                // 会触发此事件
                LOGGER.info("[NodeCreated]");
                break;
            }
            case NodeDataChanged: {
                // 会触发此事件
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
            default: {
                // 不会触发此事件
                LOGGER.info("[default]");
            }
        }
        try {
            store.getZk().exists("/rootPath/node_not_exits", this);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException, InterruptedException, KeeperException {
        store = new ResilientActiveKeyValueStore(false);
        store.connect("192.168.1.20:2181");
        // create root node
        store.write("/rootPath", ZooUtils.getIp());
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZkWatchNode_exists_Test test = new ZkWatchNode_exists_Test();
        test.init();
        // delete node for prepare
//        test.store.deleteNode("/rootPath/node_not_exits");
        // watch exist
        Stat exists = test.store.getZk().exists("/rootPath/node_not_exits", test);

        LOGGER.info("=============first start =============");
        LOGGER.info("[exists] : {}", exists);
        LOGGER.info("=============first end =============");

        // stay alive until process is killed or thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }


}
