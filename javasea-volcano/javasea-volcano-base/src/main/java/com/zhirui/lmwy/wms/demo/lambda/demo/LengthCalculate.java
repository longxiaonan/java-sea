package com.zhirui.lmwy.wms.demo.lambda.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ClassName LengthCalculate
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/4/28 0028 20:17
 */
@Slf4j
public class LengthCalculate {

    public static void main(String[] args) {
        String requirementStr = "{\"8f7022ac6e714ddd8e7ce9c7cf32464d\":{\"lengthen\":4,\"remark\":\"第一次开料长度为，右板体+左板体长度再加4毫米\"},\"223fc4ba2a60422d9c62dd8823c93aae\":{\"angle\":79,\"remark\":\"79度开料\"},\"a2433dfb8928473bbfa7dd656c6693cd\":{\"remark\":\"角度阴角板体左右冲孔，阴角端板冲压模具开料，冲孔，冲长孔\"},\"fece78902b8a4a40908ef423ee53924d\":{\"remark\":\"把角度阴角板体左右，端板1，端板2，按图纸要求焊接成产品\"}}";
        JSONObject requirement = JSONObject.parseObject(requirementStr);
        Map<String, Map<String, String>> map = JSONObject.parseObject(requirementStr, Map.class);

        //从指定工序中获取加长的长度
        Optional<Integer> lengthen = getLengthen(map, "8f7022ac6e714ddd8e7ce9c7cf32464d", "lengthen");

        //计算开料长度
        //1.易拆阴角, ELMXXXXN-W, TODO: ELM1015030N-W（W）这种怎么办？？？
        String elm1 = "ELM1010010N-W";  //{110=1}, 开料长度计算方式为010 * 10 + 10
        String elm2 = "ELM1013100N-W";  //{1010=1}, 开料长度计算方式为100 * 10 + 10
        String elm3 = "ELM1013120N-W";  //{1210=1}, 开料长度计算方式为120 * 10 + 10
        lengthen = Optional.of(4);
        Map<Integer, Long> lengthNumberMap = calculateLengthFirst(lengthen, elm2);
        System.out.println(elm2 + " >>> " + lengthNumberMap);

        //2. 外转角
        String ewz1 = "EWZ1210[040+040]";  //{530=2}, 第一块开料长度计算方式为(12 + 40) * 10 + 10, 第二块开料长度计算方式为(12 + 40) * 10 + 10
        String ewz2 = "EWZ1410[040+060]";  //{550=1, 750=1}, 第一块开料长度计算方式为(14 + 40) * 10 + 10, 第二块开料长度计算方式为(14 + 60) * 10 + 10
        lengthen = Optional.of(10);
        Map<Integer, Long> lengthNumberMapEwz1 = calculateLengthFirst(lengthen, ewz1);
        System.out.println(ewz1 +" >>> "+lengthNumberMapEwz1);
        Map<Integer, Long> lengthNumberMapEwz2 = calculateLengthFirst(lengthen, ewz2);
        System.out.println(ewz2 +" >>> "+lengthNumberMapEwz2);

        //3. 内转角
        String enz1 = "ENZ1013[040+040]";  //{410=2}, 第一块开料长度计算方式为40 * 10 + 10, 第二块开料长度计算方式为40 * 10 + 10
        String enz2 = "ENZ1013[040+060]";  //{610=1, 410=1}, 第一块开料长度计算方式为40 * 10 + 10, 第二块开料长度计算方式为60 * 10 + 10
        lengthen = Optional.of(10);
        Map<Integer, Long> lengthNumberMapEnz = calculateLengthFirst(lengthen, enz1);
        System.out.println(enz1 + ">>>" + lengthNumberMapEnz);
        Map<Integer, Long> lengthNumberMapEnz2 = calculateLengthFirst(lengthen, enz2);
        System.out.println(enz2 + ">>>" + lengthNumberMapEnz2);

        //4. 单接头龙骨
        String llmd = "LLM10060D";  //{615=1}, 开料长度计算方式为60 * 10 + 15
        String llmd2 = "LLM20080D"; //{815=1}, 开料长度计算方式为80 * 10 + 15
        lengthen = Optional.of(15);
        Map<Integer, Long> lengthNumberMapLLM = calculateLengthFirst(lengthen, llmd2);
        System.out.println(llmd2 + " >>> " + lengthNumberMapLLM);

//        5. 双接头龙骨
        String llms = "LLM10060S";  //{630=1}, 开料长度计算方式为60 * 10 + 30
        String llms2 = "LLM20080S"; //{830=1}, 开料长度计算方式为80 * 10 + 30
        lengthen = Optional.of(30);
        Map<Integer, Long> lengthNumberMapLlm1 = calculateLengthFirst(lengthen, llms2);
        System.out.println(llms2 + ">>>"+ lengthNumberMapLlm1);

    }

