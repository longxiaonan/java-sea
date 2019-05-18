package com.iee.bigdata.datav.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/19 0019 15:09
 */
@RestController
@Slf4j
public class TestController {

    //仓库总面积数, 已用+未用
    //http://120.79.246.166:5678/totalArea
    //所有仓库的总面积和
    @GetMapping("/totalArea")
    public List totalArea() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "");//name必须是"",固定写法
        map.put("value", "232245");//总面积和  **
        list.add(map);
        return list;
    }

    //铝模板产品总数, 所有租户拥有的
    //http://120.79.246.166:5678/totalProduct
    @GetMapping("/totalProduct")
    public List totalProduct() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "");//name必须是"",固定写法
        map.put("value", "7909291");//总产品数  **
        list.add(map);
        return list;
    }

    //今日入库数
    //从00:00开始到当前时间的入库数量
    //http://120.79.246.166:5678/todayInbound?area_id=:area_id
    @GetMapping("/todayInbound")
    public List todayInbound(@RequestParam String area_id) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "");//name必须是"",固定写法
        map.put("value", "4221");//今日入库的数值  **

        if(Objects.equals(area_id,"440000")) {//广东
            map.put("value", "980");//今日入库的数值  **
        }
        if(Objects.equals(area_id,"450000")) {//广西
            map.put("value", "211");//今日入库的数值  **
        }
        list.add(map);
        return list;
    }

    //当前冻结数
    //从00:00开始到当前时间的冻结数量
    //http://120.79.246.166:5678/todayFreezing?area_id=:area_id
    @GetMapping("/todayFreezing")
    public List todayFreezing(@RequestParam String area_id) {
        log.info("area_id为"+area_id);
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "");//name必须是""
        map.put("value", "1222");//今日冻结的数值  **
        if(Objects.equals(area_id,"440000")) {//广东
            map.put("value", "120");
        }
        if(Objects.equals(area_id,"450000")) {//广西
            map.put("value", "89");
        }
        list.add(map);
        log.info(""+list);
        return list;
    }

    //今日出库数
    //从00:00开始到当前时间的出库数量
    //http://120.79.246.166:5678/todayOutbound?area_id=:area_id
    @GetMapping("/todayOutbound")
    public List todayOutbound(@RequestParam String area_id) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "");//name的值必须是""
        map.put("value", "2050");//今日出库的数值 **
        if(Objects.equals(area_id,"440000")) {//广东
            map.put("value", "650");
        }
        if(Objects.equals(area_id,"450000")) {//广西
            map.put("value", "238");
        }
        list.add(map);
        return list;
    }

    //省市实时库存情况
    //Real-timeInventory
    //http://120.79.246.166:5678/realTimeInventory
    @GetMapping("/realTimeInventory")
    public List realTimeInventory() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("x", "深圳");//key固定写法, 值为仓库名 **
        map1.put("y", (1000 + new Random().nextInt(10000)) + "");//key固定写法, 值为库存值 **
        list.add(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("x", "广州");
        map2.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("x", "佛山");
        map3.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map3);
        Map<String, String> map4 = new HashMap<>();
        map4.put("x", "长沙");
        map4.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map4);
        Map<String, String> map5 = new HashMap<>();
        map5.put("x", "汕头");
        map5.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map5);
        Map<String, String> map6 = new HashMap<>();
        map6.put("x", "湛江");
        map6.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map6);
        Map<String, String> map7 = new HashMap<>();
        map7.put("x", "北京");
        map7.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map7);
        Map<String, String> map8 = new HashMap<>();
        map8.put("x", "东莞");
        map8.put("y", (1000 + new Random().nextInt(10000)) + "");
        list.add(map8);
        return list;
    }

    //出库与入库数量
    //统计6个月的入库和出库数量
    //http://120.79.246.166:5678/inBoundAndOutBound
    @GetMapping("/inBoundAndOutBound")
    public Object inBoundAndOutBound() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("x", "2018/04/01 00:00:00");//统计的入库时间点 **
        map1.put("y", "100");//入库数 **
        map1.put("s", "1");//固定写法, 1表示入库
        list.add(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("x", "2018/04/01 00:00:00");//统计的出库时间点 **
        map2.put("y", "129");//出库数 **
        map2.put("s", "2");//固定写法, 2表示出库
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("x", "2018/05/01 00:00:00");//统计的入库时间点 **
        map3.put("y", "112");//入库数 **
        map3.put("s", "1");//固定写法, 1表示入库
        list.add(map3);
        Map<String, String> map4 = new HashMap<>();
        map4.put("x", "2018/05/01 00:00:00");//统计的出库时间点 **
        map4.put("y", "20");//出库数 **
        map4.put("s", "2");//固定写法, 2表示出库
        list.add(map4);
        Map<String, String> map5 = new HashMap<>();
        map5.put("x", "2018/06/01 00:00:00");//统计的入库时间点 **
        map5.put("y", "111");//入库数 **
        map5.put("s", "1");//固定写法, 1表示入库
        list.add(map5);
        Map<String, String> map6 = new HashMap<>();
        map6.put("x", "2018/06/01 00:00:00");//统计的出库时间点 **
        map6.put("y", "65");//出库数 **
        map6.put("s", "2");//固定写法, 2表示出库
        list.add(map6);
        Map<String, String> map7 = new HashMap<>();
        map7.put("x", "2018/07/01 00:00:00");//统计的入库时间点 **
        map7.put("y", "44");//入库数 **
        map7.put("s", "1");//固定写法, 1表示入库
        list.add(map7);
        Map<String, String> map8 = new HashMap<>();
        map8.put("x", "2018/07/01 00:00:00");//统计的出库时间点 **
        map8.put("y", "30");//出库数 **
        map8.put("s", "2");//固定写法, 2表示出库
        list.add(map8);
        Map<String, String> map9 = new HashMap<>();
        map9.put("x", "2018/08/01 00:00:00");//统计的入库时间点 **
        map9.put("y", "34");//入库数 **
        map9.put("s", "1");//固定写法, 1表示入库
        list.add(map9);
        Map<String, String> map10 = new HashMap<>();
        map10.put("x", "2018/08/01 00:00:00");//统计的出库时间点 **
        map10.put("y", "67");//出库数 **
        map10.put("s", "2");//固定写法, 2表示出库
        list.add(map10);
        Map<String, String> map11 = new HashMap<>();
        map11.put("x", "2018/09/01 00:00:00");//统计的入库时间点 **
        map11.put("y", "12");//入库数 **
        map11.put("s", "1");//固定写法, 1表示入库
        list.add(map11);
        Map<String, String> map12 = new HashMap<>();
        map12.put("x", "2018/09/01 00:00:00");//统计的出库时间点 **
        map12.put("y", "89");//出库数 **
        map12.put("s", "2");//固定写法, 2表示出库
        list.add(map12);

        return list;
    }

    //总的冻结数和可用数
    //统计所有的仓库的冻结数和可用数, 排序展示库存量前6的仓库的数据
    //http://120.79.246.166:5678/freezingAndAvailability
    @GetMapping("/freezingAndAvailability")
    public Object freezingAndAvailability() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("x", "深圳");//key固定写法, 值为仓库名
        map1.put("y", (1000 + new Random().nextInt(10000)) + "");//key固定写法, 值为库存值
        map1.put("s", "1");
        list.add(map1);
        Map<String, String> map11 = new HashMap<>();
        map11.put("x", "深圳");//key固定写法, 值为仓库名
        map11.put("y", (1000 + new Random().nextInt(10000)) + "");//key固定写法, 值为库存值
        map11.put("s", "2");
        list.add(map11);

        Map<String, String> map2 = new HashMap<>();
        map2.put("x", "广州");
        map2.put("y", (1000 + new Random().nextInt(10000)) + "");
        map2.put("s", "1");
        list.add(map2);
        Map<String, String> map21 = new HashMap<>();
        map21.put("x", "广州");
        map21.put("y", (1000 + new Random().nextInt(10000)) + "");
        map21.put("s", "2");
        list.add(map21);

        Map<String, String> map3 = new HashMap<>();
        map3.put("x", "佛山");
        map3.put("y", (1000 + new Random().nextInt(10000)) + "");
        map3.put("s", "1");
        list.add(map3);
        Map<String, String> map31 = new HashMap<>();
        map31.put("x", "佛山");
        map31.put("y", (1000 + new Random().nextInt(10000)) + "");
        map31.put("s", "2");
        list.add(map31);
        return list;
    }

    //周转率 暂时不做, datav暂时的静态数据

    //待作业数
    //http://120.79.246.166:5678/planToWork
    @GetMapping("/planToWork")
    public Object planToWork() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("x", "入库");//入库操作
        map.put("y", (10 + new Random().nextInt(10)) + "");//待入库数 **
        map.put("s", "1");//固定写法
        list.add(map);
        Map<String, String> map2 = new HashMap<>();
        map2.put("x", "出库");//出库操作
        map2.put("y", (10 + new Random().nextInt(10)) + "");//待出库数 **
        map2.put("s", "1");//固定写法
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("x", "调拨");//调拨操作
        map3.put("y", (10 + new Random().nextInt(10)) + "");//待调拨数 **
        map3.put("s", "1");//固定写法
        list.add(map3);
        Map<String, String> map4 = new HashMap<>();
        map4.put("x", "盘点");//盘点操作
        map4.put("y", (10 + new Random().nextInt(10)) + "");//待盘点数 **
        map4.put("s", "1");//固定写法
        list.add(map4);
        return list;
    }

}
