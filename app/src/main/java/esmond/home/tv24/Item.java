package esmond.home.tv24;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Item {
    private static final String ns = null;
    public final String title;
    public final String image;
    public final String description;
    public final String date;

    public Item(String title, String image, String description, String update) {
        this.description = description;
        this.title = title;
        this.image = image;
        this.date = update;
    }

    private Item readItem(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "rss");

        String title = null;
        String image = null;
        String description = null;
        String date = null;

        while (parser.next() != XmlPullParser.END_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String tagName = parser.getName();
            if (tagName.equals("title")){
                title = readTitle(parser);
            } else if (tagName.equals("img")){
                image = readImage(parser);
            } else if (tagName.equals("description")){
                description = readDescription(parser);
            } else if (tagName.equals("dc:date")){
                date = readDate(parser);
            } else skip(parser);
        }
        return new Item(title, image, description, date);
    }

    private String readTitle(XmlPullParser parser)throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readImage(XmlPullParser parser) throws XmlPullParserException, IOException{
        String imageLink = "";
        String tag = parser.getName();
        parser.require(XmlPullParser.START_TAG, ns, "img");
        if (tag.equals("img")){
            imageLink = parser.getAttributeValue(ns, "src");
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, ns, "img");
        return imageLink;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "dc:date");
        String date = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "dc:date");
        return date;
    }

    private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
