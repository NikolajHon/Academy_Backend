package sk.posam.fsa.discussion.service;

import sk.posam.fsa.discussion.*;
import sk.posam.fsa.discussion.repository.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.logging.Logger;

public class ForumService implements ForumFacade {
    private final TopicRepository  topicRepository;
    private final PostRepository   postRepository;
    private final CourseRepository courseRepository;
    private final CurrentUserPort  currentUser;
    private final EmailSenderRepository emailSenderRepository;

    public ForumService(TopicRepository topicRepository,
                        PostRepository  postRepository,
                        CourseRepository courseRepository,
                        CurrentUserPort  currentUser, EmailSenderRepository emailSenderRepository) {

        this.topicRepository  = topicRepository;
        this.postRepository   = postRepository;
        this.courseRepository = courseRepository;
        this.currentUser      = currentUser;
        this.emailSenderRepository = emailSenderRepository;
    }


    @Override
    public Collection<Topic> getTopics(long courseId, int page, int size) {
        return topicRepository.getByCourse(courseId, page, size);
    }

    @Override public Topic getTopic(long id) { return topicRepository.get(id); }

    @Override
    public void createTopic(Topic topic) {

        Course course = courseRepository.getCourse(topic.getCourse().getId());
        topic.setCourse(course);

        topic.setCreatedAt(LocalDateTime.now());
        topic.setStatus(TopicStatus.OPEN);

        topicRepository.create(topic);
    }

    @Override public void updateTopic(Topic topic) { topicRepository.update(topic); }
    @Override public void deleteTopic(long id)      { topicRepository.delete(id); }


    @Override
    public Collection<Post> getRootPosts(long topicId, int page, int size) {
        return postRepository.getRootsByTopic(topicId, page, size);
    }

    @Override public Post getPost(long id) { return postRepository.get(id); }

    @Override
    public Post createPost(Post post) {
        // 1) Заполняем автора
        post.setAuthor(currentUser.getCurrentUser());

        // 2) Подтягиваем и подставляем тему из БД
        Topic topic = topicRepository.get(post.getTopic().getId());
        post.setTopic(topic);

        // 3) Статус, дата создания
        post.setStatus(PostStatus.ACTIVE);
        post.setCreatedAt(LocalDateTime.now());

        // 4) Логируем
        System.out.println("Создание корневого поста: topicId=" + topic.getId()
                + ", authorId=" + post.getAuthor().getId());

        // 5) Сохраняем
        postRepository.create(post);
        System.out.println("Пост сохранён: id=" + post.getId());

        return post;
    }


    private String buildBody(Post reply, Post parent) {
        String replier = reply.getAuthor() != null
                ? reply.getAuthor().getFamilyName()
                : "Кто‑то";
        return String.format("Пользователь %s ответил:\n\n«%s»",
                replier, reply.getContent());
    }
    @Override
    public Post reply(Long parentId, Post reply) {
        System.out.println("-> reply to " + parentId);
        Post parent = postRepository.get(parentId);
        reply.setParent(parent);
        reply.setTopic(parent.getTopic());
        prepareAndSave(reply);
        System.out.println("Ответ сохранён: id=" + reply.getId());

        // шлём письмо…
        System.out.println("Отправляем e‑mail автору родителя: " + parent.getAuthor().getEmail());
        emailSenderRepository.send(
                parent.getAuthor().getEmail(),
                "Новый ответ на ваш пост",
                buildBody(reply, parent)
        );
        System.out.println("Уведомление отправлено");

        return reply;
    }
    private void prepareAndSave(Post post) {
        post.setAuthor(currentUser.getCurrentUser());
        post.setStatus(PostStatus.ACTIVE);
        post.setCreatedAt(LocalDateTime.now());
        post.setTopic(topicRepository.get(post.getTopic().getId()));
        postRepository.create(post);
    }
    @Override
    public void updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);
    }

    @Override public void deletePost(long id) { postRepository.delete(id); }
}
