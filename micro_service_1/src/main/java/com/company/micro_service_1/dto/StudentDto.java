package com.company.micro_service_1.dto;

import com.company.micro_service_1.bean.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-11-04 20:26:49
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentDto extends Student {

}
