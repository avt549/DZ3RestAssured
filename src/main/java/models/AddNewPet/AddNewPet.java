package models.AddNewPet;

import java.util.List;

import lombok.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Data

@AllArgsConstructor
@JsonSerialize
@Getter
@Builder
@ToString
public class AddNewPet {

	private List<String> photoUrls;

	private String name;
	private int id;
	private Category category;
	private List<TagsItem> tags;
	private String status;


}