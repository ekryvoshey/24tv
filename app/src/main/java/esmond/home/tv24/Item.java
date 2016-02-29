package esmond.home.tv24;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Item{
    @Element(name = "title")
    private String title;
    @Element(required = false)
    private String link;
    @Element
    private String description;
    @Attribute(required = false)
    private String align;
    @Attribute(required = false)
    private int vSpace;
    @Attribute (required = false)
    private int hSpace;
    @Attribute
    private String src;
    @Element(name = "pubDate")
    private String pubDate;
    @Element(required = false)
    private String guid;
    @Element(required = false)
    private String creator;
    @Element
    private String date;
}
