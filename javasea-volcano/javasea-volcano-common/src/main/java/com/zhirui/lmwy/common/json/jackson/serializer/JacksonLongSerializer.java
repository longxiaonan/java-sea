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

package com.zhirui.lmwy.common.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>
 *     Jackson Long 反序列化器, 有时候存在精度丢失的情况
 * </p>
 * @author longxiaonan
 * @date 2018-11-08
 */
public class JacksonLongSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long str, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if (str != null){
            String dateString = str+"";
            jsonGenerator.writeString(dateString);
        }
    }

//    @Override
//    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
//        if (date != null){
//            String dateString = LocalDateTimeUtils.dateFormat(date);
//            jsonGenerator.writeString(dateString);
//        }
//    }
}
