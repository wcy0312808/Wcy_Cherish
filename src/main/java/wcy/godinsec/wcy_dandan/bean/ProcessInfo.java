package wcy.godinsec.wcy_dandan.bean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auther：杨玉安 on 2017/11/8 11:38
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function： 一键加速
 */
public class ProcessInfo implements Parcelable{
    private int id;
    private boolean isFilterProcess;
    private String packageName;
    private Drawable icon;
    private String name;
    private int memorySize;
    private boolean isSystemProcess;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFilterProcess() {
        return isFilterProcess;
    }

    public void setFilterProcess(boolean filterProcess) {
        isFilterProcess = filterProcess;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public boolean isSystemProcess() {
        return isSystemProcess;
    }

    public void setSystemProcess(boolean systemProcess) {
        isSystemProcess = systemProcess;
    }

    public ProcessInfo() {
    }

    protected ProcessInfo(Parcel in) {
        id = in.readInt();
        isFilterProcess = in.readByte() != 0;
        packageName = in.readString();
        name = in.readString();
        memorySize = in.readInt();
        isSystemProcess = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isFilterProcess ? 1 : 0));
        dest.writeString(packageName);
        dest.writeString(name);
        dest.writeInt(memorySize);
        dest.writeByte((byte) (isSystemProcess ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProcessInfo> CREATOR = new Creator<ProcessInfo>() {
        @Override
        public ProcessInfo createFromParcel(Parcel in) {
            return new ProcessInfo(in);
        }

        @Override
        public ProcessInfo[] newArray(int size) {
            return new ProcessInfo[size];
        }
    };
}
