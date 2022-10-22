package com.example.packagetool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 12:20
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackingToolInfo {

    BasicInfoForm basicInfoForm;
    ImageInfoForm imageInfoForm;
    DeveloperInfoForm developerInfoForm;
}
