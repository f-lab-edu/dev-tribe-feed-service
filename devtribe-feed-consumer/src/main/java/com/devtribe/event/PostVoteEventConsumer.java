package com.devtribe.event;

import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.task.PostUnvoteTask;
import com.devtribe.task.PostVoteTask;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
            if (event.getVoteType() == VoteType.UPVOTE || event.getVoteType() == VoteType.DOWNVOTE) {
                voteTask.processEvent(event);
            } else {
                unvoteTask.processEvent(event);
            }
        };
    }

}
