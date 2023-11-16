package com.example.aniamlwaruser.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttackerSkillChangeRequest {
    private String attackerAttackTypeSkill;
    private String attackerDefensiveTypeSkill;
    private String attackerUtilityTypeSkill;

    public void setAttackerAttackTypeSkill(String attackerAttackTypeSkill) {
        this.attackerAttackTypeSkill = attackerAttackTypeSkill;
    }

    public void setAttackerDefenseTypeSkill(String attackerDefenseTypeSkill) {
        this.attackerDefensiveTypeSkill = attackerDefenseTypeSkill;
    }

    public void setAttackerUtilityTypeSkill(String attackerUtilityTypeSkill) {
        this.attackerUtilityTypeSkill = attackerUtilityTypeSkill;
    }
}