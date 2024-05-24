package com.ltz.flyfun.model;

public class GetModle  extends BaseModel {
    public int getParamsSize() {
        return parserInterface.setClassificationParamsSize();
    }
    public int getStartPageNum() {
        return parserInterface.startPageNum();
    }
}
