package com.javasea.web.websocket.springb.websocket2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName Test
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/11/3 0003 11:37
 */
@ComponentScan
@Controller
@RequestMapping("/page")
public class GetPageController {

    /**
     * 页面请求
     * @param cid
     * @return
     */
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("index");
        mav.addObject("cid", cid);
        return mav;
    }

    @GetMapping("/ww/{cid}")
    public String websocket2(@PathVariable String cid) {
        return "index";
    }

}