    public static Optional<Integer> getLengthen(Map<String, Map<String, String>> requirement, String workstageId, String key) {
        Map<String, String> map = (Map<String, String>) MapUtils.getMap(requirement, workstageId);
        Optional<Integer> lengthen = Optional.ofNullable(map).filter(m -> m != null && m.size() > 0).map(a -> MapUtils.getString(a, key)).map(Integer::valueOf);
        return lengthen;
    }

    /**
     * @Description 计算第一次开料长度
     * @Author longxiaonan@163.com
     * @Date 10:36 2019/4/29 0029
     * @param lengthenValueOpt 开料时需要加长的参数值
     * @param spec 规格编码
     * @return map, key为开料的长度，value为开料的板体数
     **/
    public static Map<Integer, Long> calculateLengthFirst(Optional<Integer> lengthenValueOpt, String spec) {
        //获取板体类型
        Optional<BoardType> type = getType(spec);
        Map<Integer, Long> result = Maps.newHashMap();
        if (type.isPresent()) {  //匹配到需要计算的板体
            if (lengthenValueOpt.isPresent()) {
                Integer lengthenValue = lengthenValueOpt.get();
                switch (type.get()) {
                    case ELM:
                        result = calculateELM(spec, lengthenValue);
                        break;
                    case EWZ:
                        result = calculateEWZ(spec, lengthenValue);
                        break;
                    case ENZ:
                        result = calculateENZ(spec, lengthenValue);
                        break;
                    case LLMD:
                        result = calculateLLM(spec, lengthenValue);
                        break;
                    case LLMS:
                        result = calculateLLM(spec, lengthenValue);
                        break;
                    default:
                        throw new RuntimeException("匹配到是【角度模板】板体，但是未设置计算开料算法！");
                }
            } else {
                throw new RuntimeException("匹配到是【角度模板】板体，但是未设置开料工艺长度要求！");
            }
        }
        return result;
    }

    //获取中括号内数值之和，如EWZ1210[040+060], 返回值为100
    private static HashMap<Integer, Long> getBetweenLength(String spec) {
        Integer sum = null;
        try {
            final String s = StringUtils.substringBetween(spec, "[", "]");
            sum = 0;
            if (StringUtils.isNotBlank(s)) {
                Iterable<String> split1 = Splitter.on("+").split(s);
                Iterator<String> iterator = split1.iterator();
                System.out.println(iterator);
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    System.out.println(next);
                    sum += Integer.valueOf(next);
                }
                sum *= 10;
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(spec + "中括号内非正常数值，无法计算！", e);
        }
        HashMap<Integer, Long> result = Maps.newHashMap();
        result.put(sum, 1L);
        return result;
    }

    private static HashMap<Integer, Long> calculateLLM(String spec, Integer lengthValue) {
        String length = null;
        try {
            length = StringUtils.substring(spec, 6, 8);
            Integer len = Integer.valueOf(length);
            HashMap<Integer, Long> result = Maps.newHashMap();
            result.put(lengthValue + len * 10, 1L);
            return result;
        } catch (NumberFormatException e) {
            throw new RuntimeException(spec + "截取规格编码中的数值[" + length + "]无法计算出长度！", e);
        }

    }

