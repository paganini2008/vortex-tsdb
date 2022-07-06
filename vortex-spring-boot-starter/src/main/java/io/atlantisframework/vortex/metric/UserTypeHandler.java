/**
* Copyright 2017-2022 Fred Feng (paganini.fy@gmail.com)

* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.atlantisframework.vortex.metric;

import io.atlantisframework.vortex.common.Tuple;
import io.atlantisframework.vortex.metric.api.UserMetric;

/**
 * 
 * UserTypeHandler
 * 
 * @author Fred Feng
 *
 * @since 2.0.1
 */
public interface UserTypeHandler<V> {

	String getDataTypeName();

	UserMetric<V> convertAsMetric(String identifier, String metric, long timestamp, Tuple tuple);

	UserMetric<V> convertAsMetric(String identifier, String metric, long timestamp, UserMetric<V> metricUnit);

	Tuple convertAsTuple(String topic, String identifier, String metric, long timestamp, UserMetric<V> metricUnit);

}
