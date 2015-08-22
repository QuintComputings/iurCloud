package click.quint.iurcloud;

/**
 *  Device class for holding device specific data like the name, type, mac address and
 *  the current status (if device is enabled and gets notifications) of each device
 */
public class Device {

    private String mName, mType, mMac;
    private boolean mEnable;

    public Device() {

    }

    public Device(String name, String type, String mac, boolean enable) {
        mName = name;
        mType = type;
        mMac = mac;
        mEnable = enable;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setMac(String mac) {
        mMac = mac;
    }

    public void setStatus(boolean enable) {
        mEnable = enable;
    }

    public String getName() {
        return mName;
    }

    public String getType() {
        return mType;
    }

    public String getMac() {
        return mMac;
    }

    public boolean getStatus() {
        return mEnable;
    }
}
