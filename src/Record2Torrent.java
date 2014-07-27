import com.turn.ttorrent.common.Torrent;
import org.apache.commons.io.IOUtils;
import org.jwat.common.HttpHeader;
import org.jwat.warc.WarcRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Arrays;

/**
 * Created by wdwind on 14-7-27.
 */

public class Record2Torrent {
    private String HEXhash = "";
    private String content = "";

    Record2Torrent(WarcRecord record){
        getTorrentInfo(record);
    }

    public void getTorrentInfo(WarcRecord record){
        try{
            HttpHeader httpHeader = record.getHttpHeader();
            String url = record.header.warcTargetUriStr;
            System.out.println(record.header.warcTargetUriStr);



            if (httpHeader == null) {
                return ;
            } else if(url.contains(".torrent")){
                InputStream inputStream1 = null;
                inputStream1 = record.getPayload().getInputStream();

                byte[] b1 = IOUtils.toByteArray(inputStream1);

                Torrent torrent = new Torrent(b1, false);

                HEXhash = torrent.getHexInfoHash();
                content = new String(b1, "UTF-8");

//                System.out.println("Comment: " + torrent.getComment());
//                System.out.println("Created by: " + torrent.getCreatedBy());
//                List<String> fileList = torrent.getFilenames();
//                System.out.println("File names: ");
//                for(String s : fileList)
//                    System.out.println("  " + s);
//                System.out.println("Hex info hash: " + torrent.getHexInfoHash());
//                System.out.println("Name: " + torrent.getName());
//                System.out.println("Size: " + torrent.getSize());
//                System.out.println("Tracker count: " + torrent.getTrackerCount());
            } else if(url.startsWith("magnet:?")){
                content = url;
                String[] linkData = url.split("\\?")[1].split("&");
                for (int i = 0; i < linkData.length; i++) {
                    String[] data = linkData[i].split("=");
                    if (data[0].equals("xt")){
                        String[] subdata = data[1].split(":");
                        HEXhash = subdata[2];
                    }
                }
            }
        } catch(Exception e){

        }
    }

    public String getHEXhash(){
        return HEXhash;
    }

    public String getContent(){
        return content;
    }

    public static void getTorrentInfo(String file){
        try{
            File f = new File(file);
            InputStream inputStream1 = new FileInputStream(f);
            byte[] b1 = IOUtils.toByteArray(inputStream1);

            //Torrent.

            Torrent torrent = new Torrent(b1, false);

            System.out.println("Comment: " + torrent.getComment());
            System.out.println("Created by: " + torrent.getCreatedBy());
            List<String> fileList = torrent.getFilenames();
            System.out.println("File names: ");
            for(String s : fileList)
                System.out.println("  " + s);
            System.out.println("Hex info hash: " + torrent.getHexInfoHash());
            System.out.println("Name: " + torrent.getName());
            System.out.println("Size: " + torrent.getSize());
            System.out.println("Tracker count: " + torrent.getTrackerCount());


        } catch (Exception e){

        }
    }

    public static void main(String[] args) {
        Record2Torrent.getTorrentInfo("uTorrent_3.4.2_Build_32354_[FileSick].10566990.TPB.torrent");
    }
}
