package wcy.godinsec.wcy_dandan.network.responsebean;

/**
 * Created by Seeker on 2017/1/16.
 *
 * 网络请求返回结果
 */

public class BaseResponse<T> {

    private Head head;

    private T body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static final class Head {

        private String statuscode;

        private String statusmsg;

        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public String getStatusmsg() {
            return statusmsg;
        }

        public void setStatusmsg(String statusmsg) {
            this.statusmsg = statusmsg;
        }

        @Override
        public String toString() {
            return "Head{" +
                    "statuscode='" + statuscode + '\'' +
                    ", statusmsg='" + statusmsg + '\'' +
                    '}';
        }
    }

}
