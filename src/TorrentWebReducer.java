//package nl.surfsara.warcexamples.hadoop.warc;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

class TorrentWebReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text pKey, Iterable<Text> pValues, Context pContext)
            throws IOException, InterruptedException {

        Map<String, Integer> hm = new HashMap<String, Integer>();
        //String content;
        int iValue;
        String value;

//        List<String> torrents = new ArrayList<String>();
//        int iter = 0;
//        boolean hasTorrentFile = false;

        for (Text t : pValues) {
            value = t.toString();

            iValue = hm.containsKey(value) ? hm.get(value) : 0;
            hm.put(value, iValue + 1);

//            if (!value.startsWith("magnet:?"))
//            {
//                torrents.add(value);
//                hasTorrentFile = true;
//            } else if(iter < 15){
//                torrents.add(value);
//            }
//
//            iter ++;
//
//            if (hasTorrentFile && iter > 10)
//                break;
        }

//        System.out.println("num: " + iter);

        StringBuilder out = new StringBuilder();
        out.append("\n");
        Iterator iterator = hm.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry pairs = (Map.Entry)iterator.next();
            out.append(pairs.getValue() + "\t" + pairs.getKey());
            out.append("\n");
        }

        pContext.write(pKey, new Text(out.toString()));
    }

}