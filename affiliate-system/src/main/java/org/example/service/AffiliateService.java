package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.models.AffiliateLink;
import org.example.respository.LinkRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AffiliateService {
    private final LinkRepository linkRepository;

    public AffiliateService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public void addLink(String link, String itemId, String influencerId) {
        try {
            AffiliateLink affiliateLink = new AffiliateLink(link, itemId, influencerId);
            linkRepository.addLink(affiliateLink);
        } catch (Exception e) {
            log.error("Error Occurred while adding affiliate link for {}", link, e);
        }
    }
}
