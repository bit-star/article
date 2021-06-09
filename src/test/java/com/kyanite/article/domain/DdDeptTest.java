package com.kyanite.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.article.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DdDeptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DdDept.class);
        DdDept ddDept1 = new DdDept();
        ddDept1.setId(1L);
        DdDept ddDept2 = new DdDept();
        ddDept2.setId(ddDept1.getId());
        assertThat(ddDept1).isEqualTo(ddDept2);
        ddDept2.setId(2L);
        assertThat(ddDept1).isNotEqualTo(ddDept2);
        ddDept1.setId(null);
        assertThat(ddDept1).isNotEqualTo(ddDept2);
    }
}
