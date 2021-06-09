package com.kyanite.article.web.rest;

import com.kyanite.article.domain.Banner;
import com.kyanite.article.repository.BannerRepository;
import com.kyanite.article.service.BannerService;
import com.kyanite.article.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kyanite.article.domain.Banner}.
 */
@RestController
@RequestMapping("/api")
public class BannerResource {

    private final Logger log = LoggerFactory.getLogger(BannerResource.class);

    private static final String ENTITY_NAME = "banner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannerService bannerService;

    private final BannerRepository bannerRepository;

    public BannerResource(BannerService bannerService, BannerRepository bannerRepository) {
        this.bannerService = bannerService;
        this.bannerRepository = bannerRepository;
    }

    /**
     * {@code POST  /banners} : Create a new banner.
     *
     * @param banner the banner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banner, or with status {@code 400 (Bad Request)} if the banner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banners")
    public ResponseEntity<Banner> createBanner(@RequestBody Banner banner) throws URISyntaxException {
        log.debug("REST request to save Banner : {}", banner);
        if (banner.getId() != null) {
            throw new BadRequestAlertException("A new banner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Banner result = bannerService.save(banner);
        return ResponseEntity
            .created(new URI("/api/banners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banners/:id} : Updates an existing banner.
     *
     * @param id the id of the banner to save.
     * @param banner the banner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banner,
     * or with status {@code 400 (Bad Request)} if the banner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banners/{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable(value = "id", required = false) final Long id, @RequestBody Banner banner)
        throws URISyntaxException {
        log.debug("REST request to update Banner : {}, {}", id, banner);
        if (banner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Banner result = bannerService.save(banner);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banner.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /banners/:id} : Partial updates given fields of an existing banner, field will ignore if it is null
     *
     * @param id the id of the banner to save.
     * @param banner the banner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banner,
     * or with status {@code 400 (Bad Request)} if the banner is not valid,
     * or with status {@code 404 (Not Found)} if the banner is not found,
     * or with status {@code 500 (Internal Server Error)} if the banner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/banners/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Banner> partialUpdateBanner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Banner banner
    ) throws URISyntaxException {
        log.debug("REST request to partial update Banner partially : {}, {}", id, banner);
        if (banner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Banner> result = bannerService.partialUpdate(banner);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banner.getId().toString())
        );
    }

    /**
     * {@code GET  /banners} : get all the banners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banners in body.
     */
    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getAllBanners(Pageable pageable) {
        log.debug("REST request to get a page of Banners");
        Page<Banner> page = bannerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banners/:id} : get the "id" banner.
     *
     * @param id the id of the banner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banners/{id}")
    public ResponseEntity<Banner> getBanner(@PathVariable Long id) {
        log.debug("REST request to get Banner : {}", id);
        Optional<Banner> banner = bannerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banner);
    }

    /**
     * {@code DELETE  /banners/:id} : delete the "id" banner.
     *
     * @param id the id of the banner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banners/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        log.debug("REST request to delete Banner : {}", id);
        bannerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
