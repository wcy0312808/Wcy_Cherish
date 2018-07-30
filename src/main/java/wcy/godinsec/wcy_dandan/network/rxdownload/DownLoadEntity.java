package wcy.godinsec.wcy_dandan.network.rxdownload;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Auther：杨玉安 on 2017/9/30 17:40
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadEntity  implements Parcelable{
    private int app_Id;
    private String app_Name;             /*文件名称*/
    private String save_Path;            /*文件的存储位置*/
    private String app_Link;             /*下载文件的url*/
    private long down_App_Size;         /*文件总长度    也就是文件下载的进度*/
    private long down_Progress;         /*下载长度*/
    private int down_State;              /*下载状态 0 开始下载  1下载中 2 暂停 3 停止  4错误  5完成*/
    private int install_Status;         /*当前apk的状态  0未下载  1未安装 2 正在安装  3已经安装*/
//    private DownLoadListener mDownLoadListener;
    private String app_Md5;
    private int install_end_Time;
    private int install_start_Time;
    private String package_Name;
    private int app_sort;


    protected DownLoadEntity(Parcel in) {
        app_Id = in.readInt();
        app_Name = in.readString();
        save_Path = in.readString();
        app_Link = in.readString();
        down_App_Size = in.readLong();
        down_Progress = in.readLong();
        down_State = in.readInt();
        install_Status = in.readInt();
        app_Md5 = in.readString();
        install_end_Time = in.readInt();
        install_start_Time = in.readInt();
        package_Name = in.readString();
        app_sort = in.readInt();
    }

    public static final Creator<DownLoadEntity> CREATOR = new Creator<DownLoadEntity>() {
        @Override
        public DownLoadEntity createFromParcel(Parcel in) {
            return new DownLoadEntity(in);
        }

        @Override
        public DownLoadEntity[] newArray(int size) {
            return new DownLoadEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(app_Id);
        dest.writeString(app_Name);
        dest.writeString(save_Path);
        dest.writeString(app_Link);
        dest.writeLong(down_App_Size);
        dest.writeLong(down_Progress);
        dest.writeInt(down_State);
        dest.writeInt(install_Status);
        dest.writeString(app_Md5);
        dest.writeInt(install_end_Time);
        dest.writeInt(install_start_Time);
        dest.writeString(package_Name);
        dest.writeInt(app_sort);
    }

    public DownLoadEntity() {
    }

    public int getApp_Id() {
        return app_Id;
    }

    public void setApp_Id(int app_Id) {
        this.app_Id = app_Id;
    }

    public String getApp_Name() {
        return app_Name;
    }

    public void setApp_Name(String app_Name) {
        this.app_Name = app_Name;
    }

    public String getSave_Path() {
        return save_Path;
    }

    public void setSave_Path(String save_Path) {
        this.save_Path = save_Path;
    }

    public String getApp_Link() {
        return app_Link;
    }

    public void setApp_Link(String app_Link) {
        this.app_Link = app_Link;
    }

    public long getDown_App_Size() {
        return down_App_Size;
    }

    public void setDown_App_Size(long down_App_Size) {
        this.down_App_Size = down_App_Size;
    }

    public long getDown_Progress() {
        return down_Progress;
    }

    public void setDown_Progress(long down_Progress) {
        this.down_Progress = down_Progress;
    }

    public int getDown_State() {
        return down_State;
    }

    public void setDown_State(int down_State) {
        this.down_State = down_State;
    }

    public int getInstall_Status() {
        return install_Status;
    }

    public void setInstall_Status(int install_Status) {
        this.install_Status = install_Status;
    }

//    public DownLoadListener getDownLoadListener() {
//        return mDownLoadListener;
//    }
//
//    public void setDownLoadListener(DownLoadListener downLoadListener) {
//        mDownLoadListener = downLoadListener;
//    }

    public String getApp_Md5() {
        return app_Md5;
    }

    public void setApp_Md5(String app_Md5) {
        this.app_Md5 = app_Md5;
    }

    public int getInstall_end_Time() {
        return install_end_Time;
    }

    public void setInstall_end_Time(int install_end_Time) {
        this.install_end_Time = install_end_Time;
    }

    public int getInstall_start_Time() {
        return install_start_Time;
    }

    public void setInstall_start_Time(int install_start_Time) {
        this.install_start_Time = install_start_Time;
    }

    public String getPackage_Name() {
        return package_Name;
    }

    public void setPackage_Name(String package_Name) {
        this.package_Name = package_Name;
    }

    public int getApp_sort() {
        return app_sort;
    }

    public void setApp_sort(int app_sort) {
        this.app_sort = app_sort;
    }

    @Override
    public String toString() {
        return "DownLoadEntity{" +
                "app_Id=" + app_Id +
                ", app_Name='" + app_Name + '\'' +
                ", save_Path='" + save_Path + '\'' +
                ", app_Link='" + app_Link + '\'' +
                ", down_App_Size=" + down_App_Size +
                ", down_Progress=" + down_Progress +
                ", down_State=" + down_State +
                ", install_Status=" + install_Status +
                ", app_Md5='" + app_Md5 + '\'' +
                ", install_end_Time=" + install_end_Time +
                ", install_start_Time=" + install_start_Time +
                ", package_Name='" + package_Name + '\'' +
                ", app_sort=" + app_sort +
                '}';
    }
}
