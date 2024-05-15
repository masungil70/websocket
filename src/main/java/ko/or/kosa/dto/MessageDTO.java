package ko.or.kosa.dto;

import lombok.Data;

@Data
public class MessageDTO {
	private String roomId;
	private String userId;
	private String message;
}
