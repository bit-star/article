package com.kyanite.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.article.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MsgTaskTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MsgTask.class);
        MsgTask msgTask1 = new MsgTask();
        msgTask1.setId(1L);
        MsgTask msgTask2 = new MsgTask();
        msgTask2.setId(msgTask1.getId());
        assertThat(msgTask1).isEqualTo(msgTask2);
        msgTask2.setId(2L);
        assertThat(msgTask1).isNotEqualTo(msgTask2);
        msgTask1.setId(null);
        assertThat(msgTask1).isNotEqualTo(msgTask2);
    }
}
