/**
 * Created by wdwind on 14-7-28.
 */

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.jwat.warc.WarcRecord;

/**
 * Hadoop InputFormat for regular (or compressed) warc wat and wet files. Values
 * are provides as WarcRecords from the Java Web Archive Toolkit.
 *
 * @author mathijs.kattenberg@surfsara.nl
 */
public class WarcInputFormat extends FileInputFormat<LongWritable, WarcRecord> {

    @Override
    public RecordReader<LongWritable, WarcRecord> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new WarcRecordReader();
    }

    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

}
