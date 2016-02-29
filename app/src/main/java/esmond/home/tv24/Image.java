package esmond.home.tv24;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Image{
    @Element(name = "title")
    private String title;
    @Element(name = "url")
    private String url;
    @Element(name = "link")
    private String link;
}
