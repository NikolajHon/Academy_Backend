package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.exceptions.EducationAppException;
import sk.posam.fsa.discussion.exceptions.ResourceNotFoundException;
import sk.posam.fsa.discussion.exceptions.ResourceAlreadyExistsException;
import sk.posam.fsa.discussion.repository.*;

import java.time.LocalDateTime;
import java.util.Collection;

public class ForumService implements ForumFacade {
    private final TopicRepository        topicRepository;
    private final PostRepository         postRepository;
    private final CourseRepository       courseRepository;
    private final CurrentUserRepository  currentUser;
    private final EmailSenderRepository  emailSenderRepository;

    public ForumService(TopicRepository topicRepository,
                        PostRepository postRepository,
                        CourseRepository courseRepository,
                        CurrentUserRepository currentUser,
                        EmailSenderRepository emailSenderRepository) {
        this.topicRepository       = topicRepository;
        this.postRepository        = postRepository;
        this.courseRepository      = courseRepository;
        this.currentUser           = currentUser;
        this.emailSenderRepository = emailSenderRepository;
    }

    @Override
    public Collection<Topic> getTopics(long courseId, int page, int size) {
        try {
            return topicRepository.getByCourse(courseId, page, size);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to load topics for courseId=" + courseId, e
            );
        }
    }

    @Override
    public Topic getTopic(long id) {
        Topic topic = topicRepository.get(id);
        if (topic == null) {
            throw new ResourceNotFoundException("Topic with id=" + id + " not found");
        }
        return topic;
    }

    @Override
    public void createTopic(Topic topic) {
        // validate course
        Course course = courseRepository.getCourse(topic.getCourse().getId());
        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course with id=" + topic.getCourse().getId() + " not found"
            );
        }
        topic.setCourse(course);
        topic.setCreatedAt(LocalDateTime.now());
        topic.setStatus(TopicStatus.OPEN);

        try {
            topicRepository.create(topic);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to create topic", e);
        }
    }

    @Override
    public void updateTopic(Topic topic) {
        // ensure exists
        if (topicRepository.get(topic.getId()) == null) {
            throw new ResourceNotFoundException("Topic with id=" + topic.getId() + " not found");
        }
        try {
            topicRepository.update(topic);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to update topic id=" + topic.getId(), e);
        }
    }

    @Override
    public void deleteTopic(long id) {
        if (topicRepository.get(id) == null) {
            throw new ResourceNotFoundException("Topic with id=" + id + " not found");
        }
        try {
            topicRepository.delete(id);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to delete topic id=" + id, e);
        }
    }

    @Override
    public Collection<Post> getRootPosts(long topicId, int page, int size) {
        // ensure topic exists
        if (topicRepository.get(topicId) == null) {
            throw new ResourceNotFoundException("Topic with id=" + topicId + " not found");
        }
        try {
            return postRepository.getRootsByTopic(topicId, page, size);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException(
                    "Failed to load posts for topicId=" + topicId, e
            );
        }
    }

    @Override
    public Post getPost(long id) {
        Post post = postRepository.get(id);
        if (post == null) {
            throw new ResourceNotFoundException("Post with id=" + id + " not found");
        }
        return post;
    }

    @Override
    public Post createPost(Post post) {
        try {
            post.setAuthor(currentUser.getCurrentUser());

            Topic topic = topicRepository.get(post.getTopic().getId());
            if (topic == null) {
                throw new ResourceNotFoundException(
                        "Topic with id=" + post.getTopic().getId() + " not found"
                );
            }
            post.setTopic(topic);

            post.setStatus(PostStatus.ACTIVE);
            post.setCreatedAt(LocalDateTime.now());

            topicRepository.get(post.getTopic().getId()); // ensure topic still exists
            postRepository.create(post);

            return post;
        } catch (ResourceNotFoundException rnfe) {
            throw rnfe;
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to create post", e);
        }
    }

    @Override
    public Post reply(Long parentId, Post reply) {
        try {
            Post parent = postRepository.get(parentId);
            if (parent == null) {
                throw new ResourceNotFoundException("Parent post with id=" + parentId + " not found");
            }
            reply.setParent(parent);
            reply.setTopic(parent.getTopic());
            prepareAndSave(reply);

            emailSenderRepository.send(
                    parent.getAuthor().getEmail(),
                    "Новый ответ на ваш пост",
                    buildBody(reply, parent)
            );

            return reply;
        } catch (ResourceNotFoundException rnfe) {
            throw rnfe;
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to reply to post id=" + parentId, e);
        }
    }

    @Override
    public void updatePost(Post post) {
        if (postRepository.get(post.getId()) == null) {
            throw new ResourceNotFoundException("Post with id=" + post.getId() + " not found");
        }
        post.setUpdatedAt(LocalDateTime.now());
        try {
            postRepository.update(post);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to update post id=" + post.getId(), e);
        }
    }

    @Override
    public void deletePost(long id) {
        if (postRepository.get(id) == null) {
            throw new ResourceNotFoundException("Post with id=" + id + " not found");
        }
        try {
            postRepository.delete(id);
        } catch (RuntimeException | Error e) {
            throw new EducationAppException("Failed to delete post id=" + id, e);
        }
    }

    // --- private helpers ---

    private void prepareAndSave(Post post) {
        post.setAuthor(currentUser.getCurrentUser());
        post.setStatus(PostStatus.ACTIVE);
        post.setCreatedAt(LocalDateTime.now());

        Topic topic = topicRepository.get(post.getTopic().getId());
        if (topic == null) {
            throw new ResourceNotFoundException(
                    "Topic with id=" + post.getTopic().getId() + " not found"
            );
        }
        post.setTopic(topic);

        postRepository.create(post);
    }

    private String buildBody(Post reply, Post parent) {
        String replier = reply.getAuthor() != null
                ? reply.getAuthor().getFamilyName()
                : "Somebody";
        return String.format("User %s replied:\n\n«%s»",
                replier, reply.getContent());
    }
}
