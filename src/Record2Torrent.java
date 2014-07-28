import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.jwat.common.HttpHeader;
import org.jwat.warc.WarcRecord;

import java.io.*;
import java.util.List;
import java.util.Arrays;

/**
 * Created by wdwind on 14-7-27.
 */

public class Record2Torrent {
    private String HEXhash = "";
    private String content = "";
    private final boolean seeder = false;

    Record2Torrent(WarcRecord record){
        getTorrentInfo(record);
    }

    public void getTorrentInfo(WarcRecord record){
        try{
            HttpHeader httpHeader = record.getHttpHeader();
            //String url = record.header.warcTargetUriStr;
            //System.out.println(record.header.warcTargetUriStr);

            if (httpHeader == null){
                return;
            } else {
                try{
                    if(httpHeader.getHeader("Content-Type").value.contains("application")){
                        InputStream inputStream1 = null;
                        inputStream1 = record.getPayload().getInputStream();

                        byte[] b1 = IOUtils.toByteArray(inputStream1);

                        Torrent torrent = new Torrent(b1);

                        HEXhash = torrent.getHexInfoHash();
                        content = new String(Base64.encodeBase64(b1), "US-ASCII");

                        return;
                    }
                } catch (Exception e){

                }

                try{
                    if(record.header.warcTargetUriStr.contains(".torrent")){
                        InputStream inputStream1 = null;
                        inputStream1 = record.getPayload().getInputStream();

                        byte[] b1 = IOUtils.toByteArray(inputStream1);

                        Torrent torrent = new Torrent(b1);

                        HEXhash = torrent.getHexInfoHash();
                        content = new String(Base64.encodeBase64(b1), "US-ASCII");

                        return;

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
                    }
                } catch (Exception e){

                }

                try{
                    if(record.header.warcTargetUriStr.startsWith("magnet:?")){
                        content = record.header.warcTargetUriStr;
                        String[] linkData = record.header.warcTargetUriStr.split("\\?")[1].split("&");
                        for (int i = 0; i < linkData.length; i++) {
                            String[] data = linkData[i].split("=");
                            if (data[0].equals("xt")){
                                String[] subdata = data[1].split(":");
                                HEXhash = subdata[2];
                            }
                        }
                        return;
                    }
                } catch(Exception e){

                }

            }

//            if (httpHeader == null) {
//                return ;
//            }else if(httpHeader.getHeader("Content-Disposition").value.contains("torrent")){
//                InputStream inputStream1 = null;
//                inputStream1 = record.getPayload().getInputStream();
//
//                byte[] b1 = IOUtils.toByteArray(inputStream1);
//
//                Torrent torrent = new Torrent(b1);
//
//                HEXhash = torrent.getHexInfoHash();
//                content = new String(Base64.encodeBase64(b1), "US-ASCII");
//
//            } else if(record.header.warcTargetUriStr.contains(".torrent")){
//                InputStream inputStream1 = null;
//                inputStream1 = record.getPayload().getInputStream();
//
//                byte[] b1 = IOUtils.toByteArray(inputStream1);
//
//                Torrent torrent = new Torrent(b1);
//
//                HEXhash = torrent.getHexInfoHash();
//                content = new String(Base64.encodeBase64(b1), "US-ASCII");
//
////                System.out.println("Comment: " + torrent.getComment());
////                System.out.println("Created by: " + torrent.getCreatedBy());
////                List<String> fileList = torrent.getFilenames();
////                System.out.println("File names: ");
////                for(String s : fileList)
////                    System.out.println("  " + s);
////                System.out.println("Hex info hash: " + torrent.getHexInfoHash());
////                System.out.println("Name: " + torrent.getName());
////                System.out.println("Size: " + torrent.getSize());
////                System.out.println("Tracker count: " + torrent.getTrackerCount());
//            } else if(record.header.warcTargetUriStr.startsWith("magnet:?")){
//                content = record.header.warcTargetUriStr;
//                String[] linkData = record.header.warcTargetUriStr.split("\\?")[1].split("&");
//                for (int i = 0; i < linkData.length; i++) {
//                    String[] data = linkData[i].split("=");
//                    if (data[0].equals("xt")){
//                        String[] subdata = data[1].split(":");
//                        HEXhash = subdata[2];
//                    }
//                }
//            }
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

            b1 = Base64.encodeBase64(b1);
            String printMe = new String(b1, "US-ASCII");

            System.out.println(printMe);

            Torrent torrent = new Torrent(b1);

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
            e.printStackTrace();
        }
    }

    public static void testDecode(String file){
        try{
            File f = new File(file);
            //InputStream inputStream1 = new FileInputStream(f);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line1 = br.readLine();
            String code = line1.split("\\t")[1];
            System.out.println(code);

            byte[] b1 = Base64.decodeBase64(code);

            Torrent torrent = new Torrent(b1);

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

    public static void processOutput(String file){
        try{
            //File f = new File(file);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";

            File out;
            FileOutputStream outputStream;

            while( line != null){
                line = br.readLine();
                if (line == null)
                    break;
                //System.out.println(line);
                if (line.length() == "B2DD9C19875F3BF5412EC9E1366CC74FA3F98762\t".length())
                    continue;
                if (line.equals("") || line.equals("\n"))
                    continue;

                String[] data = line.split("\\t");
                if (data.length == 1)
                    continue;

                for(int i = 1; i < data.length; i ++){
                    String code = data[i];
                    byte[] b1 = Base64.decodeBase64(code);
                    Torrent torrent = new Torrent(b1);

                    String outPath = "./out/" + torrent.getName() + "_" + i + ".torrent";
                    //String outPath = "./out/" + ".torrent";

                    System.out.println(outPath);

                    out = new File(outPath);

                    outputStream = new FileOutputStream(out);
                    torrent.save(outputStream);
                    outputStream.flush();
                    outputStream.close();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Record2Torrent.getTorrentInfo("uTorrent_3.4.2_Build_32354_[FileSick].10566990.TPB.torrent");
        //Record2Torrent.getTorrentInfo("pom.xml");

        //Record2Torrent.testDecode("part-r-00000-1");

        //Record2Torrent.processOutput("part-r-00000");
    }
}
