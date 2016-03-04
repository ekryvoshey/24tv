package esmond.home.tv24;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss")
public class Rss {
    @Attribute(name = "version")
    private float version;
    @Element(name = "channel")
    private String channel;
    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "description")
    private String description;
    @Element(name = "creator")
    private String creator;
    @Element (name = "image")
    private Image image;
    @ElementList(inline=true)
    private List<Item> item;

    public List<Item> getItems() {
        return item;
    }
}

