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

package com.zhirui.lmwy.common.web.controller;

import com.zhirui.lmwy.common.persistence.model.result.ResultCodeEnum;
import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * ApiResultEST API 公共控制器
 * </p>
 *
 * @author longxiaonan
 * @since 2018-11-08
 */
@Slf4j
public class ApiController {

    /**
     * <p>
     * 请求成功
     * </p>
     *
     * @param data 数据内容
     * @param <T>  对象泛型
     * @return
     */
    protected <T> ResultModel<T> ok(T data) {
        return ResultModel.ok(data);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param msg 提示内容
     * @return
     */
    protected ResultModel<Object> fail(String msg) {
        return ResultModel.error(msg);
    }

    /**
     * <p>
     * 请求失败
     * </p>
     *
     * @param apiCode 请求错误码
     * @return
     */
    protected ResultModel<Object> fail(ResultCodeEnum apiCode) {
        return ResultModel.error(apiCode);
    }

}
