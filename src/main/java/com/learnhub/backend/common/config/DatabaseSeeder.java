package com.learnhub.backend.common.config;

import com.learnhub.backend.modules.discussion.entity.QAReply;
import com.learnhub.backend.modules.discussion.entity.QAThread;
import com.learnhub.backend.modules.discussion.repository.QAThreadRepository;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.entity.Review;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseSeeder — Automatic startup initializer for Q&A discussion threads and resource reviews.
 * Ensures PostgreSQL database holds populated initial records for Resource Detail & Q&A pages.
 */
@Component
public class
DatabaseSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final ContentRepository contentRepository;
    private final QAThreadRepository qaThreadRepository;
    private final ReviewRepository reviewRepository;

    public DatabaseSeeder(ContentRepository contentRepository,
                          QAThreadRepository qaThreadRepository,
                          ReviewRepository reviewRepository) {
        this.contentRepository = contentRepository;
        this.qaThreadRepository = qaThreadRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Checking database seed records for Q&A threads and Reviews...");

        List<Content> contents = contentRepository.findAll();
        if (contents.isEmpty()) {
            log.info("No content resources found to seed Q&A threads.");
            return;
        }

        if (qaThreadRepository.count() == 0) {
            log.info("Seeding initial Q&A discussion threads into PostgreSQL database...");
            for (Content content : contents) {
                QAThread thread1 = new QAThread();
                thread1.setContentId(content.getId());
                thread1.setAuthorName("Priya Sharma");
                thread1.setRole("LEARNER");
                thread1.setQuestion("In section 2 of '" + content.getTitle() + "', what is the recommended architecture pattern?");
                thread1.setUpvotes(5);
                thread1.setIsResolved(true);

                QAReply reply1 = new QAReply();
                reply1.setAuthorName(content.getCreator() != null ? content.getCreator().getName() : "Content Creator");
                reply1.setRole("CREATOR");
                reply1.setReply("Great question! For '" + content.getTitle() + "', ensure you follow the modular structure demonstrated in the guide.");
                reply1.setUpvotes(8);
                reply1.setIsVerifiedAnswer(true);

                List<QAReply> replies = new ArrayList<>();
                replies.add(reply1);
                thread1.setReplies(replies);

                qaThreadRepository.save(thread1);
            }
            log.info("Successfully seeded Q&A discussion threads into PostgreSQL.");
        }

        if (reviewRepository.count() == 0) {
            log.info("Seeding initial reviews into PostgreSQL database...");
            for (Content content : contents) {
                Review rev1 = new Review();
                rev1.setContentId(content.getId());
                rev1.setStudentName("Aarav Sharma");
                rev1.setAvatarUrl("https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100");
                rev1.setRating(5);
                rev1.setReviewText("Exceptionally clear explanation in '" + content.getTitle() + "'!");
                rev1.setReviewDate("2026-07-15");
                reviewRepository.save(rev1);

                Review rev2 = new Review();
                rev2.setContentId(content.getId());
                rev2.setStudentName("Priya Patel");
                rev2.setAvatarUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100");
                rev2.setRating(5);
                rev2.setReviewText("The concepts in '" + content.getTitle() + "' helped me pass my technical assessment easily.");
                rev2.setReviewDate("2026-07-20");
                reviewRepository.save(rev2);
            }
            log.info("Successfully seeded resource reviews into PostgreSQL.");
        }
    }
}
