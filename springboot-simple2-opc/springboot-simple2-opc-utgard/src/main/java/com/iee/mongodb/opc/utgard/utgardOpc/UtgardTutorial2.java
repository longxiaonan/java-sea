package com.iee.mongodb.opc.utgard.utgardOpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;

/** 写值  */
public class UtgardTutorial2 {
     
    public static void main(String[] args) throws Exception {
 
        // 连接信息 
        final ConnectionInformation ci = new ConnectionInformation();
        
        ci.setHost("192.168.0.1");
        ci.setDomain("");
        ci.setUser("OPCUser");
        ci.setPassword("123456");
        
        // MatrikonOPC Server
         ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305");
         final String itemId = "uu.u";//项的名字按实际

        // KEPServer
//        ci.setClsid("7BC0CC8E-482C-47CA-ABDC-0FE7F9C6E729");
//        final String itemId = "u.u.u";// 项的名字按实际
        // final String itemId = "通道 1.设备 1.标记 1";
        
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
                    // also dump value
                    try {
                        if (state.getValue().getType() == JIVariant.VT_UI4) {
                            System.out.println("<<< " + state + " / value = " + state.getValue().getObjectAsUnsigned().getValue());
                        } else {
                            System.out.println("<<< " + state + " / value = " + state.getValue().getObject());
                        }
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                }
            });
 
            // Add a new group
            final Group group = server.addGroup("test");
            // Add a new item to the group
            final Item item = group.addItem(itemId);
 
            // start reading
            access.bind();
 
            // add a thread for writing a value every 3 seconds
            //写入线程，至于为什么要用线程，要一直写，，如果只写一次就不用写线程了
            ScheduledExecutorService writeThread = Executors.newSingleThreadScheduledExecutor();
            writeThread.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    final JIVariant value = new JIVariant("24");//一直写入24
                    try {
                        System.out.println(">>> " + "writing value " + "24");
                        item.write(value);
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                }
            }, 5, 3, TimeUnit.SECONDS);
 
            // wait a little bit 
            Thread.sleep(20 * 1000);
            writeThread.shutdownNow();
            // stop reading
            access.unbind();
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
        }
    }
}