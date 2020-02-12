package io.cryptex.ms.wrapper.ripple.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorInfoDto implements Serializable{

	private static final long serialVersionUID = 1341014449174074228L;

	@NotNull
	private Integer status;
	
	@NotNull
	private List<String> messages;
	
	@NotNull 
	private String debugMessage;
	
	@NotNull
	private Integer code;
	
	private String header;
}
