package com.devtribe.domain.global.config.redis;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class LuaScriptConfig {

    private static final String LUA_SCRIPT_PATH = "scripts/";

    private static final String POST_VOTE = "post_vote.lua";
    private static final String POST_UNVOTE = "post_unvote.lua";
    private static final String POST_VOTE_COUNT = "post_vote_count.lua";

    @Bean
    public RedisScript<List> postVoteScript() {
        return loadScript(POST_VOTE);
    }

    @Bean
    public RedisScript<List> postUnvoteScript() {
        return loadScript(POST_UNVOTE);
    }

    @Bean
    public RedisScript<List> postVoteCountScript() {
        return loadScript(POST_VOTE_COUNT);
    }

    private RedisScript<List> loadScript(String fileName) {
        Resource scriptResource = new ClassPathResource(LUA_SCRIPT_PATH + fileName);
        return RedisScript.of(scriptResource, List.class);
    }
}
