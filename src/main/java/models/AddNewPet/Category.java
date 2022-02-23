package models.AddNewPet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
@ToString
public class Category{
	 String name;
	int id;

//	public Category(String name, int id) {
//		this.name = name;
//		this.id = id;
//	}
}