/**
 * Copyright 2019-2029 longxiaonan(https://github.com/longxiaonan)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhirui.lmwy.common.web.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zhirui.lmwy.common.persistence.enums.OrderEnum;
import com.zhirui.lmwy.common.web.param.QueryParam;
import com.zhirui.lmwy.common.web.service.BaseService;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author longxiaonan@163.com
 * @date 2018-11-08
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected Page setPageParam(QueryParam queryParam) {
        return setPageParam(queryParam, null, null);
    }

    public static void main(String[] args) {
        String[] a = new String[]{};
        String[] b = null;
        ArrayUtils.addAll(a, b);

        List<OrderItem> ascs = OrderItem.ascs(ArrayUtils.addAll(new String[]{}, b));
        System.out.println(ascs);

    }

    protected Page setPageParam(QueryParam queryParam, OrderEnum orderEnum, String... defaultOrderField) {
        Page page = new Page();
        page.setCurrent(queryParam.getCurrent());
        page.setSize(queryParam.getSize());
        List<OrderItem> orderItemList = Lists.newArrayList();
        orderItemList.addAll(getOrderItem(orderEnum, defaultOrderField));
        orderItemList.addAll(getOrderItem(OrderEnum.ASC, queryParam.getAscs()));
        orderItemList.addAll(getOrderItem(OrderEnum.DESC, queryParam.getDescs()));
        page.addOrder(orderItemList);
        return page;
    }

    private List<OrderItem> getOrderItem(OrderEnum orderEnum, String... orderColumn) {
        List<OrderItem> orderItems = Lists.newArrayList();
        if (orderEnum == OrderEnum.DESC) {
            List<OrderItem> orderItems1  = OrderItem.descs(ArrayUtils.addAll(new String[]{}, orderColumn));
            orderItems.addAll(orderItems1);
        } else {
            List<OrderItem> orderItems2  = OrderItem.ascs(ArrayUtils.addAll(new String[]{}, orderColumn));
            orderItems.addAll(orderItems2);
        }
        return orderItems;
    }

}
