package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.base.exception.CircleException;
import com.chs.base.model.PageResult;
import com.chs.circlepost.mapper.*;
import com.chs.circlepost.model.dto.AddUpdatePostParams;
import com.chs.circlepost.model.dto.CommentPostParams;
import com.chs.circlepost.model.dto.PostDto;
import com.chs.circlepost.model.dto.QueryPostParams;
import com.chs.circlepost.model.po.*;
import com.chs.circlepost.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 帖子表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private PostTopicMapper postTopicMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private CircleMapper circleMapper;
    @Autowired
    private PostCircleMapper postCircleMapper;
    @Autowired
    private UserLikePostMapper userLikePostMapper;
    @Autowired
    private UserCommentPostMapper userCommentPostMapper;
    @Autowired
    private UserCollectPostMapper userCollectPostMapper;

    @Override
    public void savePost(AddUpdatePostParams params) {
        List<String> topics = getPostTopic(params.getContent());
        Post post = new Post();
        BeanUtils.copyProperties(params, post);
        this.save(post);
        savePostTopic(topics, post.getId());
        if(post.getCircleId() != null){
            savePostCircle(params.getCircleId(), post.getId());
        }
    }

    @Override
    public void updatePost(AddUpdatePostParams params) {
        List<String> topics = getPostTopic(params.getContent());
        Post post = new Post();
        BeanUtils.copyProperties(params, post);
        this.updateById(post);
        // 删除已有的帖子-主题映射
        LambdaQueryWrapper<PostTopic> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PostTopic::getPostId, params.getId());
        postTopicMapper.delete(lqw);
        // 保存帖子-主题映射
        savePostTopic(topics, post.getId());
        if(post.getCircleId() != null){
            // 删除已有的帖子-圈子映射
            LambdaQueryWrapper<PostCircle> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(PostCircle::getPostId, post.getId());
            postCircleMapper.delete(lqw2);
            // 保存帖子-圈子映射
            savePostCircle(params.getCircleId(), params.getId());
        }
    }

    @Override
    public PageResult<PostDto> queryPost(QueryPostParams params, Integer userId) {
        LambdaQueryWrapper<Post> lqw = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(params.getKeyWord())){
            String key = params.getKeyWord();
            lqw.and(a -> a.like(Post::getTitle, key).or(b -> b.like(Post::getContent, key)));
        }
        if(params.getGenreId() != null){
            lqw.eq(Post::getGenreId, params.getGenreId());
        }
        if(params.getTopicId() != null){
            LambdaQueryWrapper<PostTopic> lqwPostTopic = new LambdaQueryWrapper<>();
            lqwPostTopic.eq(PostTopic::getTopicId, params.getTopicId());
            List<PostTopic> postTopics = postTopicMapper.selectList(lqwPostTopic);
            List<Integer> postIds = postTopics.stream().map(PostTopic::getPostId).collect(Collectors.toList());
            lqw.in(Post::getId, postIds);
        }
        lqw.orderByDesc(Post::getCreateTime);

        Page<Post> page = new Page<>(params.getPage(), params.getPageSize());
        postMapper.selectPage(page, lqw);

        List<Post> records = page.getRecords();
        long count = page.getTotal();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : records) {
            PostDto postDto = new PostDto();
            BeanUtils.copyProperties(post, postDto);
            Genre genre = genreMapper.selectById(post.getGenreId());
            postDto.setGenre(genre.getGenreName());
            String topicName = getFirstTopic(post.getId());
            postDto.setTopic(topicName);
            if(post.getCircleId() != null){
                Circle circle = circleMapper.selectById(post.getCircleId());
                postDto.setCircle(circle.getCircleName());
            }
            Integer postId = post.getId();
            postDto.setLikeCount(getLikeCount(postId));
            postDto.setCommentCount(getCommentCount(postId));
            postDto.setCollectCount(getCollectCount(postId));
            postDto.setLike(isLike(postId, userId));
            postDto.setCollect(isCollect(postId, userId));
            postDtos.add(postDto);
        }

        return new PageResult<>(postDtos, count);
    }

    @Override
    public void removeById(Integer postId) {
        // 删除帖子-主题关联表数据
        LambdaQueryWrapper<PostTopic> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PostTopic::getPostId, postId);
        postTopicMapper.delete(lqw);

        // 删除帖子-圈子关联表数据
        Post post = this.getById(postId);
        if(post.getCircleId() != null){
            LambdaQueryWrapper<PostCircle> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(PostCircle::getPostId, postId);
            postCircleMapper.delete(lqw2);
        }

        // 删除帖子-点赞关联表数据
        LambdaQueryWrapper<UserLikePost> lqw3 = new LambdaQueryWrapper<>();
        lqw3.eq(UserLikePost::getPostId, postId);
        userLikePostMapper.delete(lqw3);
        // 删除帖子-收藏关联表数据
        LambdaQueryWrapper<UserCollectPost> lqw4 = new LambdaQueryWrapper<>();
        lqw4.eq(UserCollectPost::getPostId, postId);
        userCollectPostMapper.delete(lqw4);
        // 删除帖子-评论关联表数据
        LambdaQueryWrapper<UserCommentPost> lqw5 = new LambdaQueryWrapper<>();
        lqw5.eq(UserCommentPost::getPostId, postId);
        userCommentPostMapper.delete(lqw5);
        // 删除帖子
        super.removeById(postId);
    }

    private String getFirstTopic(Integer postId){
        LambdaQueryWrapper<PostTopic> lqw = new LambdaQueryWrapper<>();
        lqw.eq(PostTopic::getPostId, postId).orderByDesc(PostTopic::getCreateTime);
        List<PostTopic> postTopics = postTopicMapper.selectList(lqw);
        if(postTopics.size() == 0){
            return null;
        }
        Topic topic = topicMapper.selectById(postTopics.get(0).getTopicId());
        return topic.getTopicName();
    }

    private List<String> getPostTopic(String content){
        // 提取帖子中的所有主题
        String regex = "#(.*?)#";
        String topicRegex = "[\\u4e00-\\u9fa5\\w,.?，。？]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        Pattern topicPattern = Pattern.compile(topicRegex);

        List<String> topics = new ArrayList<>();
        while(matcher.find()){
            String topicName = matcher.group(1);
            if(!topicPattern.matcher(topicName).matches()){
                CircleException.cast("主题名不合法");
            }
            topics.add(topicName);
        }
        return topics;
    }

    private void savePostTopic(List<String> topics, Integer postId){
        for(String topicName:topics){
            // 添加帖子中不存在的主题
            LambdaQueryWrapper<Topic> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Topic::getTopicName, topicName);
            Topic topicDB = topicMapper.selectOne(lqw);
            Integer topicId;
            if(topicDB == null){
                Topic topic = new Topic();
                topic.setTopicName(topicName);
                topicMapper.insert(topic);
                topicId = topic.getId();
            }else{
                topicId = topicDB.getId();
            }

            // 添加帖子-主题映射
            PostTopic postTopic = new PostTopic();
            postTopic.setPostId(postId);
            postTopic.setTopicId(topicId);
            postTopicMapper.insert(postTopic);
        }
    }

    private void savePostCircle(Integer circleId, Integer postId) {
        PostCircle postCircle = new PostCircle();
        postCircle.setCircleId(circleId);
        postCircle.setPostId(postId);
        postCircleMapper.insert(postCircle);
    }

    @Override
    public PostDto queryPostById(Integer postId, Integer userId) {
        Post post = this.getById(postId);
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        // 查询帖子分类
        Genre genre = genreMapper.selectById(post.getGenreId());
        postDto.setGenre(genre.getGenreName());
        // 查询帖子主题
        String firstTopic = getFirstTopic(postId);
        postDto.setTopic(firstTopic);
        // 查询帖子圈子
        if(post.getCircleId() != null){
            Circle circle = circleMapper.selectById(post.getCircleId());
            postDto.setCircle(circle.getCircleName());
        }
        postDto.setLikeCount(getLikeCount(postId));
        postDto.setCommentCount(getCommentCount(postId));
        postDto.setCollectCount(getCollectCount(postId));
        postDto.setLike(isLike(postId, userId));
        postDto.setCollect(isCollect(postId, userId));
        return postDto;
    }

    private boolean isLike(Integer postId, Integer userId){
        LambdaQueryWrapper<UserLikePost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserLikePost::getPostId, postId).eq(UserLikePost::getUserId, userId);
        return userLikePostMapper.selectCount(lqw) > 0;
    }

    private boolean isCollect(Integer postId, Integer userId){
        LambdaQueryWrapper<UserCollectPost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserCollectPost::getPostId, postId).eq(UserCollectPost::getUserId, userId);
        return userCollectPostMapper.selectCount(lqw) > 0;
    }

    private Integer getLikeCount(Integer postId){
        LambdaQueryWrapper<UserLikePost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserLikePost::getPostId, postId);
        return userLikePostMapper.selectCount(lqw);
    }

    private Integer getCommentCount(Integer postId){
        LambdaQueryWrapper<UserCommentPost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserCommentPost::getPostId, postId);
        return userCommentPostMapper.selectCount(lqw);
    }

    private Integer getCollectCount(Integer postId){
        LambdaQueryWrapper<UserCollectPost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserCollectPost::getPostId, postId);
        return userCollectPostMapper.selectCount(lqw);
    }

    @Override
    public void likePost(Integer postId, Integer userId) {
        LambdaQueryWrapper<UserLikePost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserLikePost::getPostId, postId).eq(UserLikePost::getUserId, userId);
        UserLikePost userLikePost = userLikePostMapper.selectOne(lqw);
        // 存在则删除，不存在则添加
        if(userLikePost != null){
            userLikePostMapper.deleteById(userLikePost.getId());
        }else{
            UserLikePost userLikePostNew = new UserLikePost();
            userLikePostNew.setPostId(postId);
            userLikePostNew.setUserId(userId);
            userLikePostMapper.insert(userLikePostNew);
        }
    }

    @Override
    public void commentPost(CommentPostParams params, Integer userId) {
        UserCommentPost userCommentPost = new UserCommentPost();
        userCommentPost.setPostId(params.getPostId());
        userCommentPost.setCommentId(params.getCommentId());
        userCommentPost.setContent(params.getContent());
        userCommentPost.setParentId(params.getParentId());
        userCommentPost.setUserId(userId);
        userCommentPostMapper.insert(userCommentPost);
    }

    @Override
    public void collectPost(Integer postId, Integer userId) {
        LambdaQueryWrapper<UserCollectPost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserCollectPost::getPostId, postId);
        lqw.eq(UserCollectPost::getUserId, userId);
        UserCollectPost userCollectPost = userCollectPostMapper.selectOne(lqw);
        // 如果存在则删除，不存在则添加
        if(userCollectPost != null){
            userCollectPostMapper.deleteById(userCollectPost.getId());
        }else{
            UserCollectPost userCollectPostNew = new UserCollectPost();
            userCollectPostNew.setPostId(postId);
            userCollectPostNew.setUserId(userId);
            userCollectPostMapper.insert(userCollectPostNew);
        }
    }
}
