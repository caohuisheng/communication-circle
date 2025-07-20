package com.chs.circlepost.controller;


import com.chs.base.model.CommonResult;
import com.chs.base.model.PageResult;
import com.chs.circlepost.model.dto.CommentDto;
import com.chs.circlepost.model.dto.QueryCommentParams;
import com.chs.circlepost.service.UserCommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户评论帖子表 前端控制器
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@RestController
@RequestMapping("/post")
public class UserCommentPostController {

    @Autowired
    private UserCommentPostService userCommentPostService;

    @PostMapping("/query_comment")
    public CommonResult<PageResult<CommentDto>> addPost(@RequestBody QueryCommentParams params){
        PageResult<CommentDto> res = userCommentPostService.queryPostComment(params);
        return CommonResult.success(res);
    }

}
