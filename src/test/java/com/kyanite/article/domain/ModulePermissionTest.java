package com.kyanite.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kyanite.article.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModulePermissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModulePermission.class);
        ModulePermission modulePermission1 = new ModulePermission();
        modulePermission1.setId(1L);
        ModulePermission modulePermission2 = new ModulePermission();
        modulePermission2.setId(modulePermission1.getId());
        assertThat(modulePermission1).isEqualTo(modulePermission2);
        modulePermission2.setId(2L);
        assertThat(modulePermission1).isNotEqualTo(modulePermission2);
        modulePermission1.setId(null);
        assertThat(modulePermission1).isNotEqualTo(modulePermission2);
    }
}
