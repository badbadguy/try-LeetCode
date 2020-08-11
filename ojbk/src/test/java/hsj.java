import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author LiuRunYuan
 * @CreateDate 2020/5/11
 */
public class hsj {

    private final static String baiduUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    @Test
    public void postWithParam() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baiduUrl);
        List<NameValuePair> param = new ArrayList<NameValuePair>();

        param.add(new BasicNameValuePair("q", "Room 5101 Block 5 “Bridge 8” No. 8-10 Jian Guo Zhong Road Shanghai 200025"));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param, "utf-8");
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println("content2 = " + content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();//关闭连接
            httpClient.close();//关闭浏览器
        }

    }
}
