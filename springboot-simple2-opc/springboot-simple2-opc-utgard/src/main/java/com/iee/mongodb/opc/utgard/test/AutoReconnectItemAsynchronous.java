package com.iee.mongodb.opc.utgard.test;

import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;

import java.util.concurrent.Executors;

/**
 * @ClassName AutoReconnectItemAsynchronous
 * @Description 自动重连Item异步读取
 * @Author longxiaonan@163.com
 * @Date 2018/9/7 0007 11:26
 */
public class AutoReconnectItemAsynchronous {
    private static final int PERIOD = 100;

    private static final int SLEEP = 2000;

    public static void main(String[] args) throws Exception {

        ConnectionInformation ci = new ConnectionInformation();
        ci.setHost("10.1.5.123");
        ci.setDomain("");
        ci.setUser("freud");
        ci.setPassword("password");
        ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305");

        Server server = new Server(ci,
                Executors.newSingleThreadScheduledExecutor());

        AutoReconnectController controller = new AutoReconnectController(server);

        controller.connect();

        AccessBase access = new SyncAccess(server, PERIOD);

        access.addItem("Random.Real5", new DataCallback() {
            private int i;

            public void changed(Item item, ItemState itemstate) {
                System.out.println("[" + (++i) + "],ItemName:[" + item.getId()
                        + "],value:" + itemstate.getValue());
            }
        });

        access.bind();
        Thread.sleep(SLEEP);
        access.unbind();
        controller.disconnect();
    }
}
