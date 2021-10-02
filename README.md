# mercedes-assessment
Assessment of Mercedes Benz
Please follow below steps to setup and run the services.

1. Kindly downlaod the project and setup in any of IDE(Preferable intellij IDEA). Once the project is setup please execute mvn clean install and reopen the project again.

2. Start both the services (user-service and user-client).
3. Once both the services runs properly, user bleow rest end point -

	1. Add user - Add the user details into the file(XML/CSV) and generate a unique uuid to identify user while updating or reading the users. Also you need to pass mandatory header file type(XML/CSV).
	
	Endpoint: http://localhost:9091/user/add
	Method: Post
	Sample Request : 
	{
	"name":"Amit Jain",
	"dob":"20-12-2020",    
	"salary":"10000",
	"age":22
	}
	Headers:
		Content-Type : application/json
		fileType : XML or CSV
	Sample Response :
	{
    "user": {
        "uuid": "2f5435a3-b303-4ada-8068-0b8d7635368e",
        "name": "Amit Jain",
        "dob": "20-12-2020",
        "salary": 10000,
        "age": 22
		}
	}
	
	Validation : 
		Date of birth should be in between range from 01-01-1900 to Present.
		Age should be in be range of 1 to 100.
		Salary should be an amount. Only 1 decimal is allowed.
	
	
	2. Update User : Update the existing user in a file (XML/CSV) with the uuid identfier. Also you need to pass mandatory header file type(XML/CSV). Value mentioned in request will be updated and get the updated values in the response.
	
		Endpoint: http://localhost:9091/user/update/{uuid}
		Method: PUT
		Sample Request :
		Sample Request : 
		{
			"name":"Amit Jain",
			"dob":"20-12-2020",
			"salary":"10000",
			"age":22
		}
		Headers:
			Content-Type : application/json
			fileType : XML or CSV
			
		Sample Response:
		{
			"user": {
				"uuid": "59b4bc55-3ec2-48ba-8d33-170bf0f375a1",
				"name": "Amit Singh",
				"dob": "11-07-2021",
				"salary": 300000,
				"age": 30
				}
		}		
		
		
		3. Read User : Read the existing records present in the file(XML/CSV).
		
		* Find by Id : Return the matching user present in the file. Preferable is XML.
		
		Endpoint : http://localhost:9091/user/{uuid}
		
		Method : GET
		
		Sample Response:
		
		{
		"users": [
				{
					"uuid": "59b4bc55-3ec2-48ba-8d33-170bf0f375a1",
					"name": "Amit Singh",
					"dob": "11-07-2021",
					"salary": 300000,
					"age": 30
				}
			]
		}
		
		* Find All : Return all the users present in the file. Preferable is XML.
		
		Endpoint : http://localhost:9091/user/users
		
		Method : GET
		
		Sample Response:
		{
		"users": [
			{
            "uuid": "59b4bc55-3ec2-48ba-8d33-170bf0f375a1",
            "name": "Amit Singh",
            "dob": "11-07-2021",
            "salary": 300000,
            "age": 30
			},
			{
            "uuid": "e0d6e70b-7c12-41e4-9a4b-d0af3f40c519",
            "name": "Amit Jain",
            "dob": "20-12-2020",
            "salary": 10000,
            "age": 22
			}
			]
		}
		
		
Please write a any query related to project setup or the running the servies to akjain1107@gmail.com
