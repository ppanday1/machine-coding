package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.models.Influencer;
import org.example.models.ShareModel;
import org.example.respository.InfluencerRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InfluencerService {
    private final InfluencerRepository influencerRepository;

    public InfluencerService(InfluencerRepository influencerRepository) {
        this.influencerRepository = influencerRepository;
    }

    public void addInfluencer(String name, String shareModel, double share) {
        try {
            Influencer influencer = new Influencer();
            influencer.setName(name);
            influencer.setShareModel(ShareModel.valueOf(shareModel));
            influencer.setShare(share);
            influencerRepository.saveInfluencer(influencer);
        } catch (Exception e) {
            log.error("error occurred with adding influencer {} , {}", name, e);
        }
    }

    public Influencer getInfluencer(String name) {
        try {
            return influencerRepository.getInfluencerByName(name);
        } catch (Exception e) {
            log.error("error occurred with adding influencer {} , {}", name, e);
        }
        return null;
    }

    public double getPendingPaymentForInfluencer(String influencerId) {
        try {
            Influencer influencer = influencerRepository.getInfluencerByName(influencerId);
            return influencer.getTotalDueAmount();
        } catch (Exception e) {
            log.error("Error occurred while fetching amount for influencer ", e);
        }
        return 0;
    }

    public double getTotalEarningForInfluencer(String influencerId) {
        try {
            Influencer influencer = influencerRepository.getInfluencerByName(influencerId);
            return influencer.getTotalPaidAmount();
        } catch (Exception e) {
            log.error("Error occurred while fetching amount for influencer ", e);
        }
        return 0;
    }

}
