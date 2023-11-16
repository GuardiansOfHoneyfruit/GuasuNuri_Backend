package com.GuardiansOfHoneyfruit.project.domain.region.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Danger {

    DANGER("감염 위험", 0.2,1, List.of("주기적인 검사 및 감시를 실시하여 꽃들의 건강 상태를 확인해주세요", "건강한 과일을 위해 규칙적으로 올바른 관리를 해주세요", "개화 전 약제방제는 1회 필수로 실시가 필요합니다.")),
    CAUTION("주의", 0.4,2, List.of("방제권고가 의심되는 때입니다.",
            "방제 약제의 재고를 확인하고 시군 농업기술센터로부터 약제를 미리 배부받아 주세요.",
            "주변 환경의 청결을 유지하여 전파를 방지해주세요.")),
    SAFETY("양호", 0.6, 0, List.of(
            "주기적인 검사 및 감시를 실시하여 꽃들의 건강 상태를 확인해주세요",
            "건강한 과일을 위해 규칙적으로 올바른 관리를 해주세요",
            "개화 전 약제방제는 1회 필수로 실시가 필요합니다."));

    private String degreeName;
    private Double convertedDegree;
    private List<String> toolTip = new ArrayList<>();
    private int degree;

    Danger(String degreeName, Double convertedDegree, int degree, List<String> toolTips){
        this.degree = degree;
        this.convertedDegree = convertedDegree;
        this.degreeName = degreeName;
        this.toolTip = toolTips;
    }

    public String getDegreeName(){
        return this.degreeName;
    }
    public Double getConvertedDegree(){
        return this.convertedDegree;
    }
    public List<String> getToolTip(){
        return this.toolTip;
    }

    public static Danger getDanger(int degree) {
        return Arrays.stream(Danger.values())
                .filter(danger -> danger.degree == degree)
                .findFirst()
                .orElse(null);
    }

}
