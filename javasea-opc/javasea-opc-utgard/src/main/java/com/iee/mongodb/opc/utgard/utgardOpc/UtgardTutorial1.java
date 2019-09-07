package com.iee.mongodb.opc.utgard.utgardOpc;

import java.util.concurrent.Executors;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;

/** 读值 */
public class UtgardTutorial1 {

    public static void main(String[] args) throws Exception {
        // 连接信息
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("192.168.1.118");
//        ci.setDomain("");
        ci.setUser("Administrator");
        ci.setPassword("123456");
        final String itemId = "MWSMART.line2.item1";//项的名字按实际

        // MatrikonOPC Server, clsid在MatrikonOPC Explorer可以找到，是固定的
//         ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305");

        // KEPServer
//        ci.setClsid("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729");
//        final String itemId = "u.u.u";// 项的名字按实际
        // final String itemId = "通道 1.设备 1.标记 1";

        // S7-200 PC Access opc server
        ci.setClsid("137CA264-75FA-4c17-BC35-7FE340BF124C");

        // create a new server
        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        try {
            // connect to server
            server.connect();
            // add sync access, poll every 500 ms
            final AccessBase access = new SyncAccess(server, 500);
            access.addItem(itemId, new DataCallback() {
                @Override
                public void changed(Item item, ItemState state) {
                    System.out.println("-----" + state);
                }
            });
            // start reading
            access.bind();
            // wait a little bit
            Thread.sleep(10 * 1000);
            // stop reading
            access.unbind();
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
        }
    }
}