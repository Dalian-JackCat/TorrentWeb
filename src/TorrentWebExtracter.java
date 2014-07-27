/**
 * Copyright 2014 SURFsara
 *
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
//package nl.surfsara.warcexamples.hadoop.warc;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.jwat.common.HttpHeader;
import org.jwat.common.Payload;
import org.jwat.warc.WarcRecord;

/**
 * Map function that from a WarcRecord extracts all links. The resulting key,
 * values: page URL, link.
 * 
 * @author mathijs.kattenberg@surfsara.nl
 */
class TorrentWebExtracter extends Mapper<LongWritable, WarcRecord, Text, Text> {
	
	private static enum Counters {
		CURRENT_RECORD, NUM_HTTP_RESPONSE_RECORDS
	}

	@Override
	public void map(LongWritable key, WarcRecord value, Context context) throws IOException, InterruptedException {
		context.setStatus(Counters.CURRENT_RECORD + ": " + key.get());

		//Record2Hashcode r1 = new Record2Hashcode();
        Record2Torrent rt = new Record2Torrent(value);
        String hex = rt.getHEXhash();

		if (!"".equals(hex) && hex != null) {
			context.getCounter(Counters.NUM_HTTP_RESPONSE_RECORDS).increment(1);
			context.write(new Text(hex), new Text(rt.getContent()));
		}
	}

}
