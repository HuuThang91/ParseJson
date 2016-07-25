# ParseJson
Parse  Json to Object with only one class

With Json :

{
	"children": [
		{
			"name": "tratra",
			"age": "26",
			"subman": [
				{
					"name": "subman1",
					"age": "26"
				},
				{
					"name": "subman2",
					"age": "28"
				}
			]
		},
		{
			"name": "tratra3232",
			"age": "26",
			"subman": [
				{
					"name": "subman3",
					"age": "26"
				},
				{
					"name": "subman4",
					"age": "28"
				}
			]
		}
	],
	"man": {
		"name": "man_name",
		"age": "man_age"
	},
	"ten": "Thao",
	"age": "28"
}

Create the sub object Man :

public class Man {
    public String name,age;

} 

Create the sub object Children:

public class Children {
    public String name,age;
    
    public List<Man> subman;

}

Create the main object to parse , call Human:

public class Human {
    public List<CHILDREN> children;
    public Man man;
    public String ten,age;
}

To parse Json just call 
Human human=ParseJson.parseJsonObject(Human.class,json);
And the result as excpect , no need another libs like Gson anymmore(:D)
--------------------------------------------------------------------------------------------------------------------------------------
Useage :
Example 1: input String and parse to Object

  public String test = "{\"ten\": \"Thang\", \"age\":\"26\"}";
  
  - create Object Human :
  
    public class Human {
    public String ten,age;
      }
  
  - parse  :             Human human=ParseJson.parseJsonObject(Human.class,test);

  
Example 2  : Input String and parse to List<Object>

  public String test = "[{\"ten\": \"Thang\", \"age\":\"26\"},{\"ten\": \"Thao\", \"age\":\"28\"}]";

  - parse   Human human=ParseJson.parseJsonArray(Human.class,test);
  
  
Example 3 :Input jsonObject parse to Object 
    Usage :Human human=ParseJson.parseJson(Human.class,jsonObject);

Example 4 :Input jsonarray parse to List<Object>
    Usage :List<Human> human=ParseJson.parseJson(Human.class,jsonArray);




