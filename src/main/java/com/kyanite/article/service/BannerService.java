package com.kyanite.article.service;

import com.kyanite.article.domain.Banner;
import com.kyanite.article.repository.BannerRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Banner}.
 */
@Service
@Transactional
public class BannerService {

    private final Logger log = LoggerFactory.getLogger(BannerService.class);

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    /**
     * Save a banner.
     *
     * @param banner the entity to save.
     * @return the persisted entity.
     */
    public Banner save(Banner banner) {
        log.debug("Request to save Banner : {}", banner);
        return bannerRepository.save(banner);
    }

    /**
     * Partially update a banner.
     *
     * @param banner the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Banner> partialUpdate(Banner banner) {
        log.debug("Request to partially update Banner : {}", banner);

        return bannerRepository
            .findById(banner.getId())
            .map(
                existingBanner -> {
                    if (banner.getTitle() != null) {
                        existingBanner.setTitle(banner.getTitle());
                    }
                    if (banner.getCoverUrl() != null) {
                        existingBanner.setCoverUrl(banner.getCoverUrl());
                    }
                    if (banner.getName() != null) {
                        existingBanner.setName(banner.getName());
                    }
                    if (banner.getBrand() != null) {
                        existingBanner.setBrand(banner.getBrand());
                    }
                    if (banner.getModel() != null) {
                        existingBanner.setModel(banner.getModel());
                    }
                    if (banner.getSpecifications() != null) {
                        existingBanner.setSpecifications(banner.getSpecifications());
                    }
                    if (banner.getIsExport() != null) {
                        existingBanner.setIsExport(banner.getIsExport());
                    }
                    if (banner.getSupplier() != null) {
                        existingBanner.setSupplier(banner.getSupplier());
                    }
                    if (banner.getSupplierAddress() != null) {
                        existingBanner.setSupplierAddress(banner.getSupplierAddress());
                    }
                    if (banner.getSupplierCapacity() != null) {
                        existingBanner.setSupplierCapacity(banner.getSupplierCapacity());
                    }

                    return existingBanner;
                }
            )
            .map(bannerRepository::save);
    }

    /**
     * Get all the banners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Banner> findAll(Pageable pageable) {
        log.debug("Request to get all Banners");
        return bannerRepository.findAll(pageable);
    }

    /**
     * Get one banner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Banner> findOne(Long id) {
        log.debug("Request to get Banner : {}", id);
        return bannerRepository.findById(id);
    }

    /**
     * Delete the banner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Banner : {}", id);
        bannerRepository.deleteById(id);
    }
}
