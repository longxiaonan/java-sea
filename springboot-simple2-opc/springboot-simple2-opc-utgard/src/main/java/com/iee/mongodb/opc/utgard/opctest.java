package com.iee.mongodb.opc.utgard;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.Leaf;

import java.net.UnknownHostException;
import java.util.concurrent.Executors;

/**
 * @ClassName opctest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/7 0007 16:56
 */
public class opctest {

    private static final int PERIOD = 100;
    private static final int SLEEP = 2000;

    public static void main(String[] args) throws Exception {
        //链接
        Server server = connect();
        //写
        write(server);
        //读
        reade(server);
    }

    private static void reade(Server server) throws Exception {
        AccessBase access = new SyncAccess(server, PERIOD);

        access.addItem("MWSMART.NewPLC.finish", new DataCallback() {
            private int i;

            public void changed(Item item, ItemState itemstate) {
                System.out.println("[" + (++i) + "],ItemName:[" + item.getId()
                        + "],value:" + itemstate.getValue());
            }
        });
    }

    private static void write(Server server) throws Exception {
        final String itemId = "MWSMART.line2.start";//项的名字按实际

        Group group = server.addGroup();
        Item item = group.addItem(itemId);
//        final Boolean[] integerData = new Boolean[] { true };
        final Integer[] integerData = new Integer[]{1};
//        final Boolean[] integerData = new Boolean[] { false };
        final JIArray array = new JIArray(integerData, false);
//        final JIVariant value = new JIVariant(array);
        final JIVariant value = new JIVariant(true);
        /** 获得OPC连接下所有的Group和Item */
        dumpTree(server.getTreeBrowser().browse(), 0);
        /** 获得OPC下所有的Item */
        dumpFlat(server.getFlatBrowser());

        item.write(value);
        Thread.sleep(2000);

        dumpItem(item);

        server.dispose();

    }

    private static Server connect() throws Exception {
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("192.168.1.118");
        ci.setUser("Administrator");
        ci.setPassword("123456");
        ci.setClsid("137CA264-75FA-4c17-BC35-7FE340BF124C");
        Server server = new Server(ci,
                Executors.newSingleThreadScheduledExecutor());
        server.connect();
        return server;
    }

    private static void dumpItem(Item item) throws JIException {
        System.out.println("[" + (++count) + "],ItemName:[" + item.getId()
                + "],value:" + item.read(true).getValue());
    }

    private static int count;

    /**
     * 遍历Flat结构的所有Item值
     *
     * @param browser
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     * @throws JIException
     */
    private static void dumpFlat(final FlatBrowser browser)
            throws IllegalArgumentException, UnknownHostException, JIException {
        for (String name : browser.browse()) {
            System.out.println(name);
        }
    }

    /**
     * 遍历Tree结构
     *
     * @param branch
     * @param level
     */
    private static void dumpTree(final Branch branch, final int level) {

        for (final Leaf leaf : branch.getLeaves()) {
            dumpLeaf(leaf, level);
        }
        for (final Branch subBranch : branch.getBranches()) {
            dumpBranch(subBranch, level);
            dumpTree(subBranch, level + 1);
        }
    }

    private static String printTab(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    /**
     * 打印Item
     *
     * @param leaf
     */
    private static void dumpLeaf(final Leaf leaf, final int level) {
        System.out.println(printTab(level) + "Leaf: " + leaf.getName() + ":"
                + leaf.getItemId());
    }

    /**
     * 打印Group
     *
     * @param branch
     */
    private static void dumpBranch(final Branch branch, final int level) {
        System.out.println(printTab(level) + "Branch: " + branch.getName());
    }
}