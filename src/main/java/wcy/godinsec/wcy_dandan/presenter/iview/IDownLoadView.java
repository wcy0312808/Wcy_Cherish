package wcy.godinsec.wcy_dandan.presenter.iview;

import java.util.ArrayList;

import wcy.godinsec.wcy_dandan.network.responsebean.DownLoadEntityResponse;

/**
 * Auther：杨玉安 on 2018/3/23 17:47
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public interface IDownLoadView  {
    void getGameSuccess(ArrayList<DownLoadEntityResponse> gameList);
}
