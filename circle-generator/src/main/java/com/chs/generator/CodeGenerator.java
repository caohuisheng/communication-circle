package com.chs.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;

/**
 * Author: chs
 * Description:
 * CreateTime: 2025-06-29
 */
public class CodeGenerator {
    private static final String URL = "jdbc:mysql://localhost:13306/communication_circle?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8";

    //数据库账号
    private static final String DATA_SOURCE_USER_NAME  = "root";
    //数据库密码
    private static final String DATA_SOURCE_PASSWORD  = "123456";
    //生成的表
    private static final String[] TABLE_NAMES = new String[]{
            "circle",
            "post",
            "post_circle",
            "post_topic",
            "topic",
            "user",
            "user_collect_post",
            "user_comment_post",
            "user_like_post"
    };

    // 一般情况下要先生成 DTO类 然后修改此参数再生成 PO 类。
    private static final Boolean IS_DTO = false;

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Velocity
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setFileOverride(true);
        //生成路径
        gc.setOutputDir(System.getProperty("user.dir") + "/circle-generator/src/main/java");
        gc.setAuthor("chs");
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setServiceName("%sService");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

        if (IS_DTO) {
            gc.setSwagger2(true);
            gc.setEntityName("%sDTO");
        }
        generator.setGlobalConfig(gc);

        // 数据库配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(URL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DATA_SOURCE_USER_NAME);
        dsc.setPassword(DATA_SOURCE_PASSWORD);
        generator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("post");
        pc.setParent("com.chs");

        pc.setServiceImpl("service.impl");
        pc.setXml("mapper.xml");
        pc.setEntity("model.entity");
        generator.setPackageInfo(pc);

        // 设置模板
        TemplateConfig tc = new TemplateConfig();
        generator.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(TABLE_NAMES);
        strategy.setControllerMappingHyphenStyle(true);
        // strategy.setTablePrefix(pc.getModuleName() + "_");
        // Boolean类型字段是否移除is前缀处理
        // strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setRestControllerStyle(true);

        // 自动填充字段配置
        strategy.setTableFillList(Arrays.asList(
                new TableFill("create_time", FieldFill.INSERT),
                new TableFill("change_time", FieldFill.INSERT_UPDATE)
        ));
        generator.setStrategy(strategy);

        generator.execute();
    }
}
