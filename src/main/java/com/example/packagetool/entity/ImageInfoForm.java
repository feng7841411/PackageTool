package com.example.packagetool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 12:22
 * @Description:
 *        imageFileName: '',
 *         workLoadYamlFileName: '',
 *         serviceYamlFileName:'',
 *
 *
 *         imageFileUid:'',
 *         wordLoadYamlFileUid:'',
 *         serviceYamlFileUid:'',
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfoForm {

    private String imageFileName;
    private String workLoadYamlFileName;
    private String serviceYamlFileName;

    private String imageFileUid;
    private String wordLoadYamlFileUid;
    private String serviceYamlFileUid;

}
