package ko.or.kosa.entity;

import lombok.Data;

@Data
public class SearchVO {
	private String draw;
	private int start;
	private int length;
	private String orderColumn;
	private String dir;
	private String searchKeyword;
}
