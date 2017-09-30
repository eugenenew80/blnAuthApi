package kz.kegoc.bln.entity.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthDataDto {
	private String userName;
	private String password;
}
