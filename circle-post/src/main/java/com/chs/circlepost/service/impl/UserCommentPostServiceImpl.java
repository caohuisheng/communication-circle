package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.base.model.PageResult;
import com.chs.circlepost.mapper.UserCommentPostMapper;
import com.chs.circlepost.model.dto.CommentDto;
import com.chs.circlepost.model.dto.QueryCommentParams;
import com.chs.circlepost.model.po.UserCommentPost;
import com.chs.circlepost.service.UserCommentPostService;
import com.chs.circlepost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户评论帖子表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class UserCommentPostServiceImpl extends ServiceImpl<UserCommentPostMapper, UserCommentPost> implements UserCommentPostService {

    @Autowired
    private UserCommentPostMapper userCommentPostMapper;
    @Autowired
    private UserService userService;

    @Override
    public PageResult<CommentDto> queryPostComment(QueryCommentParams params) {
        // 查询评论列表
        LambdaQueryWrapper<UserCommentPost> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserCommentPost::getPostId, params.getPostId())
                .isNull(UserCommentPost::getParentId)
                .orderByDesc(UserCommentPost::getCommentTime);
        Page<UserCommentPost> page = new Page<>(params.getPage(), params.getPageSize());
        Page<UserCommentPost> pageResult = userCommentPostMapper.selectPage(page, lqw);
        long total = pageResult.getTotal();
        List<UserCommentPost> commentList = pageResult.getRecords();
        // 查询每条评论下的其它评论
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (UserCommentPost comment : commentList) {
            // 查询该评论下的其它评论
            LambdaQueryWrapper<UserCommentPost> lqw2 = new LambdaQueryWrapper<>();
            lqw2.eq(UserCommentPost::getParentId, comment.getId())
                    .orderByAsc(UserCommentPost::getCommentTime);
            List<UserCommentPost> childCommentList = userCommentPostMapper.selectList(lqw2);
            List<CommentDto> childCommentDtoList = childCommentList.stream().map(e -> {
                CommentDto dto = new CommentDto();
                dto.setId(e.getId());
                dto.setContent(e.getContent());
                dto.setCommentTime(e.getCommentTime());
                dto.setUserInfo(userService.getUserInfo(e.getUserId()));
                return dto;
            }).collect(Collectors.toList());
            // 填充回复评论的用户信息
            Map<Integer, CommentDto> commentDtoMap = childCommentDtoList.stream().collect(
                    Collectors.toMap(CommentDto::getId, Function.identity(), (a, b) -> a));
            Integer parentId = comment.getId();
            for (UserCommentPost userCommentPost : childCommentList) {
                if(!userCommentPost.getCommentId().equals(parentId)){
                    CommentDto commentDto = commentDtoMap.get(userCommentPost.getId());
                    CommentDto replyCommentDto = commentDtoMap.get(userCommentPost.getCommentId());
                    commentDto.setReplyUserInfo(replyCommentDto.getUserInfo());
                }
            }

            // 转换成dto
            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setContent(comment.getContent());
            commentDto.setCommentTime(comment.getCommentTime());
            commentDto.setUserInfo(userService.getUserInfo(comment.getUserId()));
            commentDto.setChildrens(childCommentDtoList);
            commentDtoList.add(commentDto);
        }

        return new PageResult<>(commentDtoList, total);
    }
}
