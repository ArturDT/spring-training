package pl.training.bank.user.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "role")
@Data
public class RoleDto {

    private String name;

}