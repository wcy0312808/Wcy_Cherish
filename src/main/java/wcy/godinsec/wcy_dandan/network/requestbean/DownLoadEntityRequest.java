package wcy.godinsec.wcy_dandan.network.requestbean;

/**
 * Auther：杨玉安 on 2018/4/28 11:51
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class DownLoadEntityRequest {

    /**
     * app_version : 1.4.0
     * channel : godinsec
     * game_type : 0
     * imei : 867686020174650
     * offset : 0
     * position : 3
     */

    private String app_version;
    private String channel;
    private int game_type;
    private String imei;
    private int offset;
    private int position;

    public DownLoadEntityRequest() {
        this.app_version ="1.4.0";
        this.channel = "godinsec";
        this.game_type = 0;
        this.imei = "867686020174650";
        this.offset = 0;
        this.position = 3;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
