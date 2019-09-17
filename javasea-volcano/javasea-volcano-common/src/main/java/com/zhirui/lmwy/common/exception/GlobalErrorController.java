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

import com.zhirui.lmwy.common.persistence.model.result.ResultCodeEnum;
import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 全局Error/404处理，发生404的时候通过json返回到前端。如果不添加改controller会导致跳转到错误页面。
 * @author longxiaonan
 * @date 2018-11-08
 */
@ApiIgnore
@RestController
@Slf4j
public class GlobalErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ResultModel handleError(){
        log.error("404 NOT FOUND");
        return ResultModel.error(ResultCodeEnum.NOT_FOUND, ResultCodeEnum.NOT_FOUND.getMsg());
    }

    @Override
    public String getErrorPath() {
        log.error("errorPath....");
        return ERROR_PATH;
    }
}
