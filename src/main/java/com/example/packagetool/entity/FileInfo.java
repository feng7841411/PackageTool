package com.example.packagetool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: 冯金河
 * @Date: 2022/10/17 10:50
 * @Description:
 */

@Entity(name = "FILE_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {

    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer fileId;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private String fileUrl;

    private String fileMd5;

    private Boolean isDelete;

    private Boolean enable;

    private String fileUid;

}
