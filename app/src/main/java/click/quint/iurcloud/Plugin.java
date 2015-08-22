package click.quint.iurcloud;

/**
 *  Plugin class for holding plugin specific data like the name, storage path and
 *  if the plugin is enabled.
 */
public class Plugin {

    private String mName, mPath;
    private boolean mEnable;

    public Plugin() {

    }

    public Plugin(String name, String path, boolean enable) {
        mName = name;
        mPath = path;
        mEnable = enable;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public void setStatus(boolean enable) {
        mEnable = enable;
    }

    public String getName() {
        return mName;
    }

    public String getPath() {
        return mPath;
    }

    public boolean getStatus() {
        return mEnable;
    }
}
