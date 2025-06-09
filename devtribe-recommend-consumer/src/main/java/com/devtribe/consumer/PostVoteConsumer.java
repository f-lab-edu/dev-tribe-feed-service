package com.devtribe.consumer;

import com.devtribe.domain.vote.entity.VoteType;
import com.devtribe.event.PostVoteEvent;
import com.devtribe.task.PostUnvoteTask;
import com.devtribe.task.PostVoteTask;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PostVoteConsumer {

    private final PostVoteTask postVoteTask;
    private final PostUnvoteTask postUnvoteTask;

    public PostVoteConsumer(PostVoteTask postVoteTask, PostUnvoteTask postUnvoteTask) {
        this.postVoteTask = postVoteTask;
        this.postUnvoteTask = postUnvoteTask;
    }

    @Bean(name = "postVote")
    public Consumer<PostVoteEvent> postVote() {
        return event -> {
            if (event.voteType() != VoteType.UNVOTE) {
                postVoteTask.processEvent(event);
            } else {
                postUnvoteTask.processEvent(event);
            }
        };
    }

}
