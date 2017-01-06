package com.rssreader.mrlu.myrssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.rss_reader.sax.RSSHander;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public final String RSS_URL = "http://feed.feedsky.com/woshiyigebing12345";
    public final String tag = "RSSReader";
    private RSSFeed feed = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        feed = getFeed(RSS_URL);

        showListView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private RSSFeed getFeed(String urlString) {
        try {
            URL url = new URL(urlString);

            //新建SAX--xml解析工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            XMLReader reader = parser.getXMLReader();

            RSSHander rssHander = new RSSHander();
            InputSource is = new InputSource(url.openStream());

            reader.parse(is);

            return rssHander.getFeed();


        } catch (Exception e) {
            Log.e("e", e.toString());
            return null;
        }
    }

}
