package com.thitiphat.feedfold;

import com.thitiphat.feedfold.model.FeedModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by phatm on 11/17/2017.
 */

public class FeedExtacter {

    String result, title, link, description;
    List<FeedModel> feedList;


    public FeedExtacter(InputStream inputStream) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xmlPullParser = factory.newPullParser();
        xmlPullParser.setInput(inputStream, "UTF-8");
        int evenType = xmlPullParser.getEventType();
        while (evenType != XmlPullParser.END_DOCUMENT) {

            String name = xmlPullParser.getName();
            if (xmlPullParser.next() == XmlPullParser.TEXT) {
                result = xmlPullParser.getText();
                xmlPullParser.nextTag();
            }
            if (name.equalsIgnoreCase("title")) {
                title = result;
            }
            if (name.equalsIgnoreCase("link")) {
                link = result;
            }
            if (name.equalsIgnoreCase("description")) {
                description = result;
            }
            if (title != null && link != null && description != null) {
                FeedModel feedModel = new FeedModel();
                feedModel.setTitle(title);
                feedModel.setDescription(description);
                feedModel.setLink(link);
                feedList.add(feedModel);

                title = null;
                description = null;
                link = null;
            }
        }
    }
}
