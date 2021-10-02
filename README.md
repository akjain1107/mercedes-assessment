# mercedes-assessment
Assessment of Mercedes Benz
Please follow below steps to setup and run the services.

1. Kindly downlaod the project and setup in any of IDE(Preferable intellij IDEA). Once the project is setup please execute mvn clean install and reopen the project again. If you still see the errors in setup, kindly try to run two microservices using class MercedesClientApplication and MercedesServiceApplication, services will start.
2. 
3. Start both the services (user-service and user-client).

Implemantation done on the basis of below scenarios -

Service 1: user-client


1.	This will be consumer-facing service which will accept request from the user.
2.	The service should accept the data in JSON format within the request body.
3.	The service should also accept another input fileType as a parameter or header within the request. The value to this parameter could be either CSV or XML.
4.	The service should expose 3 endpoints as below
a.	Store – to save new data
b.	Update – to update existing data
c.	Read – to read existing data, Read need not contain the fileType header or parameter
Note the request itself could contain any data. Sample given below can be used.
Sample body:
{ name: “Hello”, dob: “20-08-2020”, salary: “122111241.150”, age: 20 }
Request Header or Parameter
fileType = CSV
fileType = XML
5.	The service should process the request received with the below constraints.
a.	Validate the request completely. Including the data and data types
b.	Pass on the validated information to Service 2. Below are the conditions to keep in mind.
i.	The communication between Service 1 & Service 2 should NOT be http or https. Use some other means of communication.
ii.	The data transferred to the second service should be encrypted and transformed to Google’s protocol buffer format.
iii.	Only READ operation can be done over http


Service 2: user-service
1.	Service should receive and decrypt the information passed by the previous service.
2.	Once decrypted the service should store the information either in CSV/XML file based on the input received in the fileType parameter in the previous service.
3.	If the data should be read and returned then the returned data has to be encrypted. Which Service 1 should decrypt and respond to the consumer.


5. Once both the services runs properly, user bleow rest end point -

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
