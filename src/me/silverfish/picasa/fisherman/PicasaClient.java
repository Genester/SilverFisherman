package me.silverfish.picasa.fisherman;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.photos.*;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: eugene
 * Date: 18.09.2010
 * Time: 16:16:33
 * To change this template use File | Settings | File Templates.
 */
public class PicasaClient {

    private static final String API_PREFIX
      = "http://picasaweb.google.com/data/feed/api/user/";

    private final String username;
    private final String password;

    private final PicasawebService service;

    public PicasaClient(String username, String password) {
        this.username = username;
        this.password = password;

        service = new PicasawebService(Fisherman.APP_NAME);

        try {
            service.setUserCredentials(username, password);
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException(e);
        }

    }

  public List<AlbumEntry> getAlbums(String username) throws IOException,
            ServiceException {

    String albumUrl = API_PREFIX + username;
    UserFeed userFeed = getFeed(albumUrl, UserFeed.class);

    List<GphotoEntry> entries = userFeed.getEntries();
    List<AlbumEntry> albums = new ArrayList<AlbumEntry>();
    for (GphotoEntry entry : entries) {
      GphotoEntry adapted = entry.getAdaptedEntry();
      if (adapted instanceof AlbumEntry) {
        albums.add((AlbumEntry) adapted);
      }
    }
    return albums;
  }

  public List<AlbumEntry> getAlbums() throws IOException, ServiceException {
    return getAlbums("default");
  }

  public List<PhotoEntry> getPhotos(AlbumEntry album) throws IOException,
      ServiceException {

    String feedHref = getLinkByRel(album.getLinks(), Link.Rel.FEED);
    AlbumFeed albumFeed = getFeed(feedHref, AlbumFeed.class);

    List<GphotoEntry> entries = albumFeed.getEntries();
    List<PhotoEntry> photos = new ArrayList<PhotoEntry>();
    for (GphotoEntry entry : entries) {
      GphotoEntry adapted = entry.getAdaptedEntry();
      if (adapted instanceof PhotoEntry) {
        photos.add((PhotoEntry) adapted);
      }
    }
    return photos;
  }

  public String getLinkByRel(List<Link> links, String relValue) {
    for (Link link : links) {
      if (relValue.equals(link.getRel())) {
        return link.getHref();
      }
    }
    throw new IllegalArgumentException("Missing " + relValue + " link.");
  }

  public <T extends GphotoFeed> T getFeed(String feedHref,
      Class<T> feedClass) throws IOException, ServiceException {
    System.out.println("Get Feed URL: " + feedHref);
    return service.getFeed(new URL(feedHref), feedClass);
    }
}
