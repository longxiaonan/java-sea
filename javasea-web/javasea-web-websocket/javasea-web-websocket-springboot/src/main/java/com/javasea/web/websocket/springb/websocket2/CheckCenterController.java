package com.javasea.web.websocket.springb.websocket2;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/checkcenter")
public class CheckCenterController {

    /**
     * 推送数据接口
     * @param cid
     * @param message
     * @return
     */
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable String cid,String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return "error:"+cid+"#"+e.getMessage();
        }
        return "success:"+cid;
    }

}
