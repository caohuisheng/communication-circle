package com.chs.circlepost.controller;


import com.chs.base.model.CommonResult;
import com.chs.base.model.PageResult;
import com.chs.circlepost.model.dto.AddUpdatePostParams;
import com.chs.circlepost.model.dto.CommentPostParams;
import com.chs.circlepost.model.dto.PostDto;
import com.chs.circlepost.model.dto.QueryPostParams;
import com.chs.circlepost.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 帖子前端控制器
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Slf4j
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post/add_post")
    public CommonResult<Void> addPost(@RequestBody @Validated AddUpdatePostParams params){
        postService.savePost(params);
        return CommonResult.success("添加成功");
    }

    @PostMapping("/post/delete_post")
    public CommonResult<Void> deletePost(@RequestParam Integer postId){
        postService.removeById(postId);
        return CommonResult.success("删除成功");
    }

    @PostMapping("/post/update_post")
    public CommonResult<Void> updatePost(@RequestBody @Validated AddUpdatePostParams params){
        postService.updatePost(params);
        return CommonResult.success("更新成功");
    }

    @GetMapping("/post/query_post")
    public CommonResult<PageResult<PostDto>> queryPost(@RequestBody QueryPostParams params){
        Integer userId = 101;
        PageResult<PostDto> pageResult = postService.queryPost(params, userId);
        return CommonResult.success(pageResult);
    }

    @GetMapping("/post/query_post_byid")
    public CommonResult<PostDto> queryPostById(@RequestParam Integer postId){
        Integer userId = 101;
        PostDto postDto = postService.queryPostById(postId, userId);
        return CommonResult.success(postDto);
    }

    @PostMapping("/post/like_post")
    public CommonResult<Void> likePost(@RequestParam Integer postId){
        Integer userId = 101;
        postService.likePost(postId, userId);
        return CommonResult.success("点赞成功");
    }

    @PostMapping("/post/comment_post")
    public CommonResult<Void> commentPost(@RequestBody CommentPostParams params){
        Integer userId = 101;
        postService.commentPost(params, userId);
        return CommonResult.success("评论成功");
    }

    @PostMapping("/post/collect_post")
    public CommonResult<Void> collectPost(@RequestParam Integer postId){
        Integer userId = 101;
        postService.collectPost(postId, userId);
        return CommonResult.success("收藏成功");
    }
}
