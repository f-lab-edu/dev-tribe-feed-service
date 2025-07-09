package com.devtribe.integration.infra.post

import com.devtribe.domain.post.dao.PostRepository
import com.devtribe.integration.AbstractIntegrationTest
import com.devtribe.integration.DataTestConfig
import com.devtribe.integration.DatabaseCleaner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataTestConfig.class)
class PostSearchTest extends AbstractIntegrationTest {

    @Autowired
    PostRepository postRepository

    @Autowired
    DatabaseCleaner databaseCleaner

    void cleanup() {
        databaseCleaner.clear()
    }

    /// 게시글 셋업

    /// 키워드 주어지면, 가장 연관도 높은 게시글 반환 되어야 함.


}
