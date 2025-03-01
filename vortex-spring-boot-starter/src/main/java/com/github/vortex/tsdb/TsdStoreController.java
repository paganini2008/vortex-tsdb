/*
 * Copyright 2017-2025 Fred Feng (paganini.fy@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.vortex.tsdb;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.doodler.common.ApiResult;
import com.github.doodler.common.transmitter.NioClient;
import com.github.doodler.common.transmitter.Packet;

/**
 * 
 * @Description: TsdStoreController
 * @Author: Fred Feng
 * @Date: 02/01/2025
 * @Version 1.0.0
 */
@RequestMapping("/tsd")
@RestController
public class TsdStoreController {

    @Autowired
    private TsdStoreService tsdStoreService;

    @Autowired
    private NioClient nioClient;

    @PostMapping("/push")
    public ApiResult<Packet> push(@RequestParam("t") String dataType,
            @RequestParam("c") String category, @RequestParam("d") String dimension,
            @RequestParam("v") String value) {
        Packet packet = Packet
                .wrap(Map.of("dataType", dataType, "category", category, "dimension", dimension));
        packet.setObject(value);
        nioClient.send(packet);
        return ApiResult.ok(packet);
    }

    @PostMapping("/test")
    public ApiResult<Packet> test(@RequestParam("t") String dataType,
            @RequestParam("c") String category, @RequestParam("d") String dimension) {
        Packet packet = Packet
                .wrap(Map.of("dataType", dataType, "category", category, "dimension", dimension));
        packet.setObject(RandomUtils.nextLong(1, 10000));
        nioClient.send(packet);
        return ApiResult.ok(packet);
    }

    @GetMapping("/retrieve")
    public ApiResult<TsdQueryVo> retrieve(@RequestParam("t") String dataType,
            @RequestParam("c") String category, @RequestParam("d") String dimension,
            @RequestParam(name = "z", required = false) String zone) {
        Map<String, Object> results = Collections.emptyMap();
        TimeZone timeZone = StringUtils.isNotBlank(zone) ? TimeZone.getTimeZone(ZoneId.of(zone))
                : TimeZone.getTimeZone("Australia/Sydney");
        switch (dataType) {
            case "decimal":
                results = tsdStoreService.retrieveWithDecimalType(category, dimension, timeZone);
                break;
            case "long":
                results = tsdStoreService.retrieveWithLongType(category, dimension, timeZone);
                break;
            case "double":
                results = tsdStoreService.retrieveWithDoubleType(category, dimension, timeZone);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + dataType);
        }
        TsdQueryVo queryVo = new TsdQueryVo();
        queryVo.setDataType(dataType);
        queryVo.setCategory(category);
        queryVo.setDimension(dimension);
        queryVo.setData(results);
        return ApiResult.ok(queryVo);
    }

}
