package ko.or.kosa.entity;

import lombok.Data;

@Data
public class BoardVO {
	private Long 		id;
	private String 	title;
	private String 	content;
	private int 		read_cnt;
	private String 	register_id;
	private String 	register_time;
	private String 	update_time;

}
