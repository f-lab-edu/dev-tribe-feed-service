package com.devtribe.event;

import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.task.PostUnvoteTask;
import com.devtribe.task.PostVoteTask;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostVoteEventConsumer {

    private final PostVoteTask voteTask;
    private final PostUnvoteTask unvoteTask;

    public PostVoteEventConsumer(PostVoteTask voteTask, PostUnvoteTask unvoteTask) {
        this.voteTask = voteTask;
        this.unvoteTask = unvoteTask;
    }

    @Bean(name = "vote")
    public Consumer<PostVoteEvent> vote() {
        return event -> {
            log.info("Consumed post-vote event: {}", event);

            if (event.getType() == VoteType.UPVOTE || event.getType() == VoteType.DOWNVOTE) {
                voteTask.processEvent(event);
            } else {
                unvoteTask.processEvent(event);
            }
        };
    }

}
