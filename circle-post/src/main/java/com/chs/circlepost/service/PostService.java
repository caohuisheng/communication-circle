package com.chs.circlepost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chs.base.model.PageResult;
import com.chs.circlepost.model.dto.AddUpdatePostParams;
import com.chs.circlepost.model.dto.CommentPostParams;
import com.chs.circlepost.model.dto.PostDto;
import com.chs.circlepost.model.dto.QueryPostParams;
import com.chs.circlepost.model.po.Post;

/**
 * <p>
 * 帖子表 服务类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
public interface PostService extends IService<Post> {

    /**
     * 保存帖子
     * @param params
     */
    void savePost(AddUpdatePostParams params);

    /**
     * 更新帖子
     * @param params
     */
    void updatePost(AddUpdatePostParams params);

    /**
     * 查询帖子
     * @param params
     * @param userId
     * @return
     */
    PageResult<PostDto> queryPost(QueryPostParams params, Integer userId);

    /**
     * 删除帖子
     * @param postId
     */
    void removeById(Integer postId);

    /**
     * 根据id查询帖子
     * @param postId
     */
    PostDto queryPostById(Integer postId, Integer userId);

    /**
     * 点赞帖子
     * @param postId
     * @param userId
     */
    void likePost(Integer postId, Integer userId);

    /**
     * 评论帖子
     * @param params
     * @param userId
     */
    void commentPost(CommentPostParams params, Integer userId);

    /**
     * 收藏帖子
     * @param postId
     * @param userId
     */
    void collectPost(Integer postId, Integer userId);
}
