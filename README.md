# Hours-To-Pay-Exercise
This project calculate the total payment that a company has to pay an employee, based on the hours they worked and the times during which they worked. 
## Description
The programa read a txt file located in the folder resources called it **Hours.txt**.

The txt file contains several entries and each entry must have the next strucure:
```
<EmployeeName>=<DayWorked>:<schedule worked>,<DayWorked>:<schedule worked>,....
```
For example:
```
RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00
ASTRID=MO10:00-12:00,TH12:00-14:00,SU20:00-21:00
DAVID=WE10:00-12:00,TH12:00-14:00,SU20:00-21:00,SA00:01-09:00
```
And print the employee's total payment: 
```
The amount to pay RENE is: 215
The amount to pay ASTRID is: 85
The amount to pay DAVID is: 325
```
## Design description
The program is composed by four packages:
### com.exercise.main
Here there is the class called **MethodsUseExample** which contains the main method to run the program.
### com.exercise.model
Here there is the POJO class called **Employee** which contains the employee's attributes such as his name, his worked hours with the days and his payment.
### com.exercise.resource
Here there is the txt file called **Hours** which contains the input information structured as show above. 
### com.exercise.test
Here there is the JUnit class **TestCalculationMethods** which contains methods for testing the methods of the class **Calculate Methods**. Also, this class can test the exceptions thrown by that methods.
### com.exercise.utils
Here there is the class called **CalculationMethods** which contains all the logic of the program. The methods inside this class are responsable for extract the information of the txt file **Hours**, format it and make the calculations needed for know the employee's payment.


## How to use
1. Download or clone the project 
2. Open Eclipse, go to File -> Import -> Existing Maven Projects
3. Select root directory -> Done
4. Once Eclipse import the project, run the **MethodsUseExample** class
5. See the output in the console

## Author
* **David Moreno Palacios**
