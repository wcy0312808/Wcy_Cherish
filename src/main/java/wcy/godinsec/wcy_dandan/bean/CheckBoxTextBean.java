package wcy.godinsec.wcy_dandan.bean;

/**
 * Auther：杨玉安 on 2017/9/2 17:31
 * E-meil：wcy0312808@163.com
 * WeChat：15110052956
 * QQ    ：837513007
 * Function：
 */
public class CheckBoxTextBean {
    private String text;
    private boolean state = false;

    public CheckBoxTextBean(String text, boolean state) {
        this.text = text;
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CheckBoxTextBean{" +
                "text='" + text + '\'' +
                ", state=" + state +
                '}';
    }
}
