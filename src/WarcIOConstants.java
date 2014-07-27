/**
 * Created by wdwind on 14-7-28.
 */

import org.jwat.common.UriProfile;

/**
 * Constants used in this library by the reader and writer from the Java Web
 * Archive Toolkit.
 *
 * @author mathijs.kattenberg@surfsara.nl
 */
public final class WarcIOConstants {

    public static final UriProfile URIPROFILE = UriProfile.RFC3986;
    public static final boolean BLOCKDIGESTENABLED = true;
    public static final boolean PAYLOADDIGESTENABLED = true;
    public static final int HEADERMAXSIZE = 8192;
    public static final int PAYLOADHEADERMAXSIZE = 32768;

    private WarcIOConstants() {
        // No instantiations
    }

}
