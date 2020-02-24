package org.carlspring.strongbox.services.impl;

import org.carlspring.strongbox.StorageApiTestConfig;
import org.carlspring.strongbox.data.CacheManagerTestExecutionListener;
import org.carlspring.strongbox.domain.RepositoryArtifactIdGroupEntity;
import org.carlspring.strongbox.services.RepositoryArtifactIdGroupService;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Przemyslaw Fusik
 */
@SpringBootTest
@ActiveProfiles(profiles = "test")
@ContextConfiguration(classes = StorageApiTestConfig.class)
@TestExecutionListeners(listeners = { CacheManagerTestExecutionListener.class },
                        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class RepositoryArtifactIdGroupServiceImplTest
{

    @Inject
    private RepositoryArtifactIdGroupService repositoryArtifactIdGroupService;

    @Test
    public void repositoryArtifactIdGroupShouldBeProtectedByIndex()
    {
        RepositoryArtifactIdGroupEntity g1 = new RepositoryArtifactIdGroupEntity();
        g1.setName("a1");
        g1.setRepositoryId("r1");
        g1.setStorageId("s1");
        System.out.println(repositoryArtifactIdGroupService.save(g1).getObjectId());

        assertThatExceptionOfType(ORecordDuplicatedException.class)
                .isThrownBy(() -> {
                    RepositoryArtifactIdGroupEntity g2 = new RepositoryArtifactIdGroupEntity();
                    g2.setName("a1");
                    g2.setRepositoryId("r1");
                    g2.setStorageId("s1");
                    System.out.println(repositoryArtifactIdGroupService.save(g2).getObjectId());
        });
    }
}
