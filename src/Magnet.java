//import com.turn.ttorrent.common.Torrent;
//
//import java.io.IOException;
//
///**
// * Created by wdwind on 14-7-27.
// */
//
//public class Magnet{
//    private String magnetLink;
//
//    Magnet(String magnetLink) throws IOException {
//        if (magnetLink.startsWith("magnet:?")) {
//            String[] linkData = magnetLink.split("\\?")[1].split("&");
//
//            //torrent = new Torrent();
//            for (int i = 0; i < linkData.length; i++) {
//                String[] data = linkData[i].split("=");
//                switch (data[0]) {
//                    case "dn":
//                        linkData[i] = StringUtil.removeHex(StringUtil.spaceFix(data[1]));
//                        torrent.setDisplayName(linkData[1]);
//                        break;
//
//                    case "tr":
//                        linkData[i] = StringUtil.removeHex(data[1]);
//                        Tracker tracker = Manager.getTrackerManager().addTorrent(torrent, new Tracker(linkData[i]));
//                        torrent.addTracker(tracker);
//                        break;
//
//                    case "xt":
//                        String[] subdata = data[1].split(":");
//                        if (subdata.length < 3) {
//                            System.err.println("XT from MagnetLink is incomplete");
//                            throw new IOException("XT from MagnetLink is incomplete");
//                        } else if (!subdata[0].equals("urn")) {
//                            System.err.println("[XT] Expected a URN at position 0");
//                            throw new IOException("XT from MagnetLink is incomplete");
//                        } else if (!subdata[1].equals("btih")) {
//                            System.err.println("[XT] Unsupported Hashing: " + subdata[1]);
//                            throw new IOException("XT from MagnetLink is incomplete");
//                        } else if (subdata[2].length() != 40) {
//                            System.err.println("[XT] Invalid Hash length: " + subdata[2].length());
//                            throw new IOException("XT from MagnetLink is incomplete");
//                        } else {
//                            byte[] hash = new byte[20];
//                            for (int j = 0; j < subdata[2].length() / 2; j++) {
//                                hash[j] = (byte) Integer.parseInt(subdata[2].substring(j * 2, j * 2 + 2), 16);
//                            }
//                            torrent.setHash(hash);
//                        }
//                        break;
//
//                    default:
//                        System.err.println("Unhandled Magnet Data: " + linkData[i]);
//                }
//                downloadable = succeed;
//            }
//            if(!torrent.hasHash()) {
//                System.err.println("Magnet link has no hash");
//                downloadable = false;
//            } else if(!torrent.hasTracker()) {
//                System.err.println("Manget link has no tracker");
//                downloadable = false;
//            }
//        } else {
//            downloadable = false;
//        }
//    }
//
//}
