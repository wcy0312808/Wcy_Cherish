package wcy.godinsec.wcy_dandan.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Auther：杨玉安 on 2017/11/23 14:16
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DataChangebean implements Serializable,Parcelable{
    private int brushCount;
    private String startTime;
    private String endTime;
    private List<Date> brushTiomeDates;
    private String today;

    public DataChangebean() {
    }

    protected DataChangebean(Parcel in) {
        brushCount = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        today = in.readString();
    }

    public static final Creator<DataChangebean> CREATOR = new Creator<DataChangebean>() {
        @Override
        public DataChangebean createFromParcel(Parcel in) {
            return new DataChangebean(in);
        }

        @Override
        public DataChangebean[] newArray(int size) {
            return new DataChangebean[size];
        }
    };

    public int getBrushCount() {
        return brushCount;
    }

    public void setBrushCount(int brushCount) {
        this.brushCount = brushCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Date> getBrushTiomeDates() {
        return brushTiomeDates;
    }

    public void setBrushTiomeDates(List<Date> brushTiomeDates) {
        this.brushTiomeDates = brushTiomeDates;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    @Override
    public String toString() {
        return "DataChangebean{" +
                "brushCount=" + brushCount +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", brushTiomeDates=" + brushTiomeDates +
                ", today='" + today + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(brushCount);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(today);
    }
}
