package com.devtribe.consumer;

import com.devtribe.event.PostEvent;
import com.devtribe.task.PostEventTask;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostEventConsumer {

    private final PostEventTask postEventTask;

    public PostEventConsumer(PostEventTask postEventTask) {
        this.postEventTask = postEventTask;
    }
    
    @Bean(name = "postEvent")
    public Consumer<PostEvent> postEvent() {
        return postEventTask::process;
    }

}
