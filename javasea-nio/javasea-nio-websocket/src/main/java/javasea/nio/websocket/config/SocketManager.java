package javasea.nio.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 需要一个管理Socket的类
 */
@Slf4j
public class SocketManager {
    private static ConcurrentHashMap<String, WebSocketSession> manager = new ConcurrentHashMap<String, WebSocketSession>();

    public static void add(String key, WebSocketSession webSocketSession) {
        log.info("新添加webSocket连接 {} ", key);
        manager.put(key, webSocketSession);
    }

    public static void remove(String key) {
        log.info("移除webSocket连接 {} ", key);
        manager.remove(key);
    }

    public static WebSocketSession get(String key) {
        log.info("获取webSocket连接 {}", key);
        return manager.get(key);
    }


}


