package heartMonitor.cache;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 当有多个客户端连上来时，服务端需要区分开，不然响应消息就会发生混乱。
 *
 * 所以每当有个连接上来的时候，我们都将当前的 Channel 与连上的客户端 ID 进行关联（因此每个连上的客户端 ID 都必须唯一）。
 *
 * 这里采用了一个 Map 来保存这个关系，并且在断开连接时自动取消这个关联
 * @Author longxiaonan@163.com
 * @Date 11:24 2018/9/25 0025
 **/
public class NettySocketHolder {
    private static final Map<Long, NioSocketChannel> MAP = new ConcurrentHashMap<>(16);
    public static void put(Long id, NioSocketChannel socketChannel) {
        MAP.put(id, socketChannel);
    }
    public static NioSocketChannel get(Long id) {
        return MAP.get(id);
    }
    public static Map<Long, NioSocketChannel> getMAP() {
        return MAP;
    }
    public static void remove(NioSocketChannel nioSocketChannel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> MAP.remove(entry.getKey()));
    }
}