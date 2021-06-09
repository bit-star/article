package com.kyanite.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.article.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnnexTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Annex.class);
        Annex annex1 = new Annex();
        annex1.setId(1L);
        Annex annex2 = new Annex();
        annex2.setId(annex1.getId());
        assertThat(annex1).isEqualTo(annex2);
        annex2.setId(2L);
        assertThat(annex1).isNotEqualTo(annex2);
        annex1.setId(null);
        assertThat(annex1).isNotEqualTo(annex2);
    }
}
