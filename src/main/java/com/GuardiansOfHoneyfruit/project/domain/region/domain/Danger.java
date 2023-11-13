package com.GuardiansOfHoneyfruit.project.domain.region.domain;

import java.util.Arrays;

public enum Danger {

    DANGER("위험", 0.2,1, "조심해!"),
    CAUTION("주의", 0.4,2, "주의가 필요합니다."),
    SAFETY("양호", 0.6, 0, "안심해도 되는 날입니다.");

    private String degreeName;
    private Double convertedDegree;
    private String toolTip;
    private int degree;

    Danger(String degreeName, Double convertedDegree, int degree, String toolTip){
        this.degree = degree;
        this.convertedDegree = convertedDegree;
        this.degreeName = degreeName;
        this.toolTip = toolTip;
    }

    public String getDegreeName(){
        return this.degreeName;
    }
    public Double getConvertedDegree(){
        return this.convertedDegree;
    }
    public String getToolTip(){
        return this.degreeName;
    }

    public static Danger getDanger(int degree) {
        return Arrays.stream(Danger.values())
                .filter(danger -> danger.degree == degree)
                .findFirst()
                .orElse(null); // 또는 .orElseThrow()로 예외 처리
    }

}
