package com.devtribe.consumer;

import com.devtribe.event.PostClickEvent;
import com.devtribe.task.PostClickTask;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostClickConsumer {

    private final PostClickTask postClickTask;

    public PostClickConsumer(PostClickTask postClickTask) {
        this.postClickTask = postClickTask;
    }

    @Bean(name = "postClick")
    public Consumer<PostClickEvent> postClick() {
        return postClickTask::processEvent;
    }
}
