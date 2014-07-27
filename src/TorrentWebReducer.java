//package nl.surfsara.warcexamples.hadoop.warc;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

class TorrentWebReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text pKey, Iterable<Text> pValues, Context pContext)
            throws IOException, InterruptedException {

        List<String> torrents = new ArrayList<String>();
        String value;
        int iter = 0;
        boolean hasTorrentFile = false;

        for (Text t : pValues) {
            value = t.toString();

            if (!value.startsWith("magnet:?"))
            {
                torrents.add(value);
                hasTorrentFile = true;
            } else if(iter < 15){
                torrents.add(value);
            }

            iter ++;

            if (hasTorrentFile && iter > 10)
                break;

        }

        StringBuilder out = new StringBuilder();
        for (Object o : torrents)
        {
            out.append(o.toString());
            out.append("\t");
        }

        pContext.write(pKey, new Text(out.toString()));
    }

}