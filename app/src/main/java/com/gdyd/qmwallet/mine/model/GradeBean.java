package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;

/**
 * Created by zanzq on 2017/3/3.
 */

public class GradeBean implements Serializable {
    private String name;
    private int type;
    private boolean isCurrent;
    private String content;
    private String BName;
   private int level;
    private int levelvalue;
    private int LevelType;
    private String title;
    public GradeBean(String name, int type, boolean isCurrent, String content, String BName, int level) {
        this.name = name;
        this.type = type;
        this.isCurrent = isCurrent;
        this.content = content;
        this.BName = BName;
        this.level = level;
    }

    public int getLevelvalue() {
        return levelvalue;
    }

    public void setLevelvalue(int levelvalue) {
        this.levelvalue = levelvalue;
    }

    public int getLevelType() {
        return LevelType;
    }

    public void setLevelType(int levelType) {
        LevelType = levelType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GradeBean(String name, int type, boolean isCurrent, String content, String BName, int level, int levelvalue, int LevelType, String title) {
        this.name = name;
        this.type = type;
        this.isCurrent = isCurrent;
        this.content = content;
        this.BName = BName;
        this.level = level;
        this.levelvalue = levelvalue;
        this.LevelType=LevelType;
        this.title=title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBName() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }

    public GradeBean(String name, int type, boolean isCurrent, String content, String BName) {

        this.name = name;
        this.type = type;
        this.isCurrent = isCurrent;
        this.content = content;
        this.BName = BName;
    }
}