    private static Map<Integer, Long> calculateEWZ(String spec, Integer lenthenValue) {
        Map<Integer, Long> collect = null;
        try {
            final String s = StringUtils.substringBetween(spec, "[", "]");
            final String len = StringUtils.substring(spec, 3, 5);
            if (StringUtils.isNotBlank(s)) {
                List<String> lengthsBeforeList = Splitter.on("+").splitToList(s);
                List<Integer> lenthsAfterList = lengthsBeforeList.stream()
                        .map(a -> Integer.valueOf(a) + Integer.valueOf(len))
                        .map(b -> b * 10 + lenthenValue).collect(Collectors.toList());
                collect = lenthsAfterList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(spec + "中括号内非正常数值，无法计算！", e);
        }
        return collect;
    }

    private static Map<Integer, Long> calculateENZ(String spec, Integer lenthenValue) {
        Integer sum = null;
        Map<Integer, Long> collect = null;
        try {
            final String s = StringUtils.substringBetween(spec, "[", "]");
            if (StringUtils.isNotBlank(s)) {
                List<String> lengthsBeforeList = Splitter.on("+").splitToList(s);
                List<Integer> lenthsAfterList = lengthsBeforeList.stream().map(l -> Integer.valueOf(l)).map(len -> len * 10 + lenthenValue).collect(Collectors.toList());
                collect = lenthsAfterList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(spec + "中括号内非正常数值，无法计算！", e);
        }
        return collect;
    }

    /**
     * @Description String elm1 = "ELM1010010N-W";  //{530=2}, 开料长度计算方式为010 * 10 + 4
     *         String elm2 = "ELM1013100N-W";  //{550=1, 750=1}, 开料长度计算方式为100 * 10 + 4
     *         String elm3 = "ELM1013120N-W";  //开料长度计算方式为120 * 10 + 4
     * @Author longxiaonan@163.com
     * @Date 10:04 2019/4/29 0029
     **/
    private static Map<Integer, Long> calculateELM(String spec, Integer lenthenValue) {
        String length = null;
        try {
            length = StringUtils.substring(spec, 7, 10);
            Integer len = Integer.valueOf(length);
            HashMap<Integer, Long> result = Maps.newHashMap();
            result.put(lenthenValue + len * 10, 1L);
            return result;
        } catch (NumberFormatException e) {
            throw new RuntimeException(spec + "截取规格编码中的数值[" + length + "]无法计算出长度！", e);
        }

    }


    //获取板体类型
    public static Optional<BoardType> getType(String spec) {
        if (StringUtils.startsWith(spec, BoardType.ELM.prefix) && StringUtils.endsWith(spec, BoardType.ELM.suffix)) {
            return Optional.of(BoardType.ELM);
        }
        if (StringUtils.startsWith(spec, BoardType.EWZ.prefix) && StringUtils.endsWith(spec, BoardType.EWZ.suffix)) {
            return Optional.of(BoardType.EWZ);
        }
        if (StringUtils.startsWith(spec, BoardType.ENZ.prefix) && StringUtils.endsWith(spec, BoardType.ENZ.suffix)) {
            return Optional.of(BoardType.ENZ);
        }
        if (StringUtils.startsWith(spec, BoardType.LLMD.prefix) && StringUtils.endsWith(spec, BoardType.LLMD.suffix)) {
            return Optional.of(BoardType.LLMD);
        }
        if (StringUtils.startsWith(spec, BoardType.LLMS.prefix) && StringUtils.endsWith(spec, BoardType.LLMS.suffix)) {
            return Optional.of(BoardType.LLMS);
        }
        return Optional.empty();
    }

    enum BoardType {

        ELM("异拆阴角", "ELM", "N-W"),
        EWZ("外转角阴角", "EWZ", "]"),
        ENZ("内转角阴角", "ENZ", "]"),
        LLMD("单接头龙骨","LLM", "D"),
        LLMS("双接头龙骨", "LLM", "S"),
        ZLM("早拆头", "ZLM", "");
        private String name; //板体名称
        private String prefix;
        private String suffix;  //后缀



        BoardType(String name, String prefix, String suffix) {
            this.name = name;
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }
}
