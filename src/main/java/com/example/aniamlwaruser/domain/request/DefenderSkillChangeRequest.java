package com.example.aniamlwaruser.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DefenderSkillChangeRequest {
    private String defenderAttackTypeSkill;
    private String defenderDefensiveTypeSkill;
    private String defenderUtilityTypeSkill;
    public void setDefenderAttackTypeSkill(String defenderAttackTypeSkill) {
        this.defenderAttackTypeSkill = defenderAttackTypeSkill;
    }

    public void setDefenderDefenseTypeSkill(String defenderDefenseTypeSkill) {
        this.defenderDefensiveTypeSkill = defenderDefenseTypeSkill;
    }

    public void setDefenderUtilityTypeSkill(String defenderUtilityTypeSkill) {
        this.defenderUtilityTypeSkill = defenderUtilityTypeSkill;
    }

}