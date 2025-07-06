package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.base.exception.CircleException;
import com.chs.base.model.PageResult;
import com.chs.circlepost.mapper.*;
import com.chs.circlepost.model.dto.AddUpdatePostParams;
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

    @Override
    public void savePost(AddUpdatePostParams params) {
        List<String> topics = getPostTopic(params.getContent());
        Post post = new Post();
        BeanUtils.copyProperties(params, post);
        this.save(post);
        savePostTopic(topics, post.getId());
    }

    @Override
    public void updatePost(AddUpdatePostParams params) {
        List<String> topics = getPostTopic(params.getContent());
        Post post = new Post();
        BeanUtils.copyProperties(params, post);
        this.updateById(post);
        savePostTopic(topics, post.getId());
    }

    @Override
    public PageResult<PostDto> queryPost(QueryPostParams params) {
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
            postDtos.add(postDto);
        }

        return new PageResult<>(postDtos, count);
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
        // 添加帖子中不存在的主题
        for(String topicName:topics){
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

}
