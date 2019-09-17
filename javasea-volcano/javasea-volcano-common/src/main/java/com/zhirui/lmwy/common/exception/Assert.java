/**
 * Copyright 2019-2029 longxiaonan(https://github.com/longxiaonan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhirui.lmwy.common.exception;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * REST API 业务断言<br>
 * 参考：org.junit.Assert
 * </p>
 *
 * @author longxiaonan
 * @since 2018-11-08
 */
public class Assert {

    protected Assert() {
        // to do noting
    }

    /**
     * 大于O
     */
    public static void gtZero(Integer num, String msg) {
        if (num == null || num <= 0) {
            Assert.fail(msg);
        }
    }

    /**
     * 大于等于O
     */
    public static void geZero(Integer num, String msg) {
        if (num == null || num < 0) {
            Assert.fail(msg);
        }
    }

    /**
     * num1大于num2
     */
    public static void gt(Integer num1, Integer num2, String msg) {
        if (num1 <= num2) {
            Assert.fail(msg);
        }
    }

    /**
     * num1大于等于num2
     */
    public static void ge(Integer num1, Integer num2, String msg) {
        if (num1 < num2) {
            Assert.fail(msg);
        }
    }

    /**
     * obj1 eq obj2
     */
    public static void eq(Object obj1, Object obj2, String msg) {
        if (ObjectUtils.notEqual(obj1,obj2)) {
            Assert.fail(msg);
        }
    }

    public static void isTrue(boolean condition, String msg) {
        if (!condition) {
            Assert.fail(msg);
        }
    }

    public static void isFalse(boolean condition, String msg) {
        if (condition) {
            Assert.fail(msg);
        }
    }

    public static void isNull(String msg, Object... conditions) {
        if (!ObjectUtils.anyNotNull(conditions)) {
            Assert.fail(msg);
        }
    }

    public static void notNull(String msg, Object... conditions) {
        if (ObjectUtils.anyNotNull(conditions)) {
            Assert.fail(msg);
        }
    }

    /**
     * <p>
     * 失败结果
     * </p>
     *
     * @param msg 异常错误码
     */
    public static void fail(String msg) {
        Exceptions.throwParamException(msg);
    }

    public static void fail(boolean condition, String msg) {
        if (condition) {
            Assert.fail(msg);
        }
    }

    public static void notEmpty(Object[] array, String msg) {
        if (ObjectUtils.anyNotNull(array)) {
            Assert.fail(msg);
        }
    }

    public static void noNullElements(Object[] array, String msg) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    Assert.fail(msg);
                }
            }
        }
    }

    public static void notEmpty(Collection<?> collection, String msg) {
        if (CollectionUtils.isNotEmpty(collection)) {
            Assert.fail(msg);
        }
    }

    public static void notEmpty(Map<?, ?> map, String msg) {
        if (ObjectUtils.anyNotNull(map)) {
            Assert.fail(msg);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, String msg) {
        Assert.notNull(msg, type);
        if (!type.isInstance(obj)) {
            Assert.fail(msg);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String msg) {
        Assert.notNull(msg, superType);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            Assert.fail(msg);
        }
    }

}
