package com.iee.mongodb.opc.utgard.test;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;

import java.util.Map;
import java.util.concurrent.Executors;

/**
 * @ClassName ItemSynchronousQuery
 * @Description Item的同步查询
 * @Author longxiaonan@163.com
 * @Date 2018/9/7 0007 11:21
 */
public class ItemSynchronousQuery {
    public static void main(String[] args) throws Exception {

        ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("10.1.5.123");
        ci.setDomain("");
        ci.setUser("freud");
        ci.setPassword("password");
        ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305");

        Server server = new Server(ci,
                Executors.newSingleThreadScheduledExecutor());

        server.connect();

        Group group = server.addGroup();
        Item item = group.addItem("Random.Real5");

        Map<String, Item> items = group.addItems("Random.Real1",
                "Random.Real2", "Random.Real3", "Random.Real4");

        dumpItem(item);

        for (Map.Entry<String, Item> temp : items.entrySet()) {
            dumpItem(temp.getValue());
        }

        server.dispose();
    }

    private static void dumpItem(Item item) throws JIException {
        System.out.println("[" + (++count) + "],ItemName:[" + item.getId()
                + "],value:" + item.read(false).getValue());
    }

    private static int count;
}
