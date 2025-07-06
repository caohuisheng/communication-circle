package com.chs.circlepost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chs.circlepost.mapper.TopicMapper;
import com.chs.circlepost.model.po.Topic;
import com.chs.circlepost.service.TopicService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 话题表 服务实现类
 * </p>
 *
 * @author chs
 * @since 2025-06-29
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

}
