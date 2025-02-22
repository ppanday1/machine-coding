package org.example.respository;

import org.example.exception.ElementAlreadyExistException;
import org.example.exception.ElementDoesNotExistException;
import org.example.models.AffiliateLink;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkRepository {
    private final ConcurrentHashMap<String, AffiliateLink> links;

    public LinkRepository() {
        links = new ConcurrentHashMap<>();
    }

    public void addLink(AffiliateLink link) throws ElementAlreadyExistException {
        if (links.containsKey(link.getLink())) {
            throw new ElementAlreadyExistException("Link already " + link.getLink() + " exists");
        }
        links.put(link.getLink(), link);
    }

    public AffiliateLink getAffiliateLinkByName(String link) throws ElementDoesNotExistException {
        if (!links.containsKey(link)) {
            throw new ElementDoesNotExistException("Link " + link + " does not exists");
        }
        return links.get(link);
    }

}
