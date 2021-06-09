package com.kyanite.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.article.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MsgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Msg.class);
        Msg msg1 = new Msg();
        msg1.setId(1L);
        Msg msg2 = new Msg();
        msg2.setId(msg1.getId());
        assertThat(msg1).isEqualTo(msg2);
        msg2.setId(2L);
        assertThat(msg1).isNotEqualTo(msg2);
        msg1.setId(null);
        assertThat(msg1).isNotEqualTo(msg2);
    }
}
