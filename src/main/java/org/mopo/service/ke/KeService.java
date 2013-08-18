package org.mopo.service.ke;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mopo.model.App;
import static org.mopo.utils.StringUtils.isEmpty;

public class KeService {

    private static final Logger LOGGER = Logger.getLogger(KeService.class
            .getName());

    /**
     * Gets apps from open page.
     * 
     * @param username
     *            website username.
     * @param password
     *            website password.
     * @param channelId
     *            id of ke's channel
     * @return apps list
     */
    public final List<App> getAppsFormOpenPage(final String username,
            final String password, final String channelId) {
        Map<String, String> cookies = getLoginCookies(username, password);
        Document document;
        List<App> apps = null;
        try {
            document = getOpenPageDoc(cookies, App.MOPO_KE_OPEN_PAGE, channelId);
            apps = parseOpenPageDoc(document);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    private final Document getOpenPageDoc(final Map<String, String> cookies,
            final String website, final String channelId) throws IOException {
        Document doc = null;
        Connection openPageConn;
        if (isEmpty(channelId)) {
            openPageConn = Jsoup.connect(website);
        } else {
            openPageConn = Jsoup.connect(website + "/tcid/" + channelId);
        }
        openPageConn.method(Method.POST);
        openPageConn.cookies(cookies);
        openPageConn.followRedirects(false);
        Response openPageResp = openPageConn.execute();
        doc = openPageResp.parse();
        return doc;
    }

    private final List<App> parseOpenPageDoc(Document document) {
        System.out.println(document.toString());
        return null;
    }

    /**
     * Get website login cookies.
     * 
     * @param username
     *            the specified website login name
     * @param password
     *            the specified website login password
     * @return login cookies
     */
    private Map<String, String> getLoginCookies(final String username,
            final String password) {
        Map<String, String> cookies = null;
        // 先从缓存中获取

        Map<String, String> params = new HashMap<String, String>();
        params.put("inputUsername", username);
        params.put("inputPassword", password);
        Connection conn = Jsoup.connect(App.MOPO_KE_LOGIN);
        conn.method(Method.POST);
        conn.data(params);
        conn.followRedirects(true);
        Response response;
        try {
            response = conn.execute();
            cookies = response.cookies();
            // datastoreService.addToCache(username+password, cookies);
        } catch (Exception e) {
            LOGGER.severe(username + " get cookies error :" + e.getMessage());
        }

        LOGGER.info("user " + username + " get cookies" + cookies);
        return cookies;
    }

    /**
     * 登陆冒泡客网站.
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param channelId
     *            渠道id
     * @return List<App> 应用列表
     * @throws Exception
     */
   /* private List<App> login(final String username, final String password,
            final String channelId) throws Exception {
        List<App> apps = null;
        Map<String, String> cookies = getLoginCookies(username, password);
        if (cookies == null) {
            return null;
        }
        Document doc = getDocByCookies(cookies, channelId);
        String title = doc.title();
        // 鐧诲綍涓嶆垚鍔燂紝鍐嶇櫥闄嗕竴娆�
        if (title.isEmpty()) {
            // 涔嬪墠缂撳瓨鐨刢ookies宸茬粡澶辨晥锛屽垹闄ookie缂撳瓨
            logger.warning(username + "first login is error!!");
            // datastoreService.removeCache(username + password);
            cookies = getLoginCookies(username, password);
            doc = getDocByCookies(cookies, channelId);
            title = doc.title();
            logger.info(username + " get cookie second time");
        }
        if (!title.isEmpty()) {
            try {
                logger.info(username + " login success!!");
                apps = parseAppsByDoc(doc, channelId);
            } catch (Exception e) {
                e.printStackTrace();
                logger.log(Level.SEVERE, "parse html error " + e.getMessage());
            }

        }
        return apps;

    }

    *//**
     * 从cookie中登陆网站
     * 
     * @param cookies
     *            网站cookeis
     * @param channelId
     *            渠道id
     * @return doc
     * @throws Exception
     *             exception
     *//*
    private Document getDocByCookies(Map<String, String> cookies,
            final String channelId) throws Exception {
        Document doc = null;
        Connection favConn;
        if (isEmpty(channelId)) {
            favConn = Jsoup.connect(Config.FAV_BASE_PATH);
        } else {
            favConn = Jsoup
                    .connect(Config.FAV_BASE_PATH + "/tcid/" + channelId);
        }
        favConn.method(Method.POST);
        favConn.cookies(cookies);
        favConn.followRedirects(false);
        Response favResp = favConn.execute();
        doc = favResp.parse();
        return doc;
    }

    *//**
     * 鏍规嵁doc瑙ｆ瀽鍑哄簲鐢ㄤ俊鎭�
     * 
     * @param doc
     *            鎸囧畾鐨刣oc
     * @param channelId
     *            娓犻亾id
     * @return apps 搴旂敤淇℃伅
     *//*
    private List<App> parseAppsByDoc(final Document doc, String channelId) {
        List<App> apps = null;
        if (doc != null) {
            if (isEmpty(channelId)) {
                Elements options = doc.select("select>option");
                int i = 0;
                // 鎷垮埌榛樿channelid
                for (Element element : options) {
                    if (i == 1) {
                        channelId = element.attr("value");
                    }
                    i++;
                }
                logger.info("channelId: " + channelId);
            }
            Elements lis = doc.select("[class^=li-page]");
            apps = new ArrayList<App>();
            for (Iterator<Element> it = lis.iterator(); it.hasNext();) {
                App app = new App();
                // 鑾峰彇id,name
                Element element = it.next();
                String id = element.attr("id").substring(3);
                Element titleElement = element.select(".app-title").first();
                String title = titleElement.text();
                // 鑾峰彇鍥剧墖
                Element picElement = element.select(".pic").first();
                String pic = picElement.attr("value");
                // System.out.println("pic:" + pic);
                // 鑾峰彇涓嬭浇鍦板潃
                Element divElement = element.select("[class$=operation]")
                        .first();
                Element apkElement = divElement.select("[href]").first();
                String apk = apkElement.attr("href");
                app.setName(title);
                app.setIcon(pic);
                app.setApk(apk);
                app.setUrl(getDetailUrl(channelId, id));
                logger.info("app " + app);
                apps.add(app);
            }

        }
        return apps;
    }*/
}
