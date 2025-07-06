package com.chs.circlepost.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chs.circlepost.model.dto.QueryPostParams;
import com.chs.circlepost.model.po.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 帖子表 Mapper 接口
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    Integer countByParams(QueryPostParams params);

    List<Post> selectByParams(QueryPostParams params);

}
