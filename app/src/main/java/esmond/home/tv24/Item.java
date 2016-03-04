package esmond.home.tv24;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Item{
    @Element
    private String title;
    @Element
    private String link;
    @Element
    private String description;
//    @Element
//    private String img;
//    @Element
//    private String align;
////    @Attribute
////    private int vSpace;
////    @Attribute
////    private int hSpace;
////    @Attribute
////    private String src;
    @Element
    private String pubDate;
    @Element (required = false)
    private String guid;
    @Element (required = false)
    private String creator;
    @Element (required = false)
    private String date;

    private String url;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        String[] subString1 = description.split("src='");
        String[] subString2 = subString1[1].split("'>");
        url = subString2[0];
        return url;
    }

    public String getLink() {
        return link;
    }
}
