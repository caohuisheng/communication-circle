package com.chs.circlepost.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chs.base.model.PageResult;
import com.chs.circlepost.model.dto.AddUpdatePostParams;
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

    void savePost(AddUpdatePostParams params);

    void updatePost(AddUpdatePostParams params);

    PageResult<PostDto> queryPost(QueryPostParams params);

}
