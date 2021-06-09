package com.kyanite.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.article.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubType.class);
        SubType subType1 = new SubType();
        subType1.setId(1L);
        SubType subType2 = new SubType();
        subType2.setId(subType1.getId());
        assertThat(subType1).isEqualTo(subType2);
        subType2.setId(2L);
        assertThat(subType1).isNotEqualTo(subType2);
        subType1.setId(null);
        assertThat(subType1).isNotEqualTo(subType2);
    }
}
