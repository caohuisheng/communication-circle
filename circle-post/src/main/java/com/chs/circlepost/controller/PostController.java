package com.chs.circlepost.controller;


import com.chs.base.model.CommonResult;
import com.chs.base.model.PageResult;
import com.chs.circlepost.model.dto.AddUpdatePostParams;
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

    @PostMapping("/post")
    public CommonResult<Void> addPost(@RequestBody @Validated AddUpdatePostParams params){
        postService.savePost(params);
        return CommonResult.success("添加成功");
    }

    @DeleteMapping("/post/{postId}")
    public CommonResult<Void> deletePost(@PathVariable Integer postId){
        postService.removeById(postId);
        return CommonResult.success("删除成功");
    }

    @PutMapping("/post")
    public CommonResult<Void> updatePost(@RequestBody @Validated AddUpdatePostParams params){
        postService.updatePost(params);
        return CommonResult.success("更新成功");
    }

    @GetMapping("/post")
    public CommonResult<PageResult<PostDto>> queryPost(@RequestBody QueryPostParams params){
        PageResult<PostDto> pageResult = postService.queryPost(params);
        return CommonResult.success(pageResult);
    }


}
