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
package io.atlantisframework.vortex.common.grizzly;

import io.atlantisframework.vortex.common.grizzly.GrizzlyEncoderDecoders.TupleDecoder;
import io.atlantisframework.vortex.common.grizzly.GrizzlyEncoderDecoders.TupleEncoder;
import io.atlantisframework.vortex.common.serializer.KryoSerializer;
import io.atlantisframework.vortex.common.serializer.Serializer;

/**
 * 
 * GrizzlyTupleCodecFactory
 *
 * @author Fred Feng
 * @since 2.0.1
 */
public class GrizzlyTupleCodecFactory implements TupleCodecFactory {

	private final Serializer serializer;
	
	public GrizzlyTupleCodecFactory() {
		this(new KryoSerializer());
	}

	public GrizzlyTupleCodecFactory(Serializer serializer) {
		this.serializer = serializer;
	}

	public TupleEncoder getEncoder() {
		return new TupleEncoder(serializer);
	}

	public TupleDecoder getDecoder() {
		return new TupleDecoder(serializer);
	}

	public Serializer getSerializer() {
		return serializer;
	}

}
