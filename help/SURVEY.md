### HELP
* [general help](http://localhost:8080/api/help)
* [license](http://localhost:8080/api/help/license)
* survey

# Survey Requirements
Add a survey feature to the POC.  Here are the suggested requirements:
* Each Survey has a user type.
* Each Survey User Type has a unique survey.
* Each Question can have a different Answer Type.
* Answer Types are:
	* Radio Button
	* Checkbox
	* Dropdown
	* Text
	* Rating

* All Answers are anonymous.
* All Answers can be reviewed before persisting to the database.
* A report is generated with Summary of survey answers, # of participants, # of non-participants, 
* Each Survey can have common and unique questions
* A question can be in a section / Category
* Surveys have start and stop dates

### Optional Requirements
* Survey Templates
* If a user has already taken the Survey, they can not take it again
* If the user has NOT taken the Survey, it shows up on their dashboard.