package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AffiliateLink {
    private String link;
    private String itemId;
    private String influencerId;
}
